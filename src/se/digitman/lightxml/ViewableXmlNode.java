/*
 * ViewableXmlNode.java
 *
 * Created on den 3 januari 2007, 15:55
 *
 */

package se.digitman.lightxml;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.tree.TreeNode;

/**
 * Implementation of XmlNode that complies to the Swing TreeNode interface.
 * @author Thomas
 */
public abstract class ViewableXmlNode extends DefaultXmlNode implements TreeNode {
    
    /**
     * Creates a new instance of ViewableXmlNode
     * @param name 
     */
    public ViewableXmlNode(String name) {
        super(name);
    }

    public TreeNode getChildAt(int childIndex) {
        return (TreeNode)getChildren().toArray()[childIndex];
    }

    public int getChildCount() {
        return getChildren().size();
    }

    @Override
    public ViewableXmlNode getParent() {
        return (ViewableXmlNode)super.getParent();
    }

    public int getIndex(TreeNode node) {
        int count=0;
        for (Iterator i=getChildren().iterator(); i.hasNext(); count++) {
            if (i.next().equals(node)) {
                return count;
            }
        }
        return -1;
    }

    public boolean getAllowsChildren() {
        return !isLeaf();
    }

    public abstract boolean isLeaf();
    
    public Enumeration children() {
        return new Vector(getChildren()).elements();
    }
    
}
