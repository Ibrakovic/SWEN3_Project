package org.openapitools.exception;

public class RabbitMQConnectionException extends RuntimeException {

	private static final long serialVersionUID = 18668332L;

	// Constructor that takes only a message
	public RabbitMQConnectionException(String message) {
		super(message);
	}

	// Constructor that takes both a message and a cause
	public RabbitMQConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
}
