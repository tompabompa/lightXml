package se.digitman.lightxml.transform;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

/**
 * An implementation of UriResolver that uses the classpath as context
 * for resolving URI references when transforming or importing XMLdata.
 *
 * @author Thomas
 */
public class ClasspathResolver implements URIResolver {

    private String root;
    private ClassLoader loader;

    /**
     * @param root The classpath root reference directory/package to use.
     */
    public ClasspathResolver(String root) {
        setRoot(root);
    }

    /**
     * @param root The classpath root reference directory/package to use.
     */
    public ClasspathResolver(String root, ClassLoader loader) {
        setRoot(root);
        setClassLoader(loader);
    }

    private void setRoot(String root) {
        if (!root.endsWith("/")) {
            root += "/";
        }
        while (root.startsWith("/")) {
            root = root.substring(1);
        }
        this.root = root;
    }

    private void setClassLoader(ClassLoader loader) {
        this.loader = loader;
    }
    
    private ClassLoader getClassLoader() {
        return loader != null ? loader : getClass().getClassLoader();
    }

    /**
     * The method called by the context needing resolving for a classpath-based
     * XML source.
     * @param href The relative address
     * @param base Fulfills the interface, not used by this implementation.
     */
    @Override
    public Source resolve(String href, String base) throws TransformerException {
        return new StreamSource(getClassLoader().getResourceAsStream(root + href));
    }
}
