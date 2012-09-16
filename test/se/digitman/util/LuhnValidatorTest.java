package se.digitman.util;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author thomas
 */
public class LuhnValidatorTest {

    public LuhnValidatorTest() {
    }

    @Test
    public void testIsValid() {
        assertTrue(new LuhnValidator("641002-8515").isValid());
        assertFalse(new LuhnValidator("123456").isValid());
    }
}
