package org.openapitools.controller;

import java.io.IOException;
import java.util.List;

import org.openapitools.dto.DocumentDTO;
import org.openapitools.entity.Document;
import org.openapitools.service.impl.DocumentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/document")
public class RequestController {

	private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

	@Autowired
	private DocumentServiceImpl documentServiceImpl;

	/**
	 * Endpoint to upload a document (file) to the server. It consumes a
	 * multipart/form-data request containing the file and performs the following:
	 * 1. Upload the file to MinIO (S3-compatible storage). 2. Store metadata in the
	 * database. 3. Send the file metadata to RabbitMQ for further processing.
	 * 
	 * @param file The file uploaded by the user (part of the multipart request).
	 * @return ResponseEntity containing a success message and HTTP status.
	 * @throws IOException If an error occurs during file processing (e.g., file
	 *                     reading).
	 */
	@Operation(summary = "upload a document for OCR processing")
	@PostMapping(value = "/upload/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) throws IOException {
		documentServiceImpl.uploadToMinio(file);
		Document document = documentServiceImpl.storeMetadata(file);
		logger.info("File save in DB with fileName {} and id: {}", document.getFileName(), document.getId());
		documentServiceImpl.sendFileToRabbitMQ(file);
		return ResponseEntity.status(201).body("File uploaded successfully");
	}

	/**
	 * Retrieves a list of DocumentDTO objects based on the provided id and/or fileName.
	 * The method accepts two optional request parameters:
	 * - id: The id of the document(s) to be retrieved.
	 * - fileName: The file name of the document(s) to be retrieved.
	 * 
	 * Depending on the parameters, it calls the `getDocuments` method of the `documentServiceImpl` to fetch the corresponding documents.
	 * If the resulting list of documents is empty, a 404 Not Found response is returned. If documents are found, a 200 OK response is returned 
	 * with the list of DocumentDTO objects.
	 * 
	 * @param id The id of the document(s) to be retrieved (optional).
	 * @param fileName The file name of the document(s) to be retrieved (optional).
	 * @return A ResponseEntity containing either a list of DocumentDTO objects (if found) or a 404 Not Found status if no documents are found.
	 * @throws IOException In case of I/O errors during document retrieval.
	 */
	@Operation(summary = "Get document details by fileName or id")
	@GetMapping(value = "/get/details", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<List<DocumentDTO>> getDocument(@RequestParam(value = "id", required = false) long id,
			@RequestParam(value = "fileName", required = false) String fileName) throws IOException {

		List<DocumentDTO> documents = documentServiceImpl.getDocuments(id, fileName);

		if (CollectionUtils.isEmpty(documents)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(documents);
	}

	/**
	 * Retrieves all documents and converts them to a list of DocumentDTO objects.
	 * The method calls the `getAllDocuments` method of the `documentServiceImpl` to fetch all documents.
	 * If the resulting list of documents is empty, a 404 Not Found response is returned. If documents are found, a 200 OK response is returned 
	 * with the list of DocumentDTO objects.
	 * 
	 * @return A ResponseEntity containing either a list of all DocumentDTO objects (if found) or a 404 Not Found status if no documents are found.
	 * @throws IOException In case of I/O errors during document retrieval.
	 */
	@Operation(summary = "Get list of all documents from DB")
	@GetMapping(value = "/get/details/all", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<List<DocumentDTO>> getAllDocuments() throws IOException {

		List<DocumentDTO> documents = documentServiceImpl.getAllDocuments();

		if (CollectionUtils.isEmpty(documents)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(documents);
	}
}
