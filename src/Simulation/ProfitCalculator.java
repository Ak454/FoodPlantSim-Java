package Simulation;

/**
 * A class for monitoring the profitability of a system.
 * 
 * @author Akash Parmar
 * @version 18-04-2016
 */

public class ProfitCalculator {
	
	//An int value stating the profit made by a system.
	private int profit;
	
	//An int value stating the number of items that have been packed.
	private int packedCount; 
	
	//An int value stating the number of items that have spoiled.
	private int spoiledCount;

	public ProfitCalculator(){
		profit = 0;
		packedCount = 0;
		spoiledCount = 0;
	}

	/**
	 * A method that increments the number of packed items by 1.
	 */
	public void incrementPackedCount()  {
		packedCount ++;
	}

	/**
	 * A method that increments the number of spoiled items by 1.
	 */
	public void incrementSpoiledCount(){
		spoiledCount ++;
	}

	/**
	 * A method that returns the number of items that have been packed.
	 * @return {int} packedCount the number of items that have been packed.
	 */
	public int getPackedCount() {
		return packedCount;
	}
	
	/**
	 * A method returning the number of items that have spoiled.
	 * @return {int} spoiledCount the number of spoiled items.
	 */
	public int getSpoiledCount(){
		return spoiledCount;
	}

	/**
	 * A method returning the profit made by the system.
	 * @return {int} profit the profit made by a system.
	 */
	public int getProfit() {
		return profit;
	}

	/**
	 * A method for incrementing profit by 10.
	 * @param amount the amount to add on to the total profit.
	 */
	public void addProfit(int amount) {
		profit += amount;
	}

	/**
	 * A method for decrementing profit by 20.
	 * @param amount the amount to take from the total profit.
	 */
	public void minusProfit(int amount) {
		profit -= amount;
	}
	
	/**
	 * A method for resetting profit to 0.
	 */
	public void zeroProfit(){
		this.profit = 0;
	}
}
