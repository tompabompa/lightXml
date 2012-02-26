package se.digitman.util.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

/**
 *
 * @author thomas
 */
public abstract class ResourceLoader {

    private ResourceLoader() {
    } // prevent subclassing

    /**
     * @see java.lang.ClassLoader#loadClass(java.lang.String)
     */
    public static Class loadClass(final String name)
            throws ClassNotFoundException {
        final ClassLoader loader = ClassLoaderResolver.getClassLoader();

        return Class.forName(name, false, loader);
    }

    /**
     * @see java.lang.ClassLoader#getResource(java.lang.String)
     */
    public static URL getResource(final String name) {
        final ClassLoader loader = ClassLoaderResolver.getClassLoader();

        if (loader != null) {
            return loader.getResource(name);
        } else {
            return ClassLoader.getSystemResource(name);
        }
    }

    /**
     * @see java.lang.ClassLoader#getResourceAsStream(java.lang.String)
     */
    public static InputStream getResourceAsStream(final String name) {
        final ClassLoader loader = ClassLoaderResolver.getClassLoader();

        if (loader != null) {
            return loader.getResourceAsStream(name);
        } else {
            return ClassLoader.getSystemResourceAsStream(name);
        }
    }

    /**
     * @see java.lang.ClassLoader#getResources(java.lang.String)
     */
    public static Enumeration getResources(final String name)
            throws IOException {
        final ClassLoader loader = ClassLoaderResolver.getClassLoader();

        if (loader != null) {
            return loader.getResources(name);
        } else {
            return ClassLoader.getSystemResources(name);
        }
    }
}