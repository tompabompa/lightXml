package se.digitman.util;

import java.util.Date;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author thomas
 */
public class DateUtilitiesTest {

    public DateUtilitiesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testEquality() {
        assertEquals(new DateUtilities(new Date()), new DateUtilities(new Date()));
        assertFalse(new DateUtilities(new Date()).equals(new DateUtilities(2009, 12,10)));
    }

    @Test
    public void testDiffInDays() {
        assertEquals(0, new DateUtilities(new Date()).getDiffInDays(new Date()));
        assertEquals(-1, new DateUtilities(2010, 11, 30).getDiffInDays(new DateUtilities(2010, 12, 1).get()));
        assertEquals(1, new DateUtilities(2010, 12, 1).getDiffInDays(new DateUtilities(2010, 11, 30).get()));
        assertEquals(-3652, new DateUtilities(2010, 12, 1).getDiffInDays(new DateUtilities(2020, 11, 30).get()));
    }

    @Test
    public void testFieldExtraction() {
        DateUtilities testObject = new DateUtilities(2011, 4, 1);
        assertEquals(2011, testObject.getYear());
        assertEquals(4, testObject.getMonth());
        assertEquals(1, testObject.getDay());
    }

    @Test
    public void testStringValues() {
        DateUtilities testObject = new DateUtilities(2001, 7, 1);
        assertEquals("2001-07-01", testObject.toString());
        assertEquals("juli", testObject.getMonthName());
        assertEquals("söndag", testObject.getWeekday());
        testObject = testObject.addDays(-1);
        assertEquals("lördag", testObject.getWeekday());
        testObject = testObject.addDays(2);
        assertEquals("måndag", testObject.getWeekday());
    }

    @Test
    public void testArithmetics() {
        DateUtilities testObject = new DateUtilities(2011, 7, 1);
        testObject = testObject.addYears(-3);
        assertEquals(2008, testObject.getYear());
        testObject = testObject.addMonths(7);
        assertEquals(2009, testObject.getYear());
        assertEquals(2, testObject.getMonth());
        testObject = testObject.addDays(-1);
        assertEquals(1, testObject.getMonth());
        assertEquals(31, testObject.getDay());
    }
}