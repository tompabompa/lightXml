package se.digitman.lightxml;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author brate
 */
public class DefaultXmlNodeTest {

    private DefaultXmlNode instance;
    
    @Before
    public void setUp() {
        instance = new DefaultXmlNode("test-node");
    }

    @Test
    public void testGetName() {
        String expResult = "test-node";
        assertEquals(expResult, instance.getName());
    }

    @Test
    public void testAddRemoveAttribute() {
        instance.addAttribute("klasar", 11);
        assertEquals("11", instance.getAttribute("klasar"));
        instance.addAttribute("klasar", 804);
        assertEquals("804", instance.getAttribute("klasar"));
        instance.addAttribute("typ", "Orangutang");
        assertEquals("Orangutang", instance.getAttribute("typ"));
        instance.addAttribute("typ", "Babian");
        assertEquals("Babian", instance.getAttribute("typ"));
    }


    @Test
    public void testAddChild() {
        instance.addChild(NodeFactory.createNode("KENNY"));
        assertEquals("KENNY", instance.getChildren().get(0).getName());
    }

    @Test
    public void testHasChildNamed() {
        instance.addChild(NodeFactory.createNode("KENNY"));
        assertTrue(instance.hasChildNamed("KENNY"));
        assertFalse(instance.hasChildNamed("BRACK"));
    }

    @Test
    public void testGetChild() {
        instance.addChild(NodeFactory.createNode("KENNY"));
        assertEquals(instance.getChild("KENNY").getName(), "KENNY");
        assertNull(instance.getChild("BRACK"));
    }

    @Test
    public void testGetChildren() {
        assertEquals(0, instance.getChildren().size());
        instance.addChild(NodeFactory.createNode("KENNY"));
        instance.addChild(NodeFactory.createNode("BRACK"));
        assertEquals(2, instance.getChildren().size());
        assertEquals("KENNY", instance.getChildren().get(0).getName());
    }

    @Test
    public void testCharacterFiltering() {
        instance.addText("No\u001Funprintables!");
        assertEquals("No unprintables!", instance.getText());
        instance.addChild(NodeFactory.createNode("K EN NY2"));
        assertEquals(1, instance.getChildren("K-EN-NY2").size());
    }

}
