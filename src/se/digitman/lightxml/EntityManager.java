package se.digitman.lightxml;

import se.digitman.properties.FileBoundProperties;

/**
 *
 * @author thomas
 */
public class EntityManager extends FileBoundProperties {

    @Override
    protected String getPropertyFileName() {
        return "entities";
    }

    String collapseEntities(String input) {
        String result = input;
        for (Object entity : keySet()) {
            result = result.replaceAll("&" + entity + ";", getProperty(entity.toString()));
        }
        return result;
    }

    /**
     * Expands entities to make character based content parseable by external parser
     * @param s <code>Object</code> which <code>toString()</code> - representation
     * should be transformed
     * @return A <code>String</code> object containing the converted CDATA
     */
    String expandEntities(Object s) {
        return s.toString().
                replaceAll("&", "&amp;").
                replaceAll("<", "&lt;").
                replaceAll(">", "&gt;").
                replaceAll("\"", "&quot;");
    }

}
