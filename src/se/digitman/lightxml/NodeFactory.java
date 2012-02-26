package se.digitman.lightxml;

import java.io.BufferedReader;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;

/**
 * Creates XmlNode instances depending on the input data.
 * @author Thomas
 */
public class NodeFactory {

    private static final EntityManager entityManager = new EntityManager();

    /**
     * Create a single tag node
     * 
     * @param name The name of the tag
     * @return The resulting node object
     */
    public static XmlNode createNode(String name) {
        return new DefaultXmlNode(name);
    }

    /**
     * Create a single tag node with cdata
     * 
     * @param name The name of the tag
     * @param text The contents of the node
     * @return The resulting node object
     */
    public static XmlNode createNode(String name, String text) {
        return new DefaultXmlNode(name, text);
    }
    
    /**
     * Create a node hierarchy from a java.lang.Throwable cause stack
     * 
     * @param t The Throwable to convert
     * @return The resulting root node object
     */
    public static XmlNode createNode(Throwable t) {
        XmlNode result = new DefaultXmlNode("error");
        result.addAttribute("type", t.getClass().getName());
        result.addChild(new DefaultXmlNode("message", t.getMessage()));
        result.addChild(new DefaultXmlNode("source-file", t.getStackTrace()[0].getFileName()));
        result.addChild(new DefaultXmlNode("method", t.getStackTrace()[0].getMethodName()));
        result.addChild(new DefaultXmlNode("line", String.valueOf(t.getStackTrace()[0].getLineNumber())));
        if (t.getCause()!=null) {
            XmlNode cause = new DefaultXmlNode("cause");
            cause.addChild(createNode(t.getCause()));
            result.addChild(cause);
        }
        return result;
    }
    
    /**
     * Create a node hierarchy from a W3C DOM tree
     * 
     * @param input The DOM root node
     * @return The resulting root node object
     */
    public static XmlNode createNode(Node input) {
        try {
            // Set up the output transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(input);
            trans.transform(source, result);
            return new DocumentToXmlNodeParser(sw.toString()).parse();
        } catch (Exception ex) {
            Logger.getLogger(NodeFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }


    /**
     * Create a node hierarchy from a JDBC SQL result set
     * 
     * @param name The name of the root element
     * @param rs The result set from the JDBC query
     * @return The resulting root node object
     */
    public static XmlNode createNode(String name, ResultSet rs) {
        XmlNode result = NodeFactory.createNode(name);
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int rowCount = 1;
            while (rs.next()) {
                XmlNode row = NodeFactory.createNode("row");
                row.addAttribute("nbr", rowCount++);
                result.addChild(row);
                for (int columnIndex = 1; columnIndex <= metaData.getColumnCount(); columnIndex++) {
                    Object data = rs.getObject(columnIndex);
                    XmlNode column = NodeFactory.createNode(metaData.getColumnName(columnIndex));
                    for (XmlNode child : objectToXmlNodes(data)) {
                        column.addChild(child);
                    }
                    row.addChild(column);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(NodeFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private static List<XmlNode> stringToXmlNodes(String input) {
        XmlNode result = null;
        input = input.replaceAll("&amp;", "&").replaceAll("&", "&amp;");
        try {
            result = new DocumentToXmlNodeParser("<internal>" + input + "</internal>").parse();
        } catch (Exception e) {
        }
        if (result == null) {
            result = NodeFactory.createCdataNode(input);
        }
        return result.getChildren();
    }

    private static List<XmlNode> objectToXmlNodes(Object obj) {
        String result;
        if (obj == null) {
            result = "";
        } else if (obj instanceof Clob) {
            BufferedReader br;
            try {
                Clob cl = (Clob) obj;
                StringBuilder strOut = new StringBuilder();
                String aux;
                br = new BufferedReader(cl.getCharacterStream());
                while ((aux = br.readLine()) != null) {
                    strOut.append(aux);
                }
                br.close();
                result = strOut.toString();
            } catch (Exception ex) {
                Logger.getLogger(NodeFactory.class.getName()).log(Level.SEVERE, null, ex);
                throw new XmlException("CLOB read failure", ex);
            }
        } else {
            result = obj.toString();
        }
        return stringToXmlNodes(result);
    }

    /**
     * Creates a text node
     * @param text The text to be contained
     * @return The resulting text node
     */
    public static XmlNode createCdataNode(String text) {
        return new DefaultXmlNode(text, true);
    }

    /**
     * Factory method for getting the EntityManager instance to read pre-defined
     * entity expansion rules.
     * @return the entityManager
     */
    public static EntityManager getEntityManager() {
        return entityManager;
    }
}
