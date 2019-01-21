package System;
/**
 * A class responsible for a BlueCheese object, which is a perishable food item, with a spoil
 * time of 40 seconds and process time of 20 seconds.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */
public class BlueCheese extends PerishableItem {

	//A static int value that specifies the time taken to process this PerishableFood item.
	private int processTime = 20;
	
	//A static int value that specifies the time taken for this PerishableFood item to spoil.
	private static final int spoilTime = 40;

	public BlueCheese() {
	}

	/**
	 * A method for returning the process time of this food.
	 * @return {int} processTime the process time for this food item.
	 */
	public int getProcessTime(){
		return processTime;
	}

	/**
	 * A method for returning the process time of this food.
	 * @return {int} spoilTime the spoil time for this food item.
	 */
	public int getSpoilTime() {
		return spoilTime;
	}
	
	/**
	 * A method for setting the process time of the item.
	 * @param processTime the process time to be set for this food.
	 */
	public void setProcessTime(int processTime){
		this.processTime = processTime;
	}

}
