package se.digitman.lightxml;

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;

/**
 *
 * @author hl
 */
public abstract class AbstractContentResolver implements ContentResolver {

    private Map<String, String> attributes = null;

    @Override
    public final void setConditions(Attributes parameters) {
        attributes = new HashMap<String, String>();
        for (int i = 0; i < parameters.getLength(); i++) {
            attributes.put(parameters.getLocalName(i), parameters.getValue(i));
        }
    }

    protected String getAttributeValue(String key) {
        if (attributes.containsKey(key)) {
            return attributes.get(key);
        }
        return "";
    }
}
