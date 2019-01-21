package Simulation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import System.BlueCheese;
import System.Cheese;
import System.PackingMachine;
import System.PerishableItem;
import System.ProcessingMachine;
import System.SoupPowder;


/**
 * A class to test the Simulator works as desired.
 * @author Akash Parmar
 * @version 18-04-2016
 */

public class SimulatorTest {
	Simulator s;
	Simulator s1;
	ProcessingMachine processor;
	PackingMachine packer;

	/**
	 * Set up the system for testing.
	 */
	@Before
	public void createSystem(){
		s = new Simulator(42);
		s.setP(0.01);
		s.setQ(0.01);

		s1 = new Simulator(42);
		s1.setP(0.01);
		s1.setQ(0.01);
		s1.setNumProcessors(1);
		s1.setNumPackers(1);
		s1.initialiseSystem();

		processor = s1.getProcessor(0);
		packer = s1.getPacker(0);
	}

	/**
	 * Test that the number of machines and respective queue's created are correct, using the initialiseSystem method.
	 */
	@Test
	public void initialiseSystemTest(){
		//Test default - Should use default, where numProcessors = 3 and numPackers = 2, therefore creating 5 machines and 5 queues.
		assertEquals(5, s.getMachineQuantity());
		assertEquals(5, s.getQueueQuantity());

		//Test positive - Should use numProcessors = 15 and numPackers = 8, therefore creating 23 machines and 23 queues
		s.setNumProcessors(15);
		s.setNumPackers(8);
		s.initialiseSystem();
		assertEquals(23, s.getMachineQuantity());
		assertEquals(23, s.getQueueQuantity());

		//Test negative - Should use default, where numProcessors = 3 and numPackers = 2, therefore creating 5 machines and 5 queues.
		s.setNumProcessors(-1);
		s.setNumPackers(-24);
		s.initialiseSystem();
		assertEquals(5, s.getMachineQuantity());
		assertEquals(5, s.getQueueQuantity());	
	}

	/**
	 * Test that the createItem method creates Item when the provided double is less the creation probability.
	 */
	@Test
	public void createItemTest() {

		//An approach to counting the quantity of each of the 3 Item items.
		int cheeseQuan = 0;
		int blueQuan = 0;
		int soupQuan = 0;

		//Create 1 Cheese
		s.createItem(0.005);

		//Create 2 BlueCheese
		s.createItem(0.015);
		s.createItem(0.015);

		//Create 3 SoupPowder
		s.createItem(0.026);
		s.createItem(0.028);
		s.createItem(0.026);

		//Create 0 of anything - Testing negatives
		s.createItem(-0.9);
		s.createItem(-0.001);
		s.createItem(-0.015);

		for(int i = 0; i < s.getItemsSize(); i++){
			if(s.getItem(i) instanceof Cheese){
				cheeseQuan ++;
			}
			if(s.getItem(i) instanceof BlueCheese){
				blueQuan ++;
			}
			if(s.getItem(i) instanceof SoupPowder){
				soupQuan ++;
			}
		}
		assertEquals(1, cheeseQuan);
		assertEquals(2, blueQuan);
		assertEquals(3, soupQuan);
	}


