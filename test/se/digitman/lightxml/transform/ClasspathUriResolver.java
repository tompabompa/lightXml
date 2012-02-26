package se.digitman.lightxml.transform;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author thomas
 */
public final class ClasspathUriResolver implements URIResolver {

    public static final String URI_SCHEME = "classpath";
    private static final ClasspathUriResolver singletonInstance = new ClasspathUriResolver();

    private ClasspathUriResolver() {
    }

    public static ClasspathUriResolver getInstance() {
        return singletonInstance;
    }

    @Override
    public Source resolve(String href, String base) throws TransformerException {
        /* Handle the special document('') case as normal */
        if (href.equals("")) {
            return null;
        }
        /* Resolve href against base URI */
        URI baseUri;
        try {
            baseUri = new URI(base);
        } catch (URISyntaxException e) {
            throw new TransformerException("Could not convert base=" + base + " into a URI", e);
        }
        URI resolvedUri = baseUri.resolve(href);

        /* See if it's one of our URI schemes. If so, resolve */
        Source result = null;
        if (URI_SCHEME.equals(resolvedUri.getScheme())) {
            /* Strip off the leading '/' from the path */
            String resourceLocation = resolvedUri.getPath().substring(1);
            InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(resourceLocation);
            if (resourceStream != null) {
                result = new StreamSource(resourceStream, resolvedUri.toString());
            } else {
                throw new TransformerException("Could not load resource at " + resourceLocation + " via ClassLoader");
            }
        }
        return result;
    }
}
