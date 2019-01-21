package System;
/**
 * A class responsible for a SoupPowder object, which is a non-perishable food item, with a process
 * time of 22 seconds.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */
public class SoupPowder extends NonPerishableItem {
	
	//A static int value that specifies the time taken to process this NonPerishableFood item.
	private int processTime = 22;
	
	public SoupPowder() {
	}
	
	/**
	 * A method for returning the process time of this food.
	 * @return {int} processTime the time taken to process this food item.
	 */
	public int getProcessTime() {
		return processTime;
	}
	
	/**
	 * A method for setting the process time of the item.
	 * @param processTime the process time to be set.
	 */
	public void setProcessTime(int processTime){
		this.processTime = processTime;
	}

}
