package System;
/**
 * An interface responsible for formatting an Item object.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */
public interface Item {

	/**
	 * A method for returning the process time of the item.
	 * @return {int}
	 */
	public abstract int getProcessTime();
	
	/**
	 * A method for setting the process time of the item.
	 * @param processTime the process time to be set
	 */
	public abstract void setProcessTime(int processTime);
}
