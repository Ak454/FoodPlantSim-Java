package System;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * A class responsible for testing the functionalities of the NonPerishableFood item.
 * @author
 * @version 18-04-2016
 */

public class NonPerishableItemTest {

    SoupPowder soupPowder;
    
    /**
     * Create a Non Perishable Food to test.
     */
    @Before
    public void createPerishableFood() {
        soupPowder = new SoupPowder();   
    }
    
    /**
     * Test that gets process time and returns process time of the non-perishable food object.
     */
    @Test
    public void testGetProcessTime() {
        assertEquals(22, soupPowder.getProcessTime());
        
    }   
}
