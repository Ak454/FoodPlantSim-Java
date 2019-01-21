package Simulation;
import java.util.ArrayList;
import java.util.Random;

import System.BlueCheese;
import System.Cheese;
import System.Item;
import System.InputLine;
import System.PackerQueue;
import System.PackingMachine;
import System.PerishableItem;
import System.ProcessingMachine;
import System.SoupPowder;

/**
 * An item processing and packing line simulator, based on 2 arrival 
 * probabilities p and q.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */

public class Simulator {

	//Collection of InputLine's
	private ArrayList<InputLine> inputLines;

	//Collection of ProccessingMachine's
	private ArrayList<ProcessingMachine> processors;

	//Collection of PackerQueue's
	private ArrayList<PackerQueue> packerQueues;

	//Collection of PackingMachine's
	private ArrayList<PackingMachine> packers;

	//Collection of items that are currently in the system.
	private ArrayList<Item> items;

	//Collection of items that can occur in the system
	private ArrayList<Item> potentialItems;

	//The number of time units (seconds) the machine will run for
	private static final int totalTime = 7200;

	//The probability that a Cheese or BlueCheese object will be created for processing
	private double p;

	//The probability that a SoupPowder object will be created for processing
	private double q;

	//A random number generator
	private Random rand;

	//A ProfitCalculator
	private static ProfitCalculator profitCalculator;

	//The number of ProcessingMachines that will be used in this system
	private int numProcessors = 3;

	//The number of PackingMachines that will be used in this system
	private int numPackers = 2;
	
	//The amount to add to profit
	private static final int add = 10;
	
	//The amount to minus from profit
	private static final int minus = 20;
	
	//Whether the coursework result print conditions will occur. This global variable would be removed beyond the 
	//scope of this coursework.
	private static boolean cwork;

	//The main method, responsible for accepting run-time arguments, interpreting and using them to call the simulate method.
	public static void main(String[] args) {
		int seed = 42;  // By default, use a seed of 42

		// The following code will run the GUI, when no arguments are passed at run time.
		if(args.length == 0){
			Simulator s = new Simulator(seed);
			SimGUIv2 sq = new SimGUIv2(s);
		}

		// The following code is purely for purpose of testing each value of p & q, as per the coursework 
		// and only occurs when 1 argument is provided. It would be removed beyond the scope of this coursework,
		// along with the method it calls.
		if(args.length == 1){
			courseworkTestConditions();
		}

		if(args.length > 1){
			seed = Integer.parseInt(args[0]);

			Simulator s = new Simulator(seed);

			s.setP(0.01);  // By default, set p = 0.1
			if (args.length >= 2) {
				s.setP(Double.parseDouble(args[1]));
			}

			if (s.getP() <= 0) {
				s.setP(0.01);
			}

			s.setQ(0.01);  // By default, set q = 0.1
			if (args.length >= 3) {
				s.setQ(Double.parseDouble(args[2]));
			}

			if (s.getQ() <= 0) {
				s.setQ(0.01);
			}

			s.simulate(totalTime);
		}
	}

	public Simulator(int seed){
		rand = new Random(seed);

		//Create collection of items
		items = new ArrayList<Item>();

		//Create collection of potential items
		potentialItems = new ArrayList<Item>();
		potentialItems.add(new Cheese());
		potentialItems.add(new BlueCheese());
		potentialItems.add(new SoupPowder());

		//Create profit calculator
		profitCalculator = new ProfitCalculator();

		initialiseSystem();
	}

	/**
	 *@param totalTime the total amount of time the simulation will run for.
	 *This method performs the simulation for the total amount of time specified in the parameter.
	 */
	public void simulate(int totalTime){
		profitCalculator = new ProfitCalculator();
		for(int step = 1; step <= totalTime; step++){
			simulateOneStep();
			if(cwork == false){
				printSummary(step);
			}
			if(step == totalTime){
				printFigures();
			}
		}
	}

	/**
	 *This method performs the actions that occur for a single step (time unit).
	 */
	public void simulateOneStep(){

		double nextDouble = rand.nextDouble();

		createItem(nextDouble);
		jamMachines(nextDouble);
		removeSpoiled();
		operateProcessors(processors);
		operatePackerQueues(processors);
		operatePackers();
	}

