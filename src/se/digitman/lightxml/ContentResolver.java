package se.digitman.lightxml;

import org.xml.sax.Attributes;


/**
 *
 * @author hl
 */
public interface ContentResolver {

    /**
     *
     * @return
     */
    public XmlNode getReplacement();

    /**
     *
     * @param parameters
     */
    public void setConditions(Attributes parameters);
}
