package se.digitman.lightxml;

/**
 * Basic exception for the library, thrown when a method belonging to this
 * jar code fails.
 * @author Thomas
 */
public class XmlException extends RuntimeException {

    /**
     * Creates a new instance.
     * @param message The message to store with the exception.
     * @param cause The causing exception/throwable, or <code>null</code> if none.
     */
    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }
}
