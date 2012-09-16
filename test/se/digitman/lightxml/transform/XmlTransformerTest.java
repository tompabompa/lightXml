package se.digitman.lightxml.transform;

import javax.xml.transform.stream.StreamSource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import se.digitman.lightxml.DocumentToXmlNodeParser;
import se.digitman.lightxml.XmlNode;

/**
 *
 * @author thomas
 */
public class XmlTransformerTest {

    private DocumentToXmlNodeParser parser;
    private XmlTransformer transformer;
    XmlNode sourceXml;

    @Before
    public void setUp() {
        sourceXml = new DocumentToXmlNodeParser(new XmlStreamFactory().getStream("test-source.xml")).parse();
    }

    @Test
    public void testProcessWithFailingContext() {
        transformer = new XmlTransformer(sourceXml, new StreamSource(new XmlStreamFactory().getStream("test-stylesheet.xsl")));
        try {
            transformer.process();
            fail("Exception must be thrown");
        } catch (Exception ex) {
        }
    }

    @Test
    public void testProcessWithClasspathContext() {
        transformer = new XmlTransformer(sourceXml, new StreamSource(new XmlStreamFactory().getStream("test-stylesheet.xsl")));
        transformer.setUriResolver(new ClasspathResolver("se/digitman/lightxml/transform/include", getClass().getClassLoader()));
        XmlNode result = transformer.process();
        assertEquals("html", result.getName());
        assertEquals("text1", result.getXmlNodeByPath("head/title").getText());
        assertEquals("1", result.getXmlNodeByPath("body/h4").getText());
        assertEquals("included text", result.getXmlNodeByPath("body/p").getText());
    }
}
