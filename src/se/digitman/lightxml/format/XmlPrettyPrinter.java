/*
 * XmlPrettyPrinter.java
 *
 * Created on den 5 oktober 2007, 13:28
 *
 */
package se.digitman.lightxml.format;

import java.io.StringWriter;
import se.digitman.lightxml.XmlDocument;
import se.digitman.lightxml.XmlNode;

/**
 * Used to format an XML output string, to make it human readable.
 * @author Thomas Boqvist, Digit Man
 */
public class XmlPrettyPrinter {

    private int tabsize = 2;
    private boolean endTag = false;
    private String xmlData;

    /**
     * Creates an XmlPrettyPrinter from a String object
     * @param xmlData The XML to format as a String
     */
    public XmlPrettyPrinter(String xmlData) {
        this.xmlData = xmlData;
    }

    /**
     * Creates an XmlPrettyPrinter from an XmlNode
     * @param xml The root node object to print
     */
    public XmlPrettyPrinter(XmlNode xml) {
        this(xml.toString());
    }

    /**
     * Creates an XmlPrettyPrinter from an XmlNode
     * @param xml The XmlDocument object to print
     */
    public XmlPrettyPrinter(XmlDocument xml) {
        this(xml.toString());
    }

    private String newline(int tab) {
        String result = "\n";
        for (int i = 0; i < tab; i++) {
            result += ' ';
        }
        return result;
    }

    /**
     * Performs the actual processing of the contained XML data.
     * @return The formatted XML result as a String
     */
    public String process() {
        char prevChar = ' ',  nextChar = ' ',  token = ' ';
        int tab = -2;
        StringWriter sw = new StringWriter();
        for (int i = 0; i < xmlData.length(); i++) {
            token = xmlData.charAt(i);
            if (i < xmlData.length() - 1) {
                nextChar = xmlData.charAt(i + 1);
            }
            if (token == '<') {
                if (nextChar != '/' && nextChar != '?') {
                    tab += getTabsize();
                }
                if (prevChar == '>' && nextChar != '/' || endTag) {
                    sw.write(newline(tab));
                }
                if (nextChar == '/') {
                    tab -= getTabsize();
                }
                endTag = false;
            } else if (token == '/') {
                if (prevChar == '<' || nextChar == '>') {
                    endTag = true;
                    if (nextChar == '>') {
                        tab -= getTabsize();
                    }
                }
            }
            prevChar = token;
            sw.write(token);
        }
        sw.flush();
        return sw.getBuffer().toString();
    }

    @Override
    public String toString() {
        return process();
    }

    /**
     * Tabsize getter
     * @return The tab size used when formatting
     */
    public int getTabsize() {
        return tabsize;
    }

    /**
     * Tabsize setter
     * @param tabsize The tab size to use when formatting the output
     */
    public void setTabsize(int tabsize) {
        this.tabsize = tabsize;
    }
}
