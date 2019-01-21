package System;
/**
 * A class responsible for an InputLine object, which is QueueManager that holds items waiting 
 * to be processed.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */

public class InputLine extends QueueManager {

	public InputLine() {
	}
	
	/**
	 * A method for returning the item in a given position of the InputLine's queue.
	 * @param position the position of the Item in the queue.
	 * @return {Item} item the item retrieved from the queue.
	 */
	public Item getItem (int position){
		return queue.get(position);
	}
}
