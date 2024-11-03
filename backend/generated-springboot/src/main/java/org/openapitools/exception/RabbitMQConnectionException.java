package org.openapitools.exception;

public class RabbitMQConnectionException extends RuntimeException {

    // Constructor that takes only a message
    public RabbitMQConnectionException(String message) {
        super(message);
    }

    // Constructor that takes both a message and a cause
    public RabbitMQConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
