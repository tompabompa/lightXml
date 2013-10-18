/*
 * DefaultXmlNode.java
 *
 * Created on den 29 november 2005, 08:22
 */
package se.digitman.lightxml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import se.digitman.util.DateUtilities;

/**
 * Class implementing a general-purpose XML node representation to be
 * used in lightweight contexts, where DOM objects tend to be overwork.
 *
 * @author  Thomas Boqvist, Digit Man
 */
public class DefaultXmlNode implements XmlNode {

    private String name;
    private String cdata = "";
    private List<Attribute> attributes = new ArrayList<Attribute>();
    private List<XmlNode> children = new ArrayList<XmlNode>();
    private XmlNode parent = null;
    private boolean expanded = false;

    /**
     * Creates a new instance of DefaultXmlNode
     * @param name The name of the node (tag)
     */
    DefaultXmlNode(String name, boolean cdataNode) {
        this(cdataNode ? "" : name, cdataNode ? name : "");
    }

    /**
     * Creates a new instance of DefaultXmlNode
     * @param name The name of the node (tag)
     */
    DefaultXmlNode(String name) {
        this(name, false);
    }

    /**
     * Creates a new instance of DefaultXmlNode
     * @param name The name of the node (tag)
     * @param simpleTextChild The cdata to be contained
     */
    DefaultXmlNode(String name, String simpleTextChild) {
        this.name = removeWhitespace(name);
        addText(simpleTextChild);
    }

    /**
     * Gets the name of the node.
     * @return The name of the node
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Adds an attribute to the node. If the attribute does already exist
     * it will be overwritten with the new value.
     * @param attribute The attribute
     */
    @Override
    public void addAttribute(Attribute attribute) {
        if (attribute.getValue() != null) {
            removeAttribute(attribute.getKey());
            getAttributes().add(attribute);
        }
    }

    /**
     * Adds a string-based attribute to the node. If the attribute does already exist
     * it will be overwritten with the new value.
     * @param key The name of the attribute
     * @param value The value of the attribute
     */
    @Override
    public void addAttribute(String key, String value) {
        addAttribute(new Attribute(key, value));
    }

    /**
     * Adds a <code>Date</code>-based attribute to the node. If the attribute does already exist
     * it will be overwritten with the new value.
     * @param key The name of the attribute
     * @param value The value of the attribute
     */
    @Override
    public void addAttribute(String key, Date value) {
        addAttribute(new Attribute(key, new DateUtilities(value).toString()));
    }

    /**
     * Adds an int-based attribute to the node. If the attribute does
     * already exist it will be overwritten with the new value.
     * @param key The name of the attribute
     * @param value The value of the attribute
     */
    @Override
    public void addAttribute(String key, Integer value) {
        addAttribute(key, String.valueOf(value));
    }

    /**
     * Removes an attribute from the node. If the named attribute
     * does not exist, nothing happens.
     * @param key The name of the node to remove
     */
    @Override
    public void removeAttribute(String key) {
        Attribute toDelete = null;
        for (Attribute a : getAttributes()) {
            if (a.getKey().equals(key)) {
                toDelete = a;
                break;
            }
        }
        if (toDelete != null) {
            getAttributes().remove(toDelete);
        }
    }

    /**
     * Adds an XmlNode instance as a subnode to this instance.
     * @param child The subnode to add
     */
    @Override
    public void addChild(XmlNode child) {
        addChild(children.size(), child);
    }

    /**
     * Adds an XmlNode instance as a subnode to this instance, at the given index.
     * @param child The subnode to add
     */
    @Override
    public void addChild(int index, XmlNode child) {
        if (child != null) {
            if (index <= 0) {
                index = 0;
            } else if (index > children.size()) {
                index = children.size();
            }
            children.add(index, child);
            child.setParent(this);
        }
    }

    /**
     * Tells whether this DefaultXmlNode has an attached subnode 
     * with the given name.
     * @param name Name to check if exists
     * @return true if a subnode with the given name exists
     */
    @Override
    public boolean hasChildNamed(String name) {
        return getChild(name) != null;
    }