	/**
	 * A test to check that the removeSpoiled() method will remove spoiled items from the system and leave unspoiled ones in the system.
	 */
	@Test 
	public void testRemoveSpoiled(){
		//Create 1 Item
		s.createItem(0.005);

		assertEquals(1, s.getItemsSize());

		//No items are spoiled, so should not be removed.
		//Hence there should be 6 Item items still.
		s.removeSpoiled();
		assertEquals(1, s.getItemsSize());

		//Set 1 Item item to be spoiled
		PerishableItem Item = ((PerishableItem) s.getItem(0));
		for(int i = 1; i < 45; i++){
			Item.incrementSpoil();
		}
		assertEquals(true, Item.checkSpoilt());

		//Remove 1 Item item and check it's been removed
		s.removeSpoiled();
		assertEquals(0, s.getItemsSize());	

		//Create 2 of each Item
		s.createItem(0.005);
		s.createItem(0.005);
		s.createItem(0.015);
		s.createItem(0.015);
		s.createItem(0.026);
		s.createItem(0.026);

		//Set all possible Item items to be spoiled
		PerishableItem Item1 = ((PerishableItem) s.getItem(0));
		for(int i = 1; i < 46; i++){
			Item1.incrementSpoil();
		}

		PerishableItem Item2 = ((PerishableItem) s.getItem(1));
		for(int i = 1; i < 46; i++){
			Item2.incrementSpoil();
		}

		PerishableItem Item3 = ((PerishableItem) s.getItem(2));
		for(int i = 1; i < 41; i++){
			Item3.incrementSpoil();
		}

		PerishableItem Item4 = ((PerishableItem) s.getItem(3));
		for(int i = 1; i < 41; i++){
			Item4.incrementSpoil();
		}

		//Remove all spoiled Item items and check they have been removed. Should leave the 2 unspoiled SoupPowder's.
		s.removeSpoiled();
		assertEquals(2, s.getItemsSize());	
	}

	/**
	 * A test to check that the operateProcessors method will call completeProcessing, only when the machine is not jammed, otherwise
	 * it will perform 'jammed activities'.
	 */
	@Test
	public void testOperateProcessors(){

		//Check current Item equals null and machine not busy
		assertEquals(null, processor.getCurrentItem());
		assertEquals(false, processor.checkBusy());

		//Create 1 Item item and simulate one step
		s1.createItem(0.005);
		s1.simulateOneStep();

		//After simulating 1 step, Current Item in processor1 should now equal Item item 1.
		assertEquals(s1.getItem(0), processor.getCurrentItem());

		//Check machine is now busy after simulating 1 step.
		assertEquals(true, processor.checkBusy());

		//Check time spent equals 1 after simulating 1 step.
		assertEquals(1, processor.getTimeSpent());

		//Jam the processingMachine, check jamTime equals 1.
		processor.jamMachine();
		assertEquals(1, processor.getJamTime());

		//Simulate 17 steps. Jam time should now equal 18 and currentItem should equal null.
		for(int i = 0; i < 17; i++){
			s1.simulateOneStep();
		}
		assertEquals(18, processor.getJamTime());
		assertEquals(null, processor.getCurrentItem());

		//Simulate 43 steps. Jam time should now equal 0 and currentItem should still equal null.
		for(int i = 0; i < 43; i++){
			s1.simulateOneStep();
		}
		assertEquals(0, processor.getJamTime());
		assertEquals(null, processor.getCurrentItem());

		//Simulate one more step and make sure the processor has been reset to default
		s1.simulateOneStep();
		assertEquals(false, processor.checkBusy());
		assertEquals(0, processor.getTimeSpent());
		assertEquals(null, processor.getCurrentItem());
		assertEquals(0, processor.getJamTime());
		assertEquals(null, processor.getFinishedItem());

		//Create another Item item for processing and check it's been created correctly
		s1.createItem(0.005);
		s1.simulateOneStep();
		assertEquals(s1.getItem(0), processor.getCurrentItem());
		assertEquals(true, processor.checkBusy());
		assertEquals(1, processor.getTimeSpent());

		//Simulate 20 steps until processing is complete and item has been passed on.     
		for(int x = 0; x < 20; x++){
			s1.simulateOneStep();
		}

		//Check processing has completed correctly and the item has been moved straight in to the empty packer.
		assertEquals(s1.getItem(0), s1.getPacker(0).getCurrentItem());
		assertEquals(0, processor.getTimeSpent());
		assertEquals(null, processor.getCurrentItem());
		assertEquals(0, processor.getJamTime());
	}

