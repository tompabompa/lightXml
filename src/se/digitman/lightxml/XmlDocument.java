/*
 * XmlDocument.java
 *
 * Created on den 3 oktober 2007, 16:33
 *
 */
package se.digitman.lightxml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * The XmlDocument Is the holder of prolog nodes and the root node for the
 * containing node tree. By using a XmlDocument instance when rendering XML
 * data, the result will be a complete XML document.
 * @author thomas
 */
public class XmlDocument {

    private final String version = "1.0";
    private final XmlNode root;
    private DocumentTypeDeclaration doctypeDeclaration;
    private List<PrologNode> prologNodes = new ArrayList<PrologNode>();

    /**
     * Creates a new instance of XmlDocument
     * @param root the root node of the document
     */
    public XmlDocument(XmlNode root) {
        if (root == null || root.isCdata()) {
            throw new XmlException("Invalid root node", null);
        }
        this.root = root;
        PrologNode head = new PrologNode("xml");
        head.addAttribute("version", version);
        prologNodes.add(head);
    }

    /**
     * Getter for the root XmlNode of the document
     * @return The root node
     */
    public XmlNode getRoot() {
        return root;
    }

    @Override
    public String toString() {
        StringWriter result = new StringWriter();
        write(result);
        return result.getBuffer().toString();
    }

    /**
     * Adds a prolog node to this document
     * @param pNode The node to add
     */
    public void addPrologNode(PrologNode pNode) {
        prologNodes.add(pNode);
    }

    /**
     * Adds a document type declaration to this document
     * @param doctypeDecl The doctypeDeclaration to add
     */
    public void addDocumentTypeDeclaration(DocumentTypeDeclaration doctypeDecl) {
        doctypeDeclaration = doctypeDecl;
        doctypeDeclaration.setDocument(this);
    }

    /**
     * Getter for the document head
     * @return The head prolog node of the document; the one containing
     * &lt;?XML version=...&gt;
     */
    public PrologNode getHead() {
        return prologNodes.get(0);
    }

    /**
     * Writes the document to a Writer instance
     * @param out The Writer to write the contents to
     */
    public void write(Writer out) {
        try {
            for (PrologNode node : prologNodes) {
                out.write(node + "\n");
            }
            if (doctypeDeclaration != null) {
                out.write(doctypeDeclaration + "\n");
            }
            out.write(String.valueOf(getRoot()));
        } catch (IOException ex) {
            throw new XmlException("Writing XML failed", ex);
        }
    }

    /**
     * Returns the document as a readable InputStream.
     * @param encoding String representation of encoding to use;
     * @return The document as a readable InputStream.
     */
    public InputStream asInputStream(String encoding) {
        try {
            getHead().addAttribute("encoding", encoding);
            return new ByteArrayInputStream(toString().getBytes(encoding));
        } catch (UnsupportedEncodingException ex) {
            throw new XmlException("Unsupported encoding", ex);
        }
    }

}
