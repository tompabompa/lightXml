/*
 * DocumentToXmlNodeParser.java
 *
 * Created on den 6 oktober 2007, 09:23
 *
 */
package se.digitman.lightxml;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Used to convert different XML representations to a lightXml node tree.
 * @author Thomas Boqvist, Digit Man
 */
public class DocumentToXmlNodeParser extends DefaultHandler {

    private XmlNode result = null;
    private Reader inputReader = null;
    private String currentCharacters = "";
    private Map<String, ? extends ContentResolver> contentResolvers = null;

    /**
     * Creates a new instance of DocumentToXmlNodeParser from String data
     * @param xmlInput The XML String
     */
    public DocumentToXmlNodeParser(String xmlInput) {
        inputReader = new StringReader(xmlInput);
    }

    /**
     * Creates a new instance of DocumentToXmlNodeParser from an InputStream
     * @param xmlInput The XML containing input stream
     */
    public DocumentToXmlNodeParser(InputStream xmlInput) {
        inputReader = new InputStreamReader(xmlInput);
    }

    /**
     * Creates a new instance of DocumentToXmlNodeParser from a Reader
     * @param xmlInput The XML containing Reader
     */
    public DocumentToXmlNodeParser(Reader xmlInput) {
        inputReader = xmlInput;
    }

    public void setContentResolverMap(Map<String, ? extends ContentResolver> contentResolvers) {
        this.contentResolvers = contentResolvers;
    }

    /**
     * Performs the parsing and outputs the resulting XmlNode tree.
     * @return The resulting XmlNode tree
     */
    public XmlNode parse() {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(this);
            reader.setEntityResolver(new EntityResolver() {

                @Override
                public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
                    return new InputSource(new StringReader(""));
                }
            });
            reader.parse(new InputSource(inputReader));
        } catch (SAXParseException ex) { // Return XML built so far!
        } catch (Exception ex) {
            throw new XmlException("XML parsing exception", ex);
        }
        return result;
    }
    private XmlNode currentObject = null;
    private XmlNode replacementObject = null;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        String[] qNameParts = qName.split(":");
        if (qNameParts.length > 1 && qNameParts[0].equals("subst"))  {
            if (contentResolvers != null && contentResolvers.keySet().contains(localName)) {
                ContentResolver resolver = contentResolvers.get(localName);
                resolver.setConditions(attributes);
                replacementObject = resolver.getReplacement();
            } else {
                throw new XmlException("Resolver for local name '" + localName + "' is missing", null);
            }
        } else {
            XmlNode newXmlNode = NodeFactory.createNode(localName);
            if (result == null) {
                result = newXmlNode;
            }
            if (currentObject != null) {
                flushCurrentCharacters();
                currentObject.addChild(newXmlNode);
            }
            currentObject = newXmlNode;
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getLocalName(i);
                String value = attributes.getValue(i);
                currentObject.addAttribute(name, value);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentObject != null) {
            flushCurrentCharacters();
            if (currentObject.getParent() != null) {
                if (contentResolvers != null && contentResolvers.keySet().contains(localName)) {
                    currentObject.addChild(replacementObject);
                } else {
                    currentObject = currentObject.getParent();
                }
            }
        }
    }

    @Override
    public void characters(char[] c, int i, int i0) throws SAXException {
        String text = new String(c, i, i0);
        if (text.length() > 0) {
            currentCharacters = currentCharacters.concat(text);
        }
    }

    private void flushCurrentCharacters() {
        if (currentCharacters.length() > 0) {
            currentObject.addText(currentCharacters);
        }
        currentCharacters = "";
    }
//    public static void main(String[] args) {
//        String input="<test name=\"rydifer\"><result>OLGA!</result><result>OLGA!</result><noone arg=\"hhh\">zingaticker...</noone><message><part1>First</part1><part2>Second</part2></message></test>";
//        // InputStream input = DocumentToXmlNodeParser.class.getClassLoader().getResourceAsStream("se/blinfo/skalman/blawsClient/mock/debugXml.xml");
//        XmlNode test = new DocumentToXmlNodeParser(input).parse();
//        System.out.println(test);
//        System.out.println(test.getXmlNodeByPath("result"));
//        XmlNode message = test.getXmlNode("part1");
//        System.out.println(message.getXmlNodeByPath(".."));
//        System.out.println(message.getXmlNodeByPath("../../noone"));
//        System.out.println(message.getXmlNodeByPath("/test").getAttribute("name"));
//    }
}
