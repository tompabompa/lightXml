package se.digitman.lightxml;

/**
 * Simple implementation of a tag attribute. The lightXml Attribute implementation
 * does not comply to the DOM specification, as it is not implemented as a TreeNode.
 * @author thomas
 */
public class Attribute {

    private String key;
    private String value;

    /**
     * Creates a tag attribute object.
     * @param key the name of the attribute
     * @param value the value of the attribute
     */
    public Attribute(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + "=\"" + NodeFactory.getEntityManager().expandEntities(value) + "\"";
    }
}
