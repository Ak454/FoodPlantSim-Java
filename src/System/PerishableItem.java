package System;
/**
 * A class responsible for a PerishableItem object, which is an Item, with a spoil
 * time and process time, to be specified.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */
public abstract class PerishableItem implements Item {
	
	//An int value that monitors the progress towards a PerishableItem item becoming spoiled.
	private int spoilProgress;
	
	//A boolean value that states whether the PerishableItem item is spoiled.
	protected boolean spoilt;
	
	public PerishableItem() {
		spoilProgress = 0;
		spoilt = false;
	}
	
	/**
	 * A method for returning the spoil time of this item.
	 * @return {int}
	 */
	public abstract int getSpoilTime();
	
	/**
	 * A method for returning the process time of this item.
	 * @return {int}
	 */
	public abstract int getProcessTime();
	
	/**
	 * A method for setting the process time of the item.
	 * @param processTime the process time to be set
	 */
	public abstract void setProcessTime(int processTime);
	
	/**
	 * A method for incrementing the value of spoilProgress, hence moving the given item object closer to becoming spoiled.
	 */
	public void incrementSpoil() {
		spoilProgress ++;
	}
	
	/**
	 * A method for returning whether the given item is spoiled.
	 * @return {boolean} spoilt whether the item is spoiled or not.
	 */
	public boolean checkSpoilt () {
		if (spoilProgress == getSpoilTime()) {
			spoilt = true;
		}
		return spoilt;
	}
	
	/**
	 * A method for returning the spoil progress of the Perishable Item item.
	 * @return {int} spoilProgress the progress towards an item becoming spoiled.
	 */
	public int getSpoilProgress(){
		return spoilProgress;
	}
}
