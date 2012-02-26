package se.digitman.lightxml;

import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * The main interface of the library, defining the expected behaviour of an
 * implementing node.
 * 
 * @author Thomas
 */
public interface XmlNode extends Serializable {

    /**
     * Adds an attribute to the node. If the attribute does already exist
     * it will be overwritten with the new value.
     * @param attribute The attribute
     */
    public void addAttribute(Attribute attribute);

    /**
     * Adds a string-based attribute to the node. If the attribute already exists
     * it will be overwritten with the new value.
     * @param key The name of the attribute
     * @param value The value of the attribute
     */
    public void addAttribute(String key, String value);

    /**
     * Adds a <code>Date</code>-based attribute to the node. If the attribute already exists
     * it will be overwritten with the new value.
     * @param key The name of the attribute
     * @param value The value of the attribute
     */
    public void addAttribute(String key, Date value);

    /**
     * Adds an int-based attribute to the node. If the attribute does
     * already exist it will be overwritten with the new value.
     * @param key The name of the attribute
     * @param value The value of the attribute
     */
    public void addAttribute(String key, Integer value);

    /**
     * Adds an XmlNode instance as a subnode to this instance.
     * @param child The subnode to add
     */
    public void addChild(XmlNode child);

    /**
     * Adds an XmlNode instance as a subnode at the given index to this instance.
     * @param child The subnode to add
     */
    public void addChild(int index, XmlNode child);

    /**
     * Adds a collection of XmlNode instances as subnode to this instance.
     * @param child The subnode to add
     */
    public void addChildren(Collection<XmlNode> child);

    /**
     * Adds a collection of XmlNode instances as subnodes at the given index to this instance.
     * @param child The subnode to add
     */
    public void addChildren(int index, Collection<XmlNode> child);

    public XmlNode getChild(String name);
    
    /**
     * Adds text data to this node. Existing data will be concatenated.
     * @param text The text data to add
     */
    public void addText(String text);

    /**
     * Removes all CDATA child nodes from this XmlNode instance
     */
    public void clearText();

    /**
     * Removes all child nodes from this XmlNode instance
     */
    public void removeChildren();

    /**
     * Returns named children of this node as a collection of XmlNode:s.
     * All children named as the parameter will be returned in a collection.
     * @param name The name to look for among the children
     * @return A collection of the nodes matched. If no match is found, an empty
     * collection will be returned.
     */
    public List<XmlNode> getChildren(String name);

    /**
     * Returns all children of this node as a collection of XmlNode:s.
     * @return A list of all child nodes matched. If no children exists, an empty
     * collection will be returned.
     */
    public List<XmlNode> getChildren();

    /**
     * Gets the name of the node.
     * @return The name of the node
     */
    public String getName();

    /**
     * @return The parent node of this node, or null if this node is a tree root.
     */
    public XmlNode getParent();

    /**
     * @return The text data from this node. If no text data exists, an empty 
     * String is returned.
     */
    public String getText();
    
    /**
     * Retrieves the string value of an attribute in this node.
     * @param name The name of the attribute
     * @return The attributes value, or null if no attribute with the given
     * name exists.
     */
    public String getAttribute(String name);

    /**
     * Tells if an attribute exists for this node
     * @param name The name of the attribute
     * @return <code>true</code> if the attribute exists
     */
    public boolean hasAttribute(String name);
    
    /**
     * Recursively searches the node tree from this node, looking for a match
     * of the given node name.
     * @param name The name of the subnode to find
     * @return The first matching resulting XmlNode, or null if no node with the
     * given name was found.
     */
    public XmlNode getXmlNode(String name);

    /**
     * Recursively walks the node tree from this node, looking for a match
     * of the given node path.
     * @param path The tree path to pick a node from
     * @return The first path-matching resulting XmlNode, or null if no node at the
     * given path was found.
     */
    public XmlNode getXmlNodeByPath(String path);

    /**
     * Tells whether this XmlNode has an attached subnode with the given
     * name.
     * @param name Name to check if exists
     * @return true if a subnode with the given name exists
     */
    public boolean hasChildNamed(String name);

    /**
     * @return <code>true</code> if this node is a text data node.
     */
    public boolean isCdata();

    /**
     * Getter for property expanded.
     * @return Value of property expanded.
     */
    public boolean isExpanded();

    /**
     * Removes an attribute from the node. If the named attribute
     * does not exist, nothing happens.
     * @param key The name of the node to remove
     */
    public void removeAttribute(String key);

    /**
     * Removes one child matching the instance <code>child</code>. If the
     * <code>child</code> parameter does not match any child node, nothing
     * will happen.
     * @param child The child to remove.
     */
    public void removeChild(XmlNode child);

    /**
     * Removes any children matching the instances in the <code>children</code>
     * parameter.
     * @param children The collection holding the nodes to remove
     */
    public void removeChildren(Collection<XmlNode> children);

    /**
     * Removes any children matching the name parameter.
     * @param name The name of the child nodes to remove
     */
    public void removeChildren(String name);

    /**
     * Setter for property expanded.
     * @param expanded New value of property expanded.
     */
    public void setExpanded(boolean expanded);

    void setParent(XmlNode parent);

    /**
     * Writes the contents of this node to an output stream.
     * @param out The stream to write
     */
    public void write(OutputStream out);

    /**
     * Writes the contents of this node to a Writer object.
     * @param out The Writer to use
     */
    public void write(Writer out);
}
