package System;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * A class to test the functionality of the QueueManager Abstract class.
 * @version 18-04-2016
 */

public class QueueManagerTest {
	QueueManager item;

	/**
	 * Create a QueueManager for testing.
	 */
	@Before
	public void setUp(){
		item = new PackerQueue();
	}
	
	/**
	 * A test to check if two items have been added to the Queue.
	 * It will check if the queue size is two.
	 */
	@Test
	public void testAddQueueItem() {
		item.addQueueItem(new Cheese());
		item.addQueueItem(new BlueCheese());
		assertEquals(2, item.getQueueSize());
	}

	/**
	 * A test to check if the queue retrieves the next item.
	 */
	@Test
	public void testGetNextItem() {
		item.addQueueItem(new Cheese());
		item.addQueueItem(new BlueCheese());
		item.addQueueItem(new SoupPowder());
		assertEquals(item.getQueueSize(), 3);
		item.getNextItem();
		assertEquals(item.getQueueSize(), 2);
	}

	/**
	 * A test to check the size of the Queue.
	 */
	@Test
	public void testGetQueueSize() {
		item = new PackerQueue();
		item.addQueueItem(new Cheese());
		item.addQueueItem(new BlueCheese());
		assertEquals(2, item.getQueueSize());
	}
}