    /**
     * Gets a direct child node.
     * @param name The name of the child node to get
     * @return The first child with the given name; if no child with the given name
     *  is found, <code>null</code> is returned.
     */
    @Override
    public XmlNode getChild(String name) {
        for (XmlNode child : children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    /**
     * Gets a List of child nodes.
     * @param name The name of the child nodes to get
     * @return All child nodes with the given name; if no child with the given name
     *  is found, an empty List is returned.
     */
    @Override
    public List<XmlNode> getChildren(String name) {
        List<XmlNode> result = new ArrayList<XmlNode>();
        for (XmlNode o : children) {
            if (o.getName().equals(name)) {
                result.add(o);
            }
        }
        return result;
    }

    /**
     * Gets a List of all child nodes.
     * @return All child nodes as a List.
     */
    @Override
    public List<XmlNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    /**
     * Gets a child node by unlimited recursion.
     * @param name The name of the child node to get
     * @return The first child with the given name in the recursion tree;
     * if no child with the given name is found, <code>null</code> is returned.
     */
    @Override
    public XmlNode getXmlNode(String name) {
        if (getName().equals(name)) {
            return this;
        } else {
            for (XmlNode o : children) {
                XmlNode child = o.getXmlNode(name);
                if (child != null) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * Removes any children matching the instances in the <code>children</code>.
     * parameter.
     * @param children The collection holding the nodes to remove
     */
    @Override
    public void removeChildren(Collection<XmlNode> children) {
        for (XmlNode node : children) {
            removeChild(node);
        }
    }

    /**
     * Removes any children matching the name parameter.
     * @param name The name of the child nodes to remove
     */
    @Override
    public void removeChildren(String name) {
        removeChildren(getChildren(name));
    }

    /**
     * Removes one child matching the instance <code>child</code>. If the
     * <code>child</code> parameter does not match any child node, nothing
     * will happen.
     * @param child The child to remove.
     */
    @Override
    public void removeChild(XmlNode child) {
        children.remove(child);
        child.setParent(null);
    }

    /**
     * Gets all text belonging to this node.
     * @return The text data contained in this node.
     */
    @Override
    public String getText() {
        String result;
        if (isCdata()) {
            result = cdata;
        } else {
            String tmp = "";
            for (XmlNode child : children) {
                tmp += child.getText();
            }
            result = tmp;
        }
        return result != null ? result : "";
    }

    /**
     * Adds text data to a node.
     * @param text The text to add.
     */
    @Override
    public final void addText(String text) {
        if (text != null && text.length() > 0) {
            if (isCdata()) {
                cdata += collapseEntities(removeUnprintableCharacters(text));
            } else {
                addChild(new DefaultXmlNode(text, true));
            }
        }
    }

    private String removeUnprintableCharacters(String input) {
        return input.replaceAll("\\p{C}", " ");
    }
    
    @Override
    public void setParent(XmlNode parent) {
        this.parent = parent;
    }

    /**
     * @return The parent node or <code>null</code> if this node is a root node.
     */
    @Override
    public XmlNode getParent() {
        return parent;
    }

    /**
     * Removes whitespace from strings
     * @param s <code>Object</code> which <code>toString()</code> - representation
     * should be stripped from whitespace characters
     * @return A <code>String</code> object containing the converted data
     */
    protected static String removeWhitespace(Object s) {
        if (s == null) {
            return "";
        }
        return s.toString().replaceAll(" ", "-").
                replaceAll("\t", "_");
    }

    /**
     * Expands entities to make character based content parseable by external parser
     * @param s <code>Object</code> which <code>toString()</code> - representation
     * should be transformed
     * @return A <code>String</code> object containing the converted CDATA
     */
    protected static String expandEntities(Object s) {
        return NodeFactory.getEntityManager().expandEntities(s);
    }

    /**
     * Collapses common entities to its original representing token
     * @param s <code>Object</code> which <code>toString()</code> - representation
     * should be transformed
     * @return A <code>String</code> object containing the converted CDATA
     */
    private static String collapseEntities(Object s) {
        return NodeFactory.getEntityManager().collapseEntities(s.toString());
    }

    /**
     * Reads an <CODE>Object</CODE> by reflection, storing any available properties
     * in a <CODE>DefaultXmlNode</CODE> instance. This is an easy way to convert
     * transfer objects into a common, serializable format.
     * @param o The object to convert.
     * @return A <CODE>DefaultXmlNode</CODE> instance representing the properties of the parameter
     */
    public static XmlNode toXml(Object o) {
        try {
            if (o == null) {
                return null;
            }
            XmlNode result = new DefaultXmlNode(o.getClass().getName());
            Field[] fields = o.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getType().getSimpleName().endsWith("TO")) {
                    result.addChild(toXml(fields[i].get(o)));
                } else if (fields[i].getType().isAssignableFrom(List.class)) {
                    DefaultXmlNode list = new DefaultXmlNode(fields[i].getName());
                    list.addAttribute("type", "List");
                    if (fields[i].get(o) != null) {
                        for (Iterator iter = ((List) fields[i].get(o)).iterator(); iter.hasNext();) {
                            list.addChild(toXml(iter.next()));
                        }
                        result.addChild(list);
                    }
                } else if (!(o instanceof String)) {
                    String value = fields[i].get(o) != null ? fields[i].get(o).toString() : "";
                    result.addChild(new DefaultXmlNode(fields[i].getName(), value));
                }
            }
            return result;
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Illegal argument", ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Illegal access", ex);
        }
    }

    /**
     * @return <code>true</code> if this node is a text node.
     */
    @Override
    public boolean isCdata() {
        return getName().length() == 0;
    }

    /**
     * Getter for property expanded.
     * @return Value of property expanded.
     */
    @Override
    public boolean isExpanded() {
        return this.expanded;
    }

    /**
     * Setter for property expanded.
     * @param expanded New value of property expanded.
     */
    @Override
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    /**
     * Writes the contents of this node to an output stream.
     * @param out The stream to write
     */
    @Override
    public void write(OutputStream out) {
        write(new OutputStreamWriter(out));
    }

    protected void writeAttributes(Writer out) throws IOException {
        for (Attribute attribute : getAttributes()) {
            out.write(" " + attribute);
        }
    }

    /**
     * Writes the contents of this node to a Writer object.
     * @param out The Writer to use
     */
    @Override
    public void write(Writer out) {
        try {
            if (isCdata()) {
                out.write(expandEntities(getText()));
                return;
            }
            out.write("<" + name);
            writeAttributes(out);
            if (children.isEmpty()) {
                out.write(isExpanded() ? "></" + name + ">" : "/>");
            } else {
                out.write(">");
                for (XmlNode child : children) {
                    try {
                        child.write(out);
                    } catch (Exception e) {
                        out.write("<error>" + expandEntities(e.getMessage()) + "</error>");
                    }
                }
                out.write("</" + name + ">");
            }
        } catch (IOException ex) {
            throw new XmlException("Writing XML failed", ex);
        }
    }

    /**
     * Returns the XML data of this DefaultXmlNode as a String.
     */
    @Override
    public String toString() {
        StringWriter result = new StringWriter();
        write(result);
        return result.getBuffer().toString();
    }

    /**
     * @param name The name of the attribute
     * @return The value of the attribute as a String, if no attribute with the
     * given name exists, <code>null</code> is returned.
     */
    @Override
    public String getAttribute(String name) {
        for (Attribute a : getAttributes()) {
            if (a.getKey().equals(name)) {
                return a.getValue();
            }
        }
        return null;
    }

    /**
     * @param name The attribute to look for
     * @return <code>true</code> if the node has an attribute with the given name
     */
    @Override
    public boolean hasAttribute(String name) {
        return getAttribute(name) != null;
    }

    /**
     * Finds and retrieves an XmlNode instance by recursing the path argument.
     * @param path The path to the node as an Xpath expression.
     * @return Ne node matching the path, or <code>null</code> if no node matched.
     */
    @Override
    public XmlNode getXmlNodeByPath(String path) {
        String[] parts = path.split("/");
        if (path.startsWith("/")) {
            if (getParent() != null) {
                return getParent().getXmlNodeByPath(path);
            } else if (getName().equals(parts[1])) {
                return getXmlNodeByPath(path.substring(1 + Math.min(path.length() - 1, parts[1].length() + 1)));
            } else {
                return null;
            }
        }
        if (parts.length == 0 || (parts.length == 1 && (parts[0].equals(".") || parts[0].length() == 0))) {
            return this;
        }
        if (parts[0].equals("..")) {
            if (getParent() != null) {
                return getParent().getXmlNodeByPath(path.substring(Math.min(path.length(), 3)));
            } else {
                return null;
            }
        }
        if (hasChildNamed(parts[0])) {
            return getChild(parts[0]).getXmlNodeByPath(path.substring(Math.min(path.length(), parts[0].length() + 1)));
        } else {
            return null;
        }
    }

    /**
     * Adds a collection of XmlNodes as children to this node.
     * @param children The collection of children to add
     */
    @Override
    public void addChildren(Collection<XmlNode> children) {
        for (XmlNode node : children) {
            addChild(node);
        }
    }

    /**
     * Adds a collection of XmlNodes as children to this node, starting at the given index.
     * @param children The collection of children to add
     */
    @Override
    public void addChildren(int index, Collection<XmlNode> children) {
        if (index < 0) {
            index = 0;
        }
        for (XmlNode node : children) {
            addChild(index++, node);
        }
    }

    /**
     * @return The List of attributes belonging to this node
     */
    protected List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * Removes all text from this node.
     */
    @Override
    public void clearText() {
        List<XmlNode> childCopy = new ArrayList<XmlNode>(getChildren());
        for (XmlNode child : childCopy) {
            if (child.isCdata()) {
                removeChild(child);
            }
        }
    }

    /**
     * Removes all children from this node.
     */
    @Override
    public void removeChildren() {
        children.clear();
    }
}
