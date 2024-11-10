package org.openapitools.api;

import org.openapitools.exception.InvalidDocumentException;
import org.openapitools.model.DocumentsDocumentIdPutRequest;
import org.openapitools.model.DocumentsDocumentIdStatusGet200Response;
import org.openapitools.model.DocumentsSearchGet200ResponseInner;
import org.openapitools.service.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-05T12:12:53.257010100+02:00[Europe/Vienna]", comments = "Generator version: 7.8.0")
@Controller
@RequestMapping("/api/documents")  // Fester Wert statt Konfigurationsvariable
@CrossOrigin(origins = "*")
public class DocumentsApiController implements DocumentsApi {

    private static final Logger logger = LoggerFactory.getLogger(DocumentsApiController.class);

    private final NativeWebRequest request;
    private final DocumentService documentService;

    @Autowired
    public DocumentsApiController(NativeWebRequest request, DocumentService documentService) {
        this.request = request;
        this.documentService = documentService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> documentsDocumentIdDelete(@PathVariable("documentId") String documentId) {
        documentService.deleteDocument(documentId);
        logger.info("Document with ID {} deleted successfully.", documentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @PutMapping(value = "/{documentId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> documentsDocumentIdPut(
            @PathVariable("documentId") String documentId,
            @Valid @RequestBody DocumentsDocumentIdPutRequest documentsDocumentIdPutRequest) {
        documentService.updateDocumentMetadata(documentId, documentsDocumentIdPutRequest);
        logger.info("Metadata for document with ID {} updated successfully.", documentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{documentId}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentsDocumentIdStatusGet200Response> documentsDocumentIdStatusGet(
            @PathVariable("documentId") String documentId) {
        DocumentsDocumentIdStatusGet200Response statusResponse = documentService.getDocumentStatus(documentId);
        logger.info("Status for document with ID {} retrieved successfully.", documentId);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    @Override
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> documentsPost(
            @RequestPart("file") MultipartFile file,
            @RequestPart(value = "tags", required = false) List<String> tags) {
        if (file.isEmpty()) {
            throw new InvalidDocumentException("Uploaded file is empty");
        }
        documentService.sendDocumentMessage("Document uploaded: " + file.getOriginalFilename());
        logger.info("Document {} uploaded and message sent to RabbitMQ.", file.getOriginalFilename());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentsSearchGet200ResponseInner>> documentsSearchGet(
            @RequestParam("query") String query) {
        List<DocumentsSearchGet200ResponseInner> searchResults = documentService.searchDocuments(query);
        logger.info("Search completed successfully for query: {}", query);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    // Ensure this endpoint has a unique path to avoid conflicts
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {
        documentService.sendDocumentMessage("Document uploaded: " + file.getOriginalFilename());
        logger.info("Document {} uploaded and message sent to RabbitMQ.", file.getOriginalFilename());
        return ResponseEntity.status(HttpStatus.OK).body("Document uploaded and message sent to RabbitMQ");
    }
}
