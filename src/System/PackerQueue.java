package System;
/**
 * A class that defines a particular type of QueueManager.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */

public class PackerQueue extends QueueManager {

	public PackerQueue() {
	}
	
	/**
	 * A method to return an Item from the queue.
	 * @param position the position of the item in the Queue.
	 * @return {Item} the retrieved item from the queue.
	 */
	public Item getItem (int position){
		return queue.get(position);
	}
}
