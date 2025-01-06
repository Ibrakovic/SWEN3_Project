package org.openapitools.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Generated;
import javax.validation.Valid;

import org.openapitools.exception.InvalidDocumentException;
import org.openapitools.model.DocumentsDocumentIdPutRequest;
import org.openapitools.model.DocumentsDocumentIdStatusGet200Response;
import org.openapitools.service.impl.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-05T12:12:53.257010100+02:00[Europe/Vienna]", comments = "Generator version: 7.8.0")
@Controller
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentsApiController {

	private static final Logger logger = LoggerFactory.getLogger(DocumentsApiController.class);

	private final NativeWebRequest request;

	private final DocumentService documentService;

	@Autowired
	public DocumentsApiController(NativeWebRequest request, DocumentService documentService) {
		this.request = request;
		this.documentService = documentService;
	}

	public Optional<NativeWebRequest> getRequest() {
		return Optional.ofNullable(request);
	}

	@GetMapping(value = "/{documentId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DocumentsDocumentIdStatusGet200Response> documentsDocumentIdStatusGet(
			@PathVariable("documentId") String documentId) {
		DocumentsDocumentIdStatusGet200Response statusResponse = documentService.getDocumentStatus(documentId);
		logger.info("Status for document with ID {} retrieved successfully.", documentId);
		return new ResponseEntity<>(statusResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{documentId}")
	public ResponseEntity<Void> documentsDocumentIdDelete(@PathVariable("documentId") String documentId) {
		documentService.deleteDocument(documentId);
		logger.info("Document with ID {} deleted successfully.", documentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/{documentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> documentsDocumentIdPut(@PathVariable("documentId") String documentId,
			@Valid @RequestBody DocumentsDocumentIdPutRequest documentsDocumentIdPutRequest) {
		documentService.updateDocumentMetadata(documentId, documentsDocumentIdPutRequest);
		logger.info("Metadata for document with ID {} updated successfully.", documentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> documentsPost(@RequestPart("file") MultipartFile file,

			@RequestPart(value = "tags", required = false) List<String> tags) {
		if (file.isEmpty()) {
			throw new InvalidDocumentException("Uploaded file is empty");
		}
		try {
			// Datei als Nachricht an RabbitMQ senden
			documentService.sendDocumentMessage(file.getOriginalFilename() + " uploaded.");
			documentService.sendFileToRabbitMQ(file); // Neue Methode hinzufügen

			logger.info("Document {} uploaded and message sent to RabbitMQ.", file.getOriginalFilename());
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			logger.error("Error during file upload: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Ensure this endpoint has a unique path to avoid conflicts

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {
		documentService.sendDocumentMessage("Document uploaded: " + file.getOriginalFilename());
		logger.info("Document {} uploaded and message sent to RabbitMQ.", file.getOriginalFilename());
		return ResponseEntity.status(HttpStatus.OK).body("Document uploaded and message sent to RabbitMQ");
	}

}
