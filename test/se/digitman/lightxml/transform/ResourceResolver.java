package se.digitman.lightxml.transform;

import javax.xml.transform.URIResolver;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

/**
 *
 * @author thomas
 */
public class ResourceResolver implements URIResolver {

    @Override
    public Source resolve(String href, String base)
            throws TransformerException {
        if (!href.startsWith("resource://")) {
            return null;
        }
        try {
            String resource = href.substring(11);
            ClassLoader loader = getClass().getClassLoader();
            InputStream is = loader.getResourceAsStream(resource);
            return new StreamSource(is, resource);
        }
        catch (Exception ex) {
            throw new TransformerException(ex);
        }
    }
}

