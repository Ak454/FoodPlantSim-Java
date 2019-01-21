package System;
/**
 * A class responsible for a NonPerishableItem object, which is an Item, with a process time,
 * to be specified.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */
public abstract class NonPerishableItem implements Item {
	
	public NonPerishableItem() {
	}
	
	/**
	 * A method for returning the process time of this item.
	 * @return {int} 
	 */
	public abstract int getProcessTime();
	
	/**
	 * A method for setting the process time of the item.
	 * @param processTime the process time to be set.
	 */
	public abstract void setProcessTime(int processTime);

}
