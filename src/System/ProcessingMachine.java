package System;
/**
 * A class that defines a particular type of Machine that packs items.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */
public class ProcessingMachine extends Machine{

	public ProcessingMachine(int processTime) {
		this.completeTime = processTime;
	}

	/**
	 * A method for returning whether the current action is complete
	 * @return {boolean} complete whether processing is complete.
	 */
	public boolean checkComplete(){
		if(timeSpent == completeTime){
			return true;
		}
		return false;
	}
	
	/**
	 * A method to set the process time of this machine.
	 * @param value the process time to be set.
	 */
	public void setProcessTime(int value) {
		completeTime = value;
	}
}