	/**
	 * A method for creating the queue's and machines required by the system. 
	 */
	public void initialiseSystem(){

		//Create the collection of machines and queue's
		inputLines = new ArrayList<InputLine>();
		processors = new ArrayList<ProcessingMachine>();
		packerQueues = new ArrayList<PackerQueue>();
		packers = new ArrayList<PackingMachine>();


		//Create InputLine's and add them to inputLines collection.
		for (int i = 0; i < numProcessors; i++){
			InputLine inputLine = new InputLine();
			inputLines.add(inputLine);
		}

		//Create ProcessingMachine's and add them to processors collection.
		int x = 0;
		for (int i = 0; i < numProcessors; i++){
			ProcessingMachine processor = new ProcessingMachine(potentialItems.get(x).getProcessTime());
			processors.add(processor);
			x++;
			if(x == potentialItems.size()){
				x = 0;
			}
		}
		x = 0;

		//Create PackerQueue's and add them to packerQueues collection.
		for (int i = 0; i < numPackers; i++){
			PackerQueue packerQueue = new PackerQueue();
			packerQueues.add(packerQueue);
		}

		//Create PackingMachine's and add them to packers collection.
		for (int i = 0; i < numPackers; i++){
			PackingMachine packer = new PackingMachine();
			packers.add(packer);
		}
	}

	/**
	 * Creates a given Item if nextDouble is less than the item's arrival probability.
	 * @param nextDouble the double to be used to determine food creation.
	 */
	public void createItem (double nextDouble){
		if(nextDouble >= 0){
			if(nextDouble <= p){
				Cheese cheese = new Cheese();
				inputLines.get(0).addQueueItem(cheese);
				items.add(cheese);
			}

			else if(nextDouble <= p + p && numProcessors >= 2){
				BlueCheese blueCheese = new BlueCheese();
				inputLines.get(1).addQueueItem(blueCheese);
				items.add(blueCheese);
			}

			else if(nextDouble <= p + p + q && numProcessors >= 3){
				SoupPowder soup = new SoupPowder();
				inputLines.get(2).addQueueItem(soup);
				items.add(soup);
			}
		}
	}

	/**
	 * A method to jam the required machines, based on the value nextDouble.
	 * @param nextDouble the number used to determine whether to jam machine.
	 */
	public void jamMachines(double nextDouble){
		double value = 0.001;
		boolean finish = false;

		while(finish == false){
			for(int i = 0; i < processors.size(); i++){
				if(nextDouble <= value){
					processors.get(i).jamMachine();
					finish = true;
					i = processors.size();
				}
				value += 0.001;
			}
			if(finish == false){
				for(int i = 0; i < packers.size(); i++){
					if(nextDouble <= value){
						packers.get(i).jamMachine();
						finish = true;
						i = packers.size();
					}
					value += 0.001;
				}
			}
			finish = true;
		}
	}


	/**
	 * A method to check system for spoiled items, remove from system and decrease profit accordingly.
	 */
	//Increment all non-spoiled perishable items spoilProgress by 1.
	public void removeSpoiled(){
		for(int i = 0; i < items.size(); i++){
			Item item = items.get(i);
			if(item instanceof PerishableItem){
				PerishableItem PerishFood = ((PerishableItem) item);
				if(PerishFood.checkSpoilt() == true){
					items.remove(item);
					i--;
					profitCalculator.minusProfit(minus);
					profitCalculator.incrementSpoiledCount();
				}
				else{
					PerishFood.incrementSpoil();
				}
			}
		}
	}

	/**
	 * A method to check ProcessingMachine's to see if they are jammed. 
	 * If they are jammed, completeProcessing must not occur, timeSpent must = 0 and current item must be removed. If not, processing can occur.
	 * @param useProcessors a collection of processors to be acted upon.
	 */
	//current item must be reset and removed from list of active items.
	public void operateProcessors(ArrayList<ProcessingMachine> useProcessors){
		for(int x = 0; x < useProcessors.size(); x++){
			ProcessingMachine processor = useProcessors.get(x);
			try{
				completeProcessing(processor, x);
			}
			catch(Exception e){
				processor.addJamTime();	
				items.remove(processor.getCurrentItem());
				processor.setCurrentItemNull();
				processor.setTimeZero();
			}
		}
	}


	/**
	 * A method for controlling the processing that occurs in relation to the ProcessingMachine objects.
	 * @param processor a processor to be acted upon.
	 * @param index the position of the corresponding inputLine.
	 */
	public void completeProcessing(ProcessingMachine processor, int index){

		//Get next Item stored in each InputLine and add it to the corresponding ProcessingMachine, if required and available.
		//If statement checks to see if ProcessingMachine isBusy. If busy == true, it must not accept the next Item.
		//If processor is busy, check if processing is complete. If so, mark as complete, save processed item and reset machine.
		//If processor is busy, but processing is not complete, increment time spent by 1.

		if(processor.checkJammed() == true){
			throw new UnsupportedOperationException("A jammed machine may not complete its activities.");
		}

		if(processor.checkBusy() == false){
			Item item = inputLines.get(index).getNextItem();
			if(item != null){
				processor.act(item);
			}
		}
		else if(processor.checkBusy() == true){
			try{
				processor.complete();
			}
			catch (Exception e) {
				processor.incrementTimeSpent();
			}
		}
	}

