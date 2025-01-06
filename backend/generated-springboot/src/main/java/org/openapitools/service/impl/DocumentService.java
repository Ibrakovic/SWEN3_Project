package org.openapitools.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openapitools.exception.DocumentNotFoundException;
import org.openapitools.exception.InvalidDocumentException;
import org.openapitools.exception.RabbitMQConnectionException;
import org.openapitools.model.DocumentsDocumentIdPutRequest;
import org.openapitools.model.DocumentsDocumentIdStatusGet200Response;
import org.openapitools.model.DocumentsSearchGet200ResponseInner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {

	private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public DocumentService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendDocumentMessage(String message) {
		try {
			rabbitTemplate.convertAndSend("documentQueue", message);
			logger.info("Message successfully sent to RabbitMQ: {}", message);
		} catch (AmqpException e) {
			logger.error("Error occurred while sending message to RabbitMQ: {}", e.getMessage());
			throw new RabbitMQConnectionException("Failed to send message to RabbitMQ", e);
		}
	}

	public void deleteDocument(String documentId) {
		if (documentId == null || documentId.isEmpty()) {
			throw new InvalidDocumentException("Document ID cannot be null or empty");
		}

		if (!checkIfDocumentExists(documentId)) {
			throw new DocumentNotFoundException("Document with ID " + documentId + " not found");
		}

		// Logic to delete the document if it exists
		System.out.println("Document deleted with ID: " + documentId);
		logger.info("Document deleted successfully with ID: {}", documentId);
	}

	private boolean checkIfDocumentExists(String documentId) {
		// logic to be added
		// For now, return false to simulate a missing document for testing
		return false;
	}

	public void updateDocumentMetadata(String documentId, DocumentsDocumentIdPutRequest documentsDocumentIdPutRequest) {
		if (documentId == null || documentId.isEmpty()) {
			throw new InvalidDocumentException("Document ID cannot be null or empty");
		}
		// Add your logic for updating document metadata here
		System.out.println("Document metadata updated for ID: " + documentId);
		logger.info("Document metadata updated successfully for ID: {}", documentId);
	}

	public DocumentsDocumentIdStatusGet200Response getDocumentStatus(String documentId) {
		if (documentId == null || documentId.isEmpty()) {
			throw new InvalidDocumentException("Document ID cannot be null or empty");
		}
		// Add your logic for getting document status here
		DocumentsDocumentIdStatusGet200Response statusResponse = new DocumentsDocumentIdStatusGet200Response();
		statusResponse.setStatus(DocumentsDocumentIdStatusGet200Response.StatusEnum.PENDING);
		logger.info("Document status retrieved successfully for ID: {}", documentId);
		return statusResponse;
	}

	public List<DocumentsSearchGet200ResponseInner> searchDocuments(String query) {
		if (query == null || query.isEmpty()) {
			throw new InvalidDocumentException("Query cannot be null or empty");
		}
		// Add your logic for searching documents here
		List<DocumentsSearchGet200ResponseInner> searchResults = new ArrayList<>();
		// Example document for demonstration purposes
		DocumentsSearchGet200ResponseInner document = new DocumentsSearchGet200ResponseInner();
		document.setDocumentId("exampleId");
		document.setContent("Example content for query: " + query);
		document.setTags(Arrays.stream(new String[] { "tag1", "tag2" }).collect(Collectors.toList()));
		searchResults.add(document);
		logger.info("Documents search completed successfully for query: {}", query);
		return searchResults;
	}

	public void sendFileToRabbitMQ(MultipartFile file) {
		try {
			// Datei in Byte-Array konvertieren
			byte[] fileContent = file.getBytes();
			// Datei mit RabbitTemplate senden
			rabbitTemplate.convertAndSend("documentQueue", fileContent);
			logger.info("File {} successfully sent to RabbitMQ.", file.getOriginalFilename());
		} catch (Exception e) {
			logger.error("Failed to send file to RabbitMQ: {}", e.getMessage());
			throw new RabbitMQConnectionException("Failed to send file to RabbitMQ", e);
		}
	}

}