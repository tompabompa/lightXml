package se.digitman.lightxml;

import se.digitman.lightxml.transform.XmlStreamFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thomas
 */
public class DocumentToXmlNodeParserTest {

    private DocumentToXmlNodeParser parser;

    @Before
    public void setUp() {
        parser = new DocumentToXmlNodeParser(new XmlStreamFactory().getStream("test-source.xml"));
    }

    @Test
    public void testParse() {
        XmlNode result = parser.parse();
        assertEquals("root", result.getName());
        assertEquals("text1", result.getXmlNode("tag1").getText());
        assertEquals("text2", result.getXmlNode("tag2").getText());
        assertEquals("1", result.getXmlNode("tag2").getAttribute("arg1"));
    }

}