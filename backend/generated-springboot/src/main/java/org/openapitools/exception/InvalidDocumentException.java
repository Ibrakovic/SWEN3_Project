package org.openapitools.exception;

public class InvalidDocumentException extends RuntimeException {

    // Constructor that takes only a message
    public InvalidDocumentException(String message) {
        super(message);
    }

    // Constructor that takes both a message and a cause
    public InvalidDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
