package org.openapitools.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DocumentNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleDocumentNotFoundException(DocumentNotFoundException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "Document Not Found");
		response.put("message", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RabbitMQConnectionException.class)
	public ResponseEntity<Map<String, String>> handleRabbitMQConnectionException(RabbitMQConnectionException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "RabbitMQ Connection Error");
		response.put("message", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(InvalidDocumentException.class)
	public ResponseEntity<Map<String, String>> handleInvalidDocumentException(InvalidDocumentException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "Invalid Document");
		response.put("message", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MinioConnectionException.class)
	public ResponseEntity<Map<String, String>> handleMinioConnectionException(MinioConnectionException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("error", "Minio Connection Error");
		response.put("message", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
		ex.printStackTrace();
		Map<String, String> response = new HashMap<>();
		response.put("error", "Internal Server Error");
		response.put("message", "An unexpected error occurred.");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