	/**
	 * A method to Check ProcessingMachine's for processed items, and add them to PackerQueue with smallest size.
	 * @param useProcessors a collection of processors to be acted upon.
	 */
	//If the processor contains a complete item, move that item to the packerQueue with smallest size and set processed item in processor back to null.
	//If the processor does not contain a processed item, then do nothing.
	public void operatePackerQueues(ArrayList<ProcessingMachine> useProcessors){
		for (int a = 0; a < useProcessors.size(); a++){
			Item item = useProcessors.get(a).getFinishedItem();
			if(item != null){
				int smallestQueue = 0;
				for (int nextQueue = 1; nextQueue < packerQueues.size(); nextQueue++){

					int smallestQueueSize = packerQueues.get(smallestQueue).getQueueSize();
					int nextQueueSize = packerQueues.get(nextQueue).getQueueSize();

					if(packers.get(smallestQueue).checkBusy() == true && packers.get(nextQueue).checkBusy() == false && smallestQueueSize == nextQueueSize){
						smallestQueue = nextQueue;
					}
					else {
						if(packers.get(smallestQueue).checkBusy() == true){
							smallestQueueSize ++;
						}
						if(packers.get(nextQueue).checkBusy() == true){
							nextQueueSize ++;
						}
					}
					if(smallestQueueSize > nextQueueSize){
						smallestQueue = nextQueue;
					}
				}
				packerQueues.get(smallestQueue).addQueueItem(item);
				useProcessors.get(a).setFinishedItemNull();
			}
		}
	}


	/**
	 * A method to check PackingMachine's to see if they are jammed. 
	 * If they are jammed, completePacking must not occur, timeSpent must = 0 and current item must be removed. If not, processing can occur.
	 */
	//current item must be reset and removed from list of active items.
	public void operatePackers(){
		for(int y = 0; y < packers.size(); y++){
			PackingMachine packer = packers.get(y);
			try{
				completePacking(packer, y);
			}
			catch (Exception e){
				packer.addJamTime();	
				items.remove(packer.getCurrentItem());
				packer.setCurrentItemNull();
				packer.setTimeZero();
			}
		}
	}

	/**
	 * A method for controlling the packing that occurs in relation to the PackingMachine objects.
	 * @param packer a machine to be acted upon.
	 * @param b the index of the corresponding PackerQueue.
	 */
	public void completePacking(PackingMachine packer, int b){
		//Get next Item stored in each PackerQueue and add it to the corresponding PackingMachine, if required and available.
		//If statement checks to see if PackingMachine isBusy. If busy == true, it must not accept the next Item.
		//If processor is busy, check if packing is complete. If so, mark as complete, adjust profit counts and reset machine.
		//If processor is busy, but processing is not complete, increment time spent by 1.

		if(packer.checkJammed() == true){
			throw new UnsupportedOperationException("A jammed machine may not complete its activities.");
		}

		if(packer.checkBusy() == false){
			Item item = packerQueues.get(b).getNextItem();
			if(item != null){
				packer.act(item);
			}
		}

		else if(packer.checkBusy() == true){			
			try {
				packer.complete();
				profitCalculator.addProfit(add);
				profitCalculator.incrementPackedCount();
				items.remove(packer.getFinishedItem());
				packer.setFinishedItemNull();
			}
			catch (Exception e) {
				packer.incrementTimeSpent();
			}
		}
	}

	/**
	 * A mutator method to set the value p.
	 * @param p the value to be assigned to p.
	 */
	public void setP(Double p){
		this.p = p;
	}

	/**
	 * A mutator method to set the value q.
	 * @param q the value to be assigned to q.
	 */
	public void setQ(Double q){
		this.q = q;
	}

	/**
	 * A method to get the value p
	 * @return {double} p the probability that a given item will be created.
	 */
	public double getP(){
		return p;
	}

	/**
	 * A method to get the value q
	 * @return {double} q the probability that a given item will be created.
	 */
	public double getQ(){
		return q;
	}

	/**
	 * A method to set the number of Processors and inputLines used in the system.
	 * @param value the number of processors.
	 */
	public void setNumProcessors(int value){
		if(value >= 0){
			this.numProcessors = value;
		}
		else {
			this.numProcessors = 3;
		}
	}

