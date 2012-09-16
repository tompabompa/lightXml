package se.digitman.util;

import java.io.InputStream;

/**
 * Used as base class for implementations wanting to load resource files residing
 * in the same package as the source file.
 *
 * @author Thomas Boqvist, Digit Man
 */
public abstract class ResourceStreamFactory {

    /**
     * Returns the resulting stream for a resource object from the same package as the
     * defined subclass. The resource object should be named without package path.
     * @param filename The name of the resource in the package
     * @return The resulting input stream
     */
    public InputStream getStream(String filename) {
        return getClass().getResourceAsStream("/" + getClass().getPackage().getName().replace('.', '/') + "/" + filename);
    }
}
