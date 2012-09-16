package se.digitman.lightxml;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brate
 */
public class AttributeTest {

    @Test
    public void testGetKey() {
        Attribute attribute = new Attribute("apa", "Sju sorters apor");
        String expResult = "apa";
        assertEquals(expResult, attribute.getKey());
    }

    @Test
    public void testGetValue() {
        Attribute attribute = new Attribute("apa", "Sju sorters apor");
        String expResult = "Sju sorters apor";
        assertEquals(expResult, attribute.getValue());
    }

    @Test
    public void testToString() {
        Attribute attribute = new Attribute("apa", "Sju sorters apor");
        String expResult = "apa=\"Sju sorters apor\"";
        String result = attribute.toString();
        assertEquals(expResult, attribute.toString());
    }
}