	/**
	 * A method to set the number of Packers and packerQueue's used in the system.
	 * @param value the number of packers.
	 */
	public void setNumPackers(int value){
		if(value >= 0){
			this.numPackers = value;
		}
		else {
			this.numPackers = 2;
		}
	}

	/**
	 * A method to set the packing time for the PackingMachine's
	 * @param packingTime the packingTime of all packingMachines.
	 */
	public void setPackingTime(int packingTime){
		for(int i=0; i < numPackers; i++){
			packers.get(i).setPackingTime(packingTime);
		}
	}

	/**
	 * A method to set the process time of each processor.
	 * @param value the value assigned to processor1.
	 * @param value1 the value assigned to processor2.
	 * @param value2 the value assigned to processor3.
	 */
	public void setProccessTime(int value, int value1, int value2) {
		processors.get(0).setProcessTime(value);
		processors.get(1).setProcessTime(value1);
		processors.get(2).setProcessTime(value2);		
	}

	/**
	 * A method to return the number of machines in the system
	 * @return {int} machineQuantity the number of machines in the system.
	 */
	public int getMachineQuantity(){
		return processors.size() + packers.size();
	}

	/**
	 * A method to return the number of queues in the system
	 * @return {int} queueQuantity the number of queues in the system.
	 */
	public int getQueueQuantity(){
		return inputLines.size() + packerQueues.size();
	}

	/**
	 * A method to return the size of the items ArrayList
	 * @return {int} listSize the size of the items ArrayList.
	 */
	public int getItemsSize(){
		return items.size();
	}

	/**
	 * A method to return an element 'i' from the items ArrayList
	 * @param position the of the item in the items collection.
	 * @return {Item} item the item in the items ArrayList at the given 'position'.
	 */
	public Item getItem(int position){
		return items.get(position);
	}

	/**
	 * A method to return an element 'i' from the processors ArrayList
	 * @param position the position of the processor in the processors collection.
	 * @return {ProcessingMachine} processor the processor in the processors ArrayList at the given 'position'.
	 */
	public ProcessingMachine getProcessor(int position){
		return processors.get(position);
	}

	/**
	 * A method to return an element 'i' from the packers ArrayList
	 * @param position the position of the packer in the packers collection.
	 * @return {PackingMachine} packer the packer in the packers ArrayList at the given 'position'.
	 */
	public PackingMachine getPacker(int position){
		return packers.get(position);
	}

	/**
	 * A method to return an element 'i' from the packerQueues ArrayList
	 * @param position the position of the packerQueue in the packerQueues collection.
	 * @return {PackerQueue} packerQueue the packerQueue in the packerQueues ArrayList at the given 'position'.
	 */
	public PackerQueue getPackerQueue(int position){
		return packerQueues.get(position);
	}

	/**
	 * A method to print the profit-related figures for the systems current state.
	 */
	public void printFigures(){
		System.out.println();
		System.out.println("These are the final figures for the following values of p and q: ");
		System.out.println("Value p = " + getP());
		System.out.println("Value q = " + getQ());
		System.out.println("Profit: " + profitCalculator.getProfit());
		System.out.println("Packed Count: " + profitCalculator.getPackedCount());
		System.out.println("Spoiled Count: " + profitCalculator.getSpoiledCount());
	}


