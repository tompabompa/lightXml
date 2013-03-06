package se.digitman.lightxml;

import java.io.StringWriter;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thomas
 */
public class PrologNodeTest {

    private PrologNode instance;

    public PrologNodeTest() {
    }

    @Before
    public void setUp() {
        instance = new PrologNode("test-node");
    }

    @Test
    public void testAddChild() {
        XmlNode child = null;
        try {
            instance.addChild(child);
            fail("addChild should generate exception");
        } catch (XmlException ex) {
        }
    }

    @Test
    public void testAddChildren() {
        Collection<XmlNode> children = null;
        try {
            instance.addChildren(children);
            fail("addChildren should generate exception");
        } catch (XmlException ex) {
        }
    }

    @Test
    public void testWrite() {
        StringWriter out = new StringWriter();
        instance.write(out);
        assertEquals(out.toString(), "<?test-node?>");
    }
}