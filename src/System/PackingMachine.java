package System;
/**
 * A class that defines a particular type of Machine that packs items.
 * @author Akash Parmar
 * @version 18-04-2016
 */
public class PackingMachine extends Machine{
	
	private int packingTime = 10;

	public PackingMachine() {
	}

	/**
	 * A method for returning whether the current action is complete
	 * @return {boolean}
	 */
	public boolean checkComplete(){
		if(timeSpent == packingTime){
			return true;
		}
		return false;
	}
	
	/**
	 * A method for setting the packing time
	 * @param value the packing time to be set.
	 */
	public void setPackingTime(int value){
		this.packingTime = value;
	}
}