	/**
	 * A test to check the completeProcessing method will only get the next Item item stored in each InputLine and add it to the 
	 * corresponding ProcessingMachine, if required and available.
	 */
	@Test
	public void testCompleteProcessing(){

		//Check current Item equals null and machine not busy
		assertEquals(null, processor.getCurrentItem());
		assertEquals(false, processor.checkBusy());

		//Create 1 Item item and simulate one step
		s1.createItem(0.005);
		s1.simulateOneStep();

		//After simulating 1 step, Current Item in processor1 should now equal Item item 1.
		assertEquals(s1.getItem(0), processor.getCurrentItem());

		//Check machine is now busy after simulating 1 step.
		assertEquals(true, processor.checkBusy());

		//Check time spent equals 1 after simulating 1 step.
		assertEquals(1, processor.getTimeSpent());

		//Simulate 10 more steps
		for(int i = 0; i < 10; i++){
			s1.simulateOneStep();
		}

		//Check that time spent equals 11 after simulating 11 steps. Also check currentItem still equals Item 1, 
		//check that processor still has busy status and that finishedItem equals null.
		assertEquals(11, processor.getTimeSpent());
		assertEquals(s1.getItem(0), processor.getCurrentItem());
		assertEquals(true, processor.checkBusy());
		assertEquals(null, processor.getFinishedItem());

		//Simulate 9 more steps
		for(int i = 0; i < 10; i++){
			s1.simulateOneStep();
		}

		//Check processing has completed correctly and the item has been moved straight in to the empty packer.
		assertEquals(s1.getItem(0), packer.getCurrentItem());
		assertEquals(0, processor.getTimeSpent());
		assertEquals(null, processor.getCurrentItem());
		assertEquals(0, processor.getJamTime());
	}

	/**
	 * A test to check that the operatePackerQueues method will move a processed Item item to the packerQueue with the smallest
	 * size, only when a processed Item item is available.
	 */
	@Test
	public void testOperatePackerQueues(){
		s1.setNumProcessors(3);
		s1.setNumPackers(2);
		s1.initialiseSystem();

		//Create 2 of each Item item
		s1.createItem(0.005);
		s1.createItem(0.025);
		assertEquals(2, s1.getItemsSize());

		//Simulate 4 steps and create a BlueCheese item, creating a staggered arrival to packers
		for(int i = 0; i < 4; i++){
			s1.simulateOneStep();
		}
		s1.createItem(0.015);
		assertEquals(3, s1.getItemsSize());

		//Simulate 21 steps so that Item item 1 is moved on to the first packer
		for(int i = 0; i < 17; i++){
			s1.simulateOneStep();
		}

		//Check that Item item 1 (Cheese) has been moved to the first packer
		assertEquals(s1.getItem(0), s1.getPacker(0).getCurrentItem());
		assertEquals(null, s1.getPacker(1).getCurrentItem());

		//Simulate 3 more steps so that Item items 1 and 2 are moved on to the packers
		for(int i = 0; i < 3; i++){
			s1.simulateOneStep();
		}

		//Check that Item item 2 (Soup) is added to packer 2, the packer with the smallest queue size.
		assertEquals(s1.getItem(0), s1.getPacker(0).getCurrentItem());
		assertEquals(s1.getItem(1), s1.getPacker(1).getCurrentItem());

		//Simulate 3 more steps so that Item item 3 is moved to the packerQueue with the smallest size
		//Both are the same size so should go to packer 1 as default
		for(int i = 0; i < 3; i++){
			s1.simulateOneStep();
		}
		assertEquals(1,s1.getPackerQueue(0).getQueueSize());
	}

