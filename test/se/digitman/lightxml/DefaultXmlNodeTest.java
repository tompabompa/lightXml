package se.digitman.lightxml;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author brate
 */
public class DefaultXmlNodeTest {

    public DefaultXmlNodeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetName() {
        DefaultXmlNode node = new DefaultXmlNode("apa", "Sju sorters apor");
        String expResult = "apa";
        assertEquals(expResult, node.getName());
    }

    @Test
    public void testAddRemoveAttribute() {
        DefaultXmlNode node = new DefaultXmlNode("apa", "Sju sorters apor");
        node.addAttribute("klasar", 11);
        assertEquals("11", node.getAttribute("klasar"));
        node.addAttribute("klasar", 804);
        assertEquals("804", node.getAttribute("klasar"));
        node.addAttribute("typ", "Orangutang");
        assertEquals("Orangutang", node.getAttribute("typ"));
        node.addAttribute("typ", "Babian");
        assertEquals("Babian", node.getAttribute("typ"));
        node.removeAttribute("loppor");
        node.removeAttribute("typ");
    }

//
//    @Test
//    public void testAddChild() {
//        System.out.println("addChild");
//        XmlNode child = null;
//        DefaultXmlNode node = null;
//        node.addChild(child);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testHasChildNamed() {
//        System.out.println("hasChildNamed");
//        String name = "";
//        DefaultXmlNode node = null;
//        boolean expResult = false;
//        boolean result = node.hasChildNamed(name);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetChild() {
//        System.out.println("getChild");
//        String name = "";
//        DefaultXmlNode node = null;
//        XmlNode expResult = null;
//        XmlNode result = node.getChild(name);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetChildren_String() {
//        System.out.println("getChildren");
//        String name = "";
//        DefaultXmlNode node = null;
//        List expResult = null;
//        List result = node.getChildren(name);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetChildren_0args() {
//        System.out.println("getChildren");
//        DefaultXmlNode node = null;
//        List expResult = null;
//        List result = node.getChildren();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetXmlNode() {
//        System.out.println("getXmlNode");
//        String name = "";
//        DefaultXmlNode node = null;
//        XmlNode expResult = null;
//        XmlNode result = node.getXmlNode(name);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testRemoveChildren_Collection() {
//        System.out.println("removeChildren");
//        Collection<XmlNode> children = null;
//        DefaultXmlNode node = null;
//        node.removeChildren(children);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testRemoveChildren_String() {
//        System.out.println("removeChildren");
//        String name = "";
//        DefaultXmlNode node = null;
//        node.removeChildren(name);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testRemoveChild() {
//        System.out.println("removeChild");
//        XmlNode child = null;
//        DefaultXmlNode node = null;
//        node.removeChild(child);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetText() {
//        System.out.println("getText");
//        DefaultXmlNode node = null;
//        String expResult = "";
//        String result = node.getText();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testAddText() {
//        System.out.println("addText");
//        String text = "";
//        DefaultXmlNode node = null;
//        node.addText(text);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testSetParent() {
//        System.out.println("setParent");
//        XmlNode parent = null;
//        DefaultXmlNode node = null;
//        node.setParent(parent);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetParent() {
//        System.out.println("getParent");
//        DefaultXmlNode node = null;
//        XmlNode expResult = null;
//        XmlNode result = node.getParent();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testRemoveWhitespace() {
//        System.out.println("removeWhitespace");
//        Object s = null;
//        String expResult = "";
//        String result = DefaultXmlNode.removeWhitespace(s);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testExpandEntities() {
//        System.out.println("expandEntities");
//        Object s = null;
//        String expResult = "";
//        String result = DefaultXmlNode.expandEntities(s);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testToXml() {
//        System.out.println("toXml");
//        Object o = null;
//        XmlNode expResult = null;
//        XmlNode result = DefaultXmlNode.toXml(o);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testIsCdata() {
//        System.out.println("isCdata");
//        DefaultXmlNode node = null;
//        boolean expResult = false;
//        boolean result = node.isCdata();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testIsExpanded() {
//        System.out.println("isExpanded");
//        DefaultXmlNode node = null;
//        boolean expResult = false;
//        boolean result = node.isExpanded();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testSetExpanded() {
//        System.out.println("setExpanded");
//        boolean expanded = false;
//        DefaultXmlNode node = null;
//        node.setExpanded(expanded);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testWrite_OutputStream() {
//        System.out.println("write");
//        OutputStream out = null;
//        DefaultXmlNode node = null;
//        node.write(out);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testWriteAttributes() throws Exception {
//        System.out.println("writeAttributes");
//        Writer out = null;
//        DefaultXmlNode node = null;
//        node.writeAttributes(out);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testWrite_Writer() {
//        System.out.println("write");
//        Writer out = null;
//        DefaultXmlNode node = null;
//        node.write(out);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testToString() {
//        System.out.println("toString");
//        DefaultXmlNode node = null;
//        String expResult = "";
//        String result = node.toString();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetAttribute() {
//        System.out.println("getAttribute");
//        String name = "";
//        DefaultXmlNode node = null;
//        String expResult = "";
//        String result = node.getAttribute(name);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testHasAttribute() {
//        System.out.println("hasAttribute");
//        String name = "";
//        DefaultXmlNode node = null;
//        boolean expResult = false;
//        boolean result = node.hasAttribute(name);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetXmlNodeByPath() {
//        System.out.println("getXmlNodeByPath");
//        String path = "";
//        DefaultXmlNode node = null;
//        XmlNode expResult = null;
//        XmlNode result = node.getXmlNodeByPath(path);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testAddChildren() {
//        System.out.println("addChildren");
//        Collection<XmlNode> children = null;
//        DefaultXmlNode node = null;
//        node.addChildren(children);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testGetAttributes() {
//        System.out.println("getAttributes");
//        DefaultXmlNode node = null;
//        List expResult = null;
//        List result = node.getAttributes();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testClearText() {
//        System.out.println("clearText");
//        DefaultXmlNode node = null;
//        node.clearText();
//        fail("The test case is a prototype.");
//    }
//
//    @Test
//    public void testRemoveChildren_0args() {
//        System.out.println("removeChildren");
//        DefaultXmlNode node = null;
//        node.removeChildren();
//        fail("The test case is a prototype.");
//    }
}
