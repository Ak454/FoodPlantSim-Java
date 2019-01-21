package System;
/**
 * An abstract class responsible for a Machine object, which is any machine that takes an item
 * holds it for a period of time, while an action occurs and then passes the item on.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */
public abstract class Machine {

	//A value used to monitor the amount of time spent acting on an Item.
	protected int timeSpent;

	//A boolean value to state whether the machine is busy or not.
	protected boolean isBusy;

	//An Item variable that holds finished items until they are retrieved. 
	protected Item finishedItem;

	//An Item variable that holds the item that is currently being acted on.
	protected Item currentItem;

	//An int variable, which specifies the amount of time it takes to complete the machines activities
	//on one item, from start to finish.
	protected int completeTime;

	//A boolean value to state whether the machine is jammed or not.
	protected boolean jammed;

	//An int variable to count how long a jammed machine has been jammed for.
	protected int timeSpentJammed;

	//An int variable that states how long a machine will jam for.
	private int totalJamTime = 60;

	public Machine(){
		timeSpent = 0;
		isBusy = false;
		finishedItem = null;
		jammed = false;
	}

	/**
	 * An abstract method for returning the whether an action is complete.
	 * @return {boolean} 
	 */
	public abstract boolean checkComplete();

	/**
	 * A method for beginning the process of acting on an Item.
	 * @param item an item to be acted upon by the machine.
	 */
	public void act(Item item) {
		this.currentItem = item;
		this.timeSpent ++;
		isBusy = true;
	}

	/**
	 * A method for checking whether the machine is busy.
	 * @return {boolean} isBusy whether the machine is busy.
	 */
	public boolean checkBusy() {
		return isBusy;
	}

	/**
	 * A method for returning the finished Item.
	 * @return {Item} finishedItem a finished item.
	 */
	public Item getFinishedItem() {
		return finishedItem;
	}

	/**
	 * A method for setting the value of finishedItem to null.
	 */
	public void setFinishedItemNull(){
		this.finishedItem = null;
	}

	/**
	 * A method for setting the value of currentItem to null.
	 */
	public void setCurrentItemNull(){
		this.currentItem = null;
	}

	/**
	 * A method for setting the time spent on an Item, back to 0.
	 */
	public void setTimeZero(){
		this.timeSpent = 0;
	}

	/**
	 * A method returning the current Item being acted upon.
	 * @return {Item} currentItem a item being acted upon.
	 */
	public Item getCurrentItem(){
		return currentItem;
	}

	/**
	 * A method for incrementing the time spent acting on a item by 1.
	 */
	public void incrementTimeSpent(){
		this.timeSpent ++;
	}

	/**
	 * A method for completing the action that occurs on an Item.
	 */
	public void complete(){

		if(checkComplete() == false){
			throw new UnsupportedOperationException("The action has not yet been completed.");
		}	

		this.finishedItem = this.currentItem;
		this.currentItem = null;
		this.timeSpent = 0;
		isBusy = false;
	}

	/**
	 * A method to return the amount of time spent acting on an item.
	 * @return {int} timeSpent the stime spent acting on the current item.
	 */
	public int getTimeSpent(){
		return timeSpent;
	}

	/**
	 * A method for jamming the machine.
	 */
	public void jamMachine(){
		if(this.jammed == false){
			this.jammed = true;
			this.timeSpentJammed = 1;
		}
	}

	/**
	 * A method for returning whether the given machine is currently jammed.
	 * @return {boolean} jammed the current jam status of the machine.
	 */
	public boolean checkJammed(){
		if(timeSpentJammed == totalJamTime){
			this.jammed = false;
			timeSpentJammed = 0;
			this.isBusy = false;
		}
		return jammed;
	}

	/**
	 * A method for incrementing the time a machine has spent jammed by 1.
	 */
	public void addJamTime(){
		this.timeSpentJammed ++;
	}

	/**
	 * A method for setting the total jam time..
	 * @param jamTime the time this machine will remain jammed.
	 */
	public void setJamTime(int jamTime){
		this.totalJamTime = jamTime;
	}
	
	/**
	 * A method for setting the total jam time.
	 * @return {int} timeSpentJammed the time this machine has spent jammed.
	 */
	public int getJamTime(){
		return timeSpentJammed;
	}

	/**
	 * A method for returning the completion time.
	 * @return {int} completeTime the time taken for this machine to complete it's activities on 1 item.
	 */
	public int getCompleteTime(){
		return completeTime;
	}

}

