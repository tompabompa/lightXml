package se.digitman.lightxml;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import javax.naming.OperationNotSupportedException;

/**
 * Implementation of XmlNode that utilizes the prolog node behaviour.
 * A PrologNode must only be added to an XmlDocument instance, never to a
 * XmlNode instance.
 * @author Thomas Boqvist, Digit Man
 */
public class PrologNode extends DefaultXmlNode {

    /**
     * Creates a prolog node
     * @param name The node name
     */
    public PrologNode(String name) {
        super(name);
    }

    /**
     * @throws XmlException This method must not be called on a PrologNode
     */
    @Override
    public void addChild(XmlNode child) {
        throw new XmlException("A prolog node can not contain child nodes", new OperationNotSupportedException("Not supported"));
    }

    /**
     * @throws XmlException This method must not be called on a PrologNode
     */
    @Override
    public void addChildren(Collection<XmlNode> children) {
        throw new XmlException("A prolog node can not contain child nodes", new OperationNotSupportedException("Not supported"));
    }

    @Override
    public void write(Writer out) {
        try {
            out.write("<?" + getName());
            writeAttributes(out);
            out.write("?>");
        } catch (IOException ex) {
            throw new XmlException("Writing XML failed", ex);
        }
    }


}
