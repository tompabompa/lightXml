package se.digitman.properties;

import java.io.InputStream;
import java.util.Properties;

/**
 * Not really belonging to the lightXml project, but a small usable specialization
 * of the java.util.Properties class that simplifies the connection to a property file.
 * A sample subclass named MyProperties would automatically load all properties defined
 * in the file my.properties residing in the same package as the class itself.
 * No further overriding is needed; the class name is enough to resolve and read a
 * default property file.<br/>
 * <strong>Example:</strong>
 * <table>
 * <tr><th>Sample class name</th><th>Property filename</th></tr>
 * <tr><td>ApplicationProperties</td><td>application.properties</td></tr>
 * <tr><td>PropertyReader</td><td>propertyreader.properties</td></tr>
 * </table>
 * The property file must use the standard key-value-pair format for property files.
 * @author Thomas Boqvist, Digit Man
 */
public class FileBoundProperties extends Properties {

    private static final String SUFFIX = "Properties";

    public FileBoundProperties() {
        try {
            InputStream propertyStream = getClass().getClassLoader().
                    getResourceAsStream(getDashedClasspath() + getPropertyFileName() + ".properties");
            if (propertyStream != null) {
                load(propertyStream);
            }
        } catch (Exception ex) {
        }
        try {
            InputStream propertyStream = getClass().getClassLoader().
                    getResourceAsStream(getDashedClasspath() + getPropertyFileName() + ".xml");
            if (propertyStream != null) {
                loadFromXML(propertyStream);
            }
        } catch (Exception ex) {
        }
    }

    protected String getDashedClasspath() {
        return getClass().getPackage().getName().replaceAll("\\.", "/") + "/";
    }

    protected String getPropertyFileName() {
        String className = getClass().getSimpleName();
        return (className.endsWith(SUFFIX)
                ? className.substring(0, className.length() - SUFFIX.length()) : className).toLowerCase();
    }
}
