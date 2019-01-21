/**
 * A class to test the functionality of the Machine Abstract class.
 * @author
 * @version 18-04-2016
 */

package System;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MachineTest {

	Machine processing;
	Machine packing;

	/**
	 * Set up the tests
	 */
	@Before
	public void setUp(){
		processing = new ProcessingMachine(10);
		packing = new PackingMachine();
	}

	/**
	 * A test to check if the item in the machine is complete.
	 */
	@Test 
	public void testCheckComplete() {
		//add 10 items
		processing.act(new Cheese());
		processing.act(new BlueCheese());
		processing.act(new SoupPowder());
		processing.act(new Cheese());
		processing.act(new BlueCheese());
		processing.act(new SoupPowder());
		processing.act(new Cheese());
		processing.act(new BlueCheese());
		processing.act(new SoupPowder());
		processing.act(new Cheese());
		//check if the time spent in the processing machine is correct and check if it is complete.
		assertEquals(processing.checkComplete(), true);
		assertEquals(processing.getTimeSpent(), 10);

		//add 10 items
		packing.act(new Cheese());
		packing.act(new BlueCheese());
		packing.act(new SoupPowder());
		packing.act(new Cheese());
		packing.act(new BlueCheese());
		packing.act(new SoupPowder());
		packing.act(new Cheese());
		packing.act(new BlueCheese());
		packing.act(new SoupPowder());
		packing.act(new Cheese());
		//check if the time spent in the packing machine is correct and check if it is complete.
		assertEquals(packing.checkComplete(), true);
		assertEquals(packing.getTimeSpent(), 10);
	}

	/**
	 * A test to check whether the process of acting on an item is complete
	 */
	@Test
	public void testAct() { 
		//add two items
		processing.act(new Cheese());
		processing.act(new BlueCheese());
		//check if the time spent in the processing machine is correct and check if it is busy.
		processing.getTimeSpent();
		assertEquals(processing.getTimeSpent(), 2);
		processing.checkBusy();
		assertEquals(processing.checkBusy(), true);

		//add two items
		packing.act(new Cheese());
		packing.act(new BlueCheese());
		//check if the time spent in the packing machine is correct and check if it is busy.
		packing.getTimeSpent();
		assertEquals(packing.getTimeSpent(), 2);
		packing.checkBusy();
		assertEquals(packing.checkBusy(), true);
	}

	/**
	 * A test to check if the machine is busy
	 */
	@Test
	public void testCheckBusy() {
		//add an item
		processing.act(new Cheese());
		//check if the processing machine is busy
		processing.checkBusy();
		assertEquals(processing.checkBusy(), true);

		//add an item
		packing.act(new Cheese());
		//check if the processing machine is busy
		packing.checkBusy();
		assertEquals(packing.checkBusy(), true);
	}

	/**
	 * A test to check if the item in the machine is finished
	 */
	@Test
	public void testgetFinishedItem() {
		processing.act(new Cheese());
		processing.getFinishedItem();
		assertEquals(processing.getFinishedItem(), null);
		packing.act(new Cheese());
		packing.getFinishedItem();
		assertEquals(packing.getFinishedItem(), null);
	}

	/**
	 * A test to set the finished item to null
	 */
	@Test
	public void testsetFinishedItemNull() {
		processing.act(new Cheese());
		processing.getFinishedItem();

		processing.setFinishedItemNull();
		assertEquals(processing.getFinishedItem(), null);
		packing.act(new Cheese());
		packing.getFinishedItem();

		packing.setFinishedItemNull();
		assertEquals(packing.getFinishedItem(), null);

	}
	/**
	 * A test to set the current item to null
	 */
	@Test
	public void testsetCurrentItemNull() {
		processing.act(new Cheese());
		processing.getFinishedItem();
		processing.setCurrentItemNull();
		assertEquals(processing.getCurrentItem(), null);
		packing.act(new Cheese());
		packing.getFinishedItem();
		packing.setCurrentItemNull();
		assertEquals(packing.getCurrentItem(), null);
	}
	/**
	 * A test to set the time spent to zero
	 */
	@Test
	public void testsetTimeZero() {
		processing.act(new Cheese());
		processing.setTimeZero();
		assertEquals(processing.getTimeSpent(), 0);

		packing.act(new Cheese());
		packing.setTimeZero();
		assertEquals(packing.getTimeSpent(), 0);
	}
	/**
	 * A test to check if the item in the machine is current
	 */
	@Test
	public void testgetCurrentItem() {
		Item cheese = new Cheese();
		processing.act(cheese);
		processing.getCurrentItem();
		assertEquals(processing.getCurrentItem(), cheese );
		Item cheese2 = new Cheese();
		packing.act(cheese2);
		packing.getCurrentItem();
		assertEquals(packing.getCurrentItem(), cheese2 );
	}

	/**
	 * A test to check if the time spent incremented by 1
	 */
	@Test
	public void testIncrementTimeSpent() {
		processing.act(new Cheese());
		processing.act(new BlueCheese());
		//check if the time spent in the processing machine is correct
		assertEquals(processing.getTimeSpent(), 2);
		for (int i = 0; i < 10; i++)
			processing.incrementTimeSpent();
		assertEquals(processing.getTimeSpent(), 12);

		packing.act(new Cheese());
		packing.act(new BlueCheese());
		//check if the time spent in the packing machine is correct
		assertEquals(packing.getTimeSpent(), 2);
		for (int i = 0; i < 10; i++)
			packing.incrementTimeSpent();
		assertEquals(packing.getTimeSpent(), 12);
	}

	/**
	 * A test to check if the item in the machine is complete
	 */
	@Test
	public void testComplete() {
		//check if current item equals null and the processing machine is not busy
		assertEquals(null, processing.getCurrentItem());
		assertEquals(false, processing.checkBusy());
		//create 1 item and check if it is complete
		processing.act(new Cheese());
		for(int i = 0; i < 9; i++){
			processing.incrementTimeSpent();
		}
		processing.complete();
		//Check if the processing machine has completed correctly
		assertEquals(null, processing.getCurrentItem());
		assertEquals(false, processing.checkBusy());
		assertEquals(0, processing.getTimeSpent());

		//check if current item equals null and machine not busy
		assertEquals(null, packing.getCurrentItem());
		assertEquals(false, packing.checkBusy());
		//create 1 item and check if it is complete
		packing.act(new Cheese());
		for(int i = 0; i < 9; i++){
			packing.incrementTimeSpent();
		}
		packing.complete();
		//Check if the processing machine has completed correctly
		assertEquals(null, packing.getCurrentItem());
		assertEquals(false, packing.checkBusy());
		assertEquals(0, packing.getTimeSpent());
	}

	@Test
	/**
	 * A test to check the getTimeSent method returns the correct time spent.
	 */
	public void testGetTimeSpent() {
		processing.act(new Cheese());
		processing.act(new BlueCheese());
		//check if the time spent in the processing machine is correct
		assertEquals(processing.getTimeSpent(), 2);

		packing.act(new Cheese());
		packing.act(new BlueCheese());
		//check if the time spent in the packing machine is correct
		assertEquals(packing.getTimeSpent(), 2);
	}

	@Test
	/**
	 * Test method for checking if a machine will jam.
	 */ 
	public void testJamMachine() {
		processing.jamMachine();
		//Check if the processing machine is jammed
		assertEquals(processing.checkJammed(), true);

		packing.jamMachine();
		//Check if the packing machine is jammed
		assertEquals(packing.checkJammed(), true);
	}

	@Test
	/**
	 * A test to check the machines are currently jammed.
	 */
	public void testCheckJammed() {
		processing.jamMachine();
		//Check if the processing machine is jammed.
		assertEquals(processing.checkJammed(), true);

		packing.jamMachine();
		//Check if the packing machine is jammed.
		assertEquals(packing.checkJammed(), true);
	}

	@Test
	/**
	 * Test method for checking the incrementation of a jam machine by 1.
	 */ 
	public void testAddJamTime() {
		processing.addJamTime();
		//Check if the processing machine is not jammed.
		assertEquals(processing.checkJammed(), false);

		for(int i = 0; i < 11; i++) {
			processing.addJamTime();
		}
		//Check if the amount of time the processing machine spent jammed is correct.
		assertEquals(processing.timeSpentJammed, 12);

		packing.addJamTime();
		//Check if the packing machine is not jammed
		assertEquals(packing.checkJammed(), false);

		for(int i = 0; i < 11; i++) {
			packing.addJamTime();
		}
		//Check if the amount of time the processing machine spent jammed is correct.
		assertEquals(packing.timeSpentJammed, 12);
	}
}

