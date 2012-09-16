package se.digitman.lightxml;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of XmlNode that utilizes the document type declaration behaviour.
 * A DocumentTypeDeclaration must only be added to an XmlDocument instance, never to a
 * XmlNode instance.
 * @author hl
 */
public class DocumentTypeDeclaration {

    private IdentifierDeclaration identifierDeclaration;
    private String publicIdentifier;
    private String dtd;
    private XmlDocument document;
    private Map<String, String> entityMap = new HashMap<String, String>();

    /**
     * @return the identifierDeclaration
     */
    public IdentifierDeclaration getIdentifierDeclaration() {
        return identifierDeclaration;
    }

    /**
     * @param identifierDeclaration the identifierDeclaration to set
     */
    public void setIdentifierDeclaration(IdentifierDeclaration identifierDeclaration) {
        this.identifierDeclaration = identifierDeclaration;
    }

    /**
     * @return the publicIdentifier
     */
    public String getPublicIdentifier() {
        return publicIdentifier;
    }

    /**
     * @param publicIdentifier the publicIdentifier to set
     */
    public void setPublicIdentifier(String publicIdentifier) {
        this.publicIdentifier = publicIdentifier;
    }

    /**
     * @return the dtd
     */
    public String getDtd() {
        return dtd;
    }

    /**
     * @param dtd the dtd to set
     */
    public void setDtd(String dtd) {
        this.dtd = dtd;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(XmlDocument document) {
        this.document = document;
    }

    /**
     * @return the entityMap
     */
    public Map<String, String> getEntityMap() {
        return entityMap;
    }

    /**
     * @param entityMap the entityMap to set
     */
    public void setEntityMap(Map<String, String> entityMap) {
        this.entityMap = entityMap;
    }

    public void addEntity(String key, String value) {
        entityMap.put(key, value);
    }

    public void write(Writer out) {
        try {
            out.write("<!DOCTYPE " + document.getRoot().getName());
            if (getIdentifierDeclaration() != null) {
                out.write(" " + identifierDeclaration.name());
            }
            if (getPublicIdentifier() != null && !getPublicIdentifier().equals("")) {
                out.write(" \"" + getPublicIdentifier() + "\"");
            }
            if (getDtd() != null && !getDtd().equals("")) {
                out.write(" \"" + getDtd() + "\"");
            }
            if (!getEntityMap().isEmpty()) {
                out.write(" [");
                for (String key : getEntityMap().keySet()) {
                    out.write(" <!ENTITY " + key + " \"" + getEntityMap().get(key) + "\"> ");
                }
                out.write("]");
            }
            out.write(">");
        } catch (IOException ex) {
            throw new XmlException("Writing XML failed", ex);
        }
    }

    @Override
    public String toString() {
        StringWriter result = new StringWriter();
        write(result);
        return result.getBuffer().toString();
    }

    public enum IdentifierDeclaration {

        PUBLIC,
        SYSTEM;
    }
}