	/**
	 * A method to print the output for all coursework requirements, including seed & variations of p & q.
	 */
	private static void courseworkTestConditions(){
		ArrayList<Integer> profitFigures = new ArrayList<Integer>();
		cwork = true;

		for(int seed = 7; seed <= 52; seed += 5){
			System.out.println();
			System.out.println("Seed number: " + seed);
			double x = 0.01;
			double i = 0.01;
			boolean finish = false;
			int highestProfit = 0;
			double pValue = 0;
			double qValue = 0;
			int counter = 0;

			while(finish == false){
				Simulator s = new Simulator (seed);
				profitCalculator.zeroProfit();
				s.setP(x);
				s.setQ(i);
				s.simulate(totalTime);
				i += 0.01;


				if(seed == 7){
					profitFigures.add(profitCalculator.getProfit());
				}
				else{
					int profit = profitFigures.get(counter) + profitCalculator.getProfit();
					profitFigures.set(counter, profit);
				}

				if(profitCalculator.getProfit() > highestProfit){
					highestProfit = profitCalculator.getProfit();
					pValue = s.getP();
					qValue = s.getQ();
				}

				if(s.getQ() == 0.05 && s.getP() != 0.05){
					x += 0.01;
					i = 0.01;
				}

				if(s.getP() == 0.05 && s.getQ() == 0.05){
					finish = true;
				}
				counter ++;
			}
			System.out.println();
			System.out.println("Result");
			System.out.println("Highest profit made = " + highestProfit + ", when:");
			System.out.println("Value p = " + pValue);
			System.out.println("Value q = " + qValue);
		}
		System.out.println();
		double pValue = 0.01;
		double qValue = 0.01;
		double optimalP = 0;
		double optimalQ = 0;
		int mostProfit = 0;

		for(int index = 0; index < profitFigures.size(); index++){
			int avgProfit = profitFigures.get(index)/10;
			System.out.println();
			System.out.println("Value p = " + pValue);
			System.out.println("Value q = " + qValue);
			System.out.println("Average Profit = " + avgProfit);

			if(avgProfit > mostProfit){
				mostProfit = avgProfit;
				optimalP = pValue;
				optimalQ = qValue;
			}

			if(qValue >= 0.05){
				qValue = 0.01;
				pValue += 0.01;
			}
			else{
				qValue += 0.01;
			}
			if(pValue > 0.05){
				pValue = 0.01;
			}
		}
		System.out.println();
		System.out.println();
		System.out.println("Most profit occurs when:");
		System.out.println("Value p = " + optimalP);
		System.out.println("Value q = " + optimalQ);
		System.out.println();
		System.out.println("These values of p and q across the 10 seeds produces");
		System.out.println("Average Profit = " + mostProfit);
	}

	/**
	 * A method to print a summary of the systems current state.
	 */

	//This method was used for analysing results as the system was developed. Hence, why it is not currently in use. 
	//We have left it in the code as it may be useful for further development, in the future.
	@SuppressWarnings("unused")
	private void printSummary(int step){

		int cheeseQuan = 0;
		int blueQuan = 0;
		int soupQuan = 0;

		for(int i = 0; i < items.size(); i++){
			if(items.get(i) instanceof Cheese){
				cheeseQuan ++;
			}
			if(items.get(i) instanceof BlueCheese){
				blueQuan ++;
			}
			if(items.get(i) instanceof SoupPowder){
				soupQuan ++;
			}
		}

		String processor1 = "Processor1 Jam Status: " + processors.get(0).checkJammed();
		String processor2 = "Processor2 Jam Status: " + processors.get(1).checkJammed();
		String processor3 = "Processor3 Jam Status: " + processors.get(2).checkJammed();

		String packer1 = "Packer1 Jam Status: " + packers.get(0).checkJammed();
		String packer2 = "Packer2 Jam Status: " + packers.get(1).checkJammed();

		System.out.println("Step " + step + ": ");
		System.out.println("Value p = " + getP());
		System.out.println("Value q = " + getQ());
		System.out.println("Active Item Quantity: " + items.size());
		System.out.println("Cheese Quantity: " + cheeseQuan);
		System.out.println("Blue Cheese Quantity: " + blueQuan);
		System.out.println("Soup Powder Quantity: " + soupQuan);
		System.out.println("Profit: " + profitCalculator.getProfit());
		System.out.println("Packed Count: " + profitCalculator.getPackedCount());
		System.out.println("Spoiled Count: " + profitCalculator.getSpoiledCount());
		System.out.println("Input Line 1 Size: " + inputLines.get(0).getQueueSize());
		System.out.println("Input Line 2 Size: " + inputLines.get(1).getQueueSize());
		System.out.println("Input Line 3 Size: " + inputLines.get(2).getQueueSize());
		System.out.println("Processor 1, Time Spent: " + processors.get(0).getTimeSpent());
		System.out.println("Processor 2, Time Spent: " + processors.get(1).getTimeSpent());
		System.out.println("Processor 3, Time Spent: " + processors.get(2).getTimeSpent());
		System.out.println("Packer Queue 1 Size: " + packerQueues.get(0).getQueueSize());
		System.out.println("Packer Queue 2 Size: " + packerQueues.get(1).getQueueSize());
		System.out.println("Packer 1, Time Spent: " + packers.get(0).getTimeSpent());
		System.out.println("Packer 1, Busy Status: " + packers.get(0).checkBusy());
		System.out.println("Packer 2, Time Spent: " + packers.get(1).getTimeSpent());
		System.out.println("Packer 2, Busy Status: " + packers.get(1).checkBusy());
		System.out.println(processor1);
		System.out.println(processor2);
		System.out.println(processor3);
		System.out.println(packer1);
		System.out.println(packer2);
		System.out.println();
	}
}