	/**
	 * A test to check that the operatePackers method will call completePacking, only when the machine is not jammed, otherwise
	 * it will perform 'jammed activities'.
	 */
	@Test
	public void testOperatePackers(){
		//Check current Item equals null and machine not busy
		assertEquals(null, processor.getCurrentItem());
		assertEquals(false, processor.checkBusy());

		//Create 1 Item item and simulate 20 steps to move the Item item to the packer.
		s1.createItem(0.005);
		for(int i = 0; i < 21; i++){
			s1.simulateOneStep();
		}

		//After simulating 20 steps, Current Item in processor1 should now equal null and machine should not be busy.
		assertEquals(null, processor.getCurrentItem());
		assertEquals(false, processor.checkBusy());

		//Check packer has acquired Cheese product, is marked busy and time spent is 1.
		assertEquals(s1.getItem(0), packer.getCurrentItem());
		assertEquals(true, packer.checkBusy());
		assertEquals(1, packer.getTimeSpent());

		//Jam the packingMachine, check jamTime equals 1.
		packer.jamMachine();
		assertEquals(1, packer.getJamTime());

		//Simulate 17 steps. Jam time should now equal 18 and currentItem should equal null.
		for(int i = 0; i < 17; i++){
			s1.simulateOneStep();
		}
		assertEquals(18, packer.getJamTime());
		assertEquals(null, packer.getCurrentItem());

		//Simulate 43 steps. Jam time should now equal 0 and currentItem should still equal null.
		for(int i = 0; i < 43; i++){
			s1.simulateOneStep();
		}
		assertEquals(0, packer.getJamTime());
		assertEquals(null, packer.getCurrentItem());

		//Simulate one more step and make sure the processor has been reset to default
		s1.simulateOneStep();
		assertEquals(false, packer.checkBusy());
		assertEquals(0, packer.getTimeSpent());
		assertEquals(null, packer.getCurrentItem());
		assertEquals(0, packer.getJamTime());
		assertEquals(null, packer.getFinishedItem());

		//Create another Item item and simulate 20 steps to move the Item item to the packer.
		s1.createItem(0.005);
		for(int i = 0; i < 21; i++){
			s1.simulateOneStep();
		}

		//After simulating 20 steps, Current Item in processor1 should now equal null and machine should not be busy.
		assertEquals(null, processor.getCurrentItem());
		assertEquals(false, processor.checkBusy());

		//Check packer has acquired Cheese product, is marked busy and time spent is 1.
		assertEquals(s1.getItem(0), packer.getCurrentItem());
		assertEquals(true, packer.checkBusy());
		assertEquals(1, packer.getTimeSpent());

		//Simulate 10 steps until packing is complete.      
		for(int x = 0; x < 10; x++){
			s1.simulateOneStep();
		}

		//Check packing has completed correctly and the item has been moved out.
		assertEquals(0, packer.getTimeSpent());
		assertEquals(null, packer.getCurrentItem());
		assertEquals(0, packer.getJamTime());
	}

	/**
	 * A test to check the completePacking method will only get the next Item item stored in each PackerQueue and add it to the 
	 * corresponding PackingMachine, if required and available.
	 */
	@Test
	public void testCompletePacking(){
		//Check current Item equals null and machine not busy
		assertEquals(null, packer.getCurrentItem());
		assertEquals(false, packer.checkBusy());

		//Create 1 Item item and simulate 20 steps to move the Item item to the packer.
		s1.createItem(0.005);
		for(int i = 0; i < 21; i++){
			s1.simulateOneStep();
		}

		//After simulating 20 steps, Current Item in processor1 should now equal null and machine should not be busy.
		assertEquals(null, processor.getCurrentItem());
		assertEquals(false, processor.checkBusy());

		//Check Current Item in packer1 equals Item item 1 and packer is busy, with a current time spent of 1..
		assertEquals(s1.getItem(0), packer.getCurrentItem());
		assertEquals(true, packer.checkBusy());
		assertEquals(1, packer.getTimeSpent());

		//Simulate 5 more steps
		for(int i = 0; i < 5; i++){
			s1.simulateOneStep();
		}

		//Check that time spent equals 5 after simulating 5 steps. Also check currentItem still equals Item 1, 
		//check that processor still has busy status and that finishedItem equals null.
		assertEquals(6, packer.getTimeSpent());
		assertEquals(s1.getItem(0), packer.getCurrentItem());
		assertEquals(true, packer.checkBusy());
		assertEquals(null, packer.getFinishedItem());

		//Simulate 5 more steps
		for(int i = 0; i < 5; i++){
			s1.simulateOneStep();
		}

		//Check packing has completed correctly and the item has been moved straight out.
		assertEquals(null, packer.getCurrentItem());
		assertEquals(0, processor.getTimeSpent());
		assertEquals(0, processor.getJamTime());
	}
}
