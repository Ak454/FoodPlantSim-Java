package System;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * A class responsible for testing the functionalities of the PerishableFood item.
 * @version 18-04-2016
 */

public class PerishableItemTest {

	Cheese cheese;
	BlueCheese blueCheese;

	/**
	 * Create the items, ready for testing.
	 */
	@Before
	public void createPerishableFood() {
		cheese = new Cheese();
		blueCheese = new BlueCheese();
	}

	/**
	 * Test that gets process time and returns process time of the perishable food object
	 */
	@Test
	public void testGetProcessTime() {
		assertEquals(20, cheese.getProcessTime());
		assertEquals(20, blueCheese.getProcessTime());
	}

	/**
	 * Test that gets spoil time and returns spoil time of perishable food object 
	 */
	@Test
	public void testGetSpoilTime() {
		assertEquals(45, cheese.getSpoilTime());
		assertEquals(40, blueCheese.getSpoilTime());

	}

	/**
	 * Test that increments spoil and increases spoil progress by 1
	 */
	@Test
	public void testIncrementSpoil() {
		for (int i = 0; i < 5; i++) {
			cheese.incrementSpoil();
			blueCheese.incrementSpoil();

		}

		assertEquals(5, cheese.getSpoilProgress());
		assertEquals(5, blueCheese.getSpoilProgress());


		for (int i = 0; i < 20; i++) {
			cheese.incrementSpoil();
			blueCheese.incrementSpoil();
		}

		assertEquals(25, cheese.getSpoilProgress());        
		assertEquals(25, blueCheese.getSpoilProgress());        

	}


	/**
	 * Test that checks spoil method and returns true if spoil progress is equal to spoil time
	 */
	@Test
	public void testCheckSpoilt() {
		assertEquals(false, cheese.checkSpoilt());
		assertEquals(false, blueCheese.checkSpoilt());        

		for (int i = 0; i < 40; i++) {
			cheese.incrementSpoil();
			blueCheese.incrementSpoil();
		}

		assertEquals(false, cheese.checkSpoilt());
		assertEquals(true, blueCheese.checkSpoilt());

		for (int i = 0; i < 5; i++) {
			cheese.incrementSpoil();
			blueCheese.incrementSpoil();
		}

		assertEquals(true, cheese.checkSpoilt());
		assertEquals(true, blueCheese.checkSpoilt());

	}

}

