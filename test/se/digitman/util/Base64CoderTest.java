package se.digitman.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author thomas
 */
public class Base64CoderTest {

    @Test
    public void testDecodeString() {
        System.out.println("encodeString");
        String s = "Zeb Macahan rocks!";
        String result = Base64Coder.encodeString(s);
        result = Base64Coder.decodeString(result);
        assertEquals(s, result);
    }

}
