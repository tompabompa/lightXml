package se.digitman.util.resource;

/**
 *
 * @author thomas
 */
public interface ClassLoadStrategy {

    ClassLoader getClassLoader(ClassLoadContext ctx);
}