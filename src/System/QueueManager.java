package System;
import java.util.LinkedList;

/**
 * A class responsible for a QueueManager object that holds items waiting to be processed.
 * @author Liam Frailing and Akash Parmar
 * @version 18-04-2016
 */

public abstract class QueueManager {

	//A collection of items queuing to be passed to a machine.
	protected LinkedList<Item> queue;	

	public QueueManager(){
		queue = new LinkedList<Item>();
	}

	/**
	 * A method for adding a given item to the queue.
	 * @param f an item to be added to the queue.
	 */
	public void addQueueItem(Item f) {
		queue.add(f);
	}

	/**
	 * A method for retrieving the next item in the queue.
	 * @return {Item} item the next item in the queue.
	 */
	public Item getNextItem() {
		if(queue.size() > 0){
			return queue.remove();
		}
		return null;
	}

	/**
	 * A method for returning the size of the queue.
	 * @return {int} queueSize the size of the queue.
	 */
	public int getQueueSize() {
		return queue.size();
	}
	
	/**
	 * A method for returning the item in a given position of the queue.
	 * @param position the position of the Item in the queue.
	 * @return {Item} item the item retrieved from the queue.
	 */
	public abstract Item getItem (int position);
}
