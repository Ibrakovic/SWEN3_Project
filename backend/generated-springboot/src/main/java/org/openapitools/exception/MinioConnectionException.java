package org.openapitools.exception;

public class MinioConnectionException extends RuntimeException {

	private static final long serialVersionUID = 1497994567L;

	// Constructor that takes only a message
	public MinioConnectionException(String message) {
		super(message);
	}

	// Constructor that takes both a message and a cause
	public MinioConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
