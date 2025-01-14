package org.openapitools.exception;

public class DocumentNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 988533214L;

    // Constructor that takes only a message
    public DocumentNotFoundException(String message) {
        super(message);
    }

    // Constructor that takes both a message and a cause
    public DocumentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
