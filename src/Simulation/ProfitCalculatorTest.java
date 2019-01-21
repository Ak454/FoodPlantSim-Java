package Simulation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * A class responsible for testing the functionalities of the ProfitCalculator.
 * 
 * @author Akash Parmar
 * @version 18-04-2016
 */

public class ProfitCalculatorTest {

	//A ProfitCalculator variable
	ProfitCalculator profitCalculator1;

	/**
	 * Initialise the ProfitCalculator variable.
	 */
	@Before
	public void setUp(){
		profitCalculator1 = new ProfitCalculator();
	}

	/**
	 * Test the incrementation of packed items
	 */
	@Test
	public void testIncrementPackedCount() {
		//Check if 0 packed items are counted
		assertEquals(profitCalculator1.getPackedCount(), 0);

		for(int i = 0; i < 12; i++){
			profitCalculator1.incrementPackedCount();
		}
		//Check if 12 packed items are counted
		assertEquals(profitCalculator1.getPackedCount(), 12);

		for(int i = 0; i < 120; i++){
			profitCalculator1.incrementPackedCount();
		}
		//Check if 132 packed items are counted
		assertEquals(profitCalculator1.getPackedCount(), 132);
	}

	/**
	 * Test the incrementation of spoiled items
	 */
	@Test
	public void testIncrementSpoiledCount() {
		//Check if 0 spoiled items are counted
		assertEquals(profitCalculator1.getSpoiledCount(), 0);

		for(int i = 0; i < 12; i++){
			profitCalculator1.incrementSpoiledCount();
		}
		//Check if 12 spoiled items are counted
		assertEquals(profitCalculator1.getSpoiledCount(), 12);

		for(int i = 0; i < 120; i++){
			profitCalculator1.incrementSpoiledCount();
		}
		//Check if 132 spoiled items are counted
		assertEquals(profitCalculator1.getSpoiledCount(), 132);
	}

	/**
	 * Test for counting the number of packed items.
	 */
	@Test
	public void testGetPackedCount() {
		profitCalculator1.incrementPackedCount();  
		//Check if the number of packed items is counted correctly
		assertEquals(profitCalculator1.getPackedCount(), 1); 
	}

	/**
	 * Test for returning profit produced.
	 */
	@Test
	public void testGetProfit() { 
		profitCalculator1.addProfit(10);
		assertEquals(profitCalculator1.getProfit(), 10);
		for(int i = 0; i < 11; i++) {
			profitCalculator1.addProfit(10);
		}
		//Check if profit has been added correctly
		assertEquals(profitCalculator1.getProfit(), 120);

		profitCalculator1.minusProfit(20);
		assertEquals(profitCalculator1.getProfit(), 100);
		for(int i = 0; i < 11; i++) {
			profitCalculator1.minusProfit(20);
		}
		//Check if profit has been minus correctly
		assertEquals(profitCalculator1.getProfit(), -120);
	}

	/**
	 * Test for profit increase.
	 */
	@Test
	public void testAddProfit() {
		profitCalculator1.addProfit(10);
		//Check if profit has been added correctly
		assertEquals(profitCalculator1.getProfit(), 10);
	}

	/**
	 * Test for profit reduction.
	 */
	@Test
	public void testMinusProfit() {
		profitCalculator1.minusProfit(20);
		//Check if profit has been minus correctly
		assertEquals(profitCalculator1.getProfit(), -20);
	}

	/**
	 * Test that profit is set to 0.
	 */
	@Test
	public void testZeroProfit(){
		for(int i = 0; i < 10; i++){
			profitCalculator1.addProfit(10);
		}
		assertEquals(profitCalculator1.getProfit(), 100);

		profitCalculator1.zeroProfit();
		assertEquals(profitCalculator1.getProfit(), 0);
	}

}

