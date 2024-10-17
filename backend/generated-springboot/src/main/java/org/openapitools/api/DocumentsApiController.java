package org.openapitools.api;

import org.openapitools.model.Document;
import org.openapitools.model.DocumentsDocumentIdPutRequest;
import org.openapitools.model.DocumentsDocumentIdStatusGet200Response;
import org.openapitools.model.DocumentsSearchGet200ResponseInner;
import org.openapitools.service.DocumentService;
import org.openapitools.mapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.sWEN.base-path:}")
public class DocumentsApiController implements DocumentsApi {

    private final NativeWebRequest request;
    private final DocumentService documentService;
    private final DocumentMapper documentMapper;

    @Autowired
    public DocumentsApiController(NativeWebRequest request, DocumentService documentService, DocumentMapper documentMapper) {
        this.request = request;
        this.documentService = documentService;
        this.documentMapper = documentMapper;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    // Upload a document
    @Override
    public ResponseEntity<Void> documentsPost(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "tags", required = false) List<String> tags
    ) throws IOException {
        Document document = new Document();
        document.setContent(new String(file.getBytes()));
        document.setTags(tags);
        document.setStatus(DocumentStatus.PENDING); // Initialstatus
        documentService.createDocument(document);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Update document metadata
    @Override
    public ResponseEntity<Void> documentsDocumentIdPut(
            @PathVariable("documentId") String documentId,
            @Valid @RequestBody DocumentsDocumentIdPutRequest documentsDocumentIdPutRequest
    ) {
        Optional<Document> documentOptional = documentService.getDocumentById(Long.parseLong(documentId));
        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            document.setTags(documentsDocumentIdPutRequest.getTags());
            documentService.updateDocument(document.getId(), document);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Check the status of the OCR processing
    @Override
    public ResponseEntity<DocumentsDocumentIdStatusGet200Response> documentsDocumentIdStatusGet(
            @PathVariable("documentId") String documentId
    ) {
        Optional<Document> documentOptional = documentService.getDocumentById(Long.parseLong(documentId));
        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            DocumentsDocumentIdStatusGet200Response response = documentMapper.toStatusResponse(document);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete a document
    @Override
    public ResponseEntity<Void> documentsDocumentIdDelete(
            @PathVariable("documentId") String documentId
    ) {
        documentService.deleteDocument(Long.parseLong(documentId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Search for a document (ElasticSearch implementation is needed for full-text search)
    @Override
    public ResponseEntity<List<DocumentsSearchGet200ResponseInner>> documentsSearchGet(
            @RequestParam(value = "query", required = true) String query
    ) {
        // Placeholder: Actual ElasticSearch logic would go here
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Override
    public ResponseEntity<List<DocumentsSearchGet200ResponseInner>> documentsSearchGet(
            @RequestParam(value = "query", required = true) String query
    ) {
        List<Document> searchResults = elasticSearchService.searchDocuments(query);
        // Konvertiere die Dokumente in das entsprechende DTO-Format und gib sie zurück
        List<DocumentsSearchGet200ResponseInner> response = searchResults.stream()
                .map(document -> new DocumentsSearchGet200ResponseInner()
                        .documentId(document.getId().toString())
                        .content(document.getContent())
                        .tags(document.getTags()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }



}
