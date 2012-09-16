package se.digitman.lightxml.transform;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;										//JA
import java.util.Map;											//JA
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import se.digitman.lightxml.DocumentToXmlNodeParser;
import se.digitman.lightxml.XmlDocument;
import se.digitman.lightxml.XmlException;
import se.digitman.lightxml.XmlNode;

/**
 * A wrapper for the Transformer interface supplied by the java API. In order to
 * use this class, the VM running must be able to load an implementation of the
 * Transformer interface.
 *
 * @author Thomas
 */
public class XmlTransformer {

    private XmlDocument xmlSource;
    private Source xslSource;
    private URIResolver uriResolver = null;
    private Map<String, Object> parameters;

    /**
     * Creates an XmlTransformer with an XmlNode and an XSL stylesheet source
     * @param xmlSource The root node to use as XML source for the transformation
     * @param xslSource The StreamSource implementation for the XSL stylesheet to use
     */
    public XmlTransformer(XmlNode xmlSource, Source xslSource) {
        this(new XmlDocument(xmlSource), xslSource);
    }

    /**
     * Creates an XmlTransformer with an XmlDocument and an XSL stylesheet source
     * @param xmlSource The XML document to transform
     * @param xslSource The StreamSource implementation for the XSL stylesheet to use
     */
    public XmlTransformer(XmlDocument xmlSource, Source xslSource) {
        this(xmlSource, xslSource, Collections.EMPTY_MAP);
    }

    /**
     * Creates an XmlTransformer with an XmlDocument and an XSL stylesheet source
     * @param xmlSource The XML document to transform
     * @param xslSource The StreamSource implementation for the XSL stylesheet to use
     * @param parameters The paramameters to use when initialising the XSL transformer instance
     */
    public XmlTransformer(XmlDocument xmlSource, Source xslSource, Map<String, Object> parameters) {	//JA
        this.xmlSource = xmlSource;
        this.xslSource = xslSource;
        this.parameters = parameters;
    }

    /**
     * Setup a URIResolver for the transformator.
     * @param uriResolver Set if default resolver (file system) doesn't support
     * your context.
     */
    public void setUriResolver(URIResolver uriResolver) {
        this.uriResolver = uriResolver;
    }

    /**
     * Performs the transformation and returns the result as a String
     * @return The resulting String
     */
    public String getResultAsString() {
        Source ss;
        TransformerFactory tFactory;
        Templates xsl;
        Transformer xslt;
        StringWriter output = new StringWriter();
        try {
            ss = xslSource;
            tFactory = TransformerFactory.newInstance();
            if (uriResolver != null) {
                tFactory.setURIResolver(uriResolver);
            }
            xsl = tFactory.newTemplates(ss);
            xslt = xsl.newTransformer();
            for (String key : parameters.keySet()) {
                xslt.setParameter(key, parameters.get(key));
            }
            xslt.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            if (uriResolver != null) {
                xslt.setURIResolver(uriResolver);
            }
            xslt.transform(new StreamSource(new StringReader(xmlSource.toString())), new StreamResult(output));
        } catch (Exception ex) {
            throw new XmlException("XSL transformation failure: " + ex.getMessage(), ex);
        }
        return output.toString();
    }

    /**
     * Performs the transformation and returns the result as an XmlNode
     * @return The resulting XML
     */
    public XmlNode process() {
        return new DocumentToXmlNodeParser(getResultAsString().toString()).parse();
    }
}
