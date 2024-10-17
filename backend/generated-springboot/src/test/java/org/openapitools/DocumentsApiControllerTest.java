package org.openapitools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.api.DocumentsApiController;
import org.openapitools.mapper.DocumentMapper;
import org.openapitools.model.Document;
import org.openapitools.model.DocumentStatus;
import org.openapitools.model.DocumentsDocumentIdPutRequest;
import org.openapitools.model.DocumentsDocumentIdStatusGet200Response;
import org.openapitools.model.DocumentsSearchGet200ResponseInner;
import org.openapitools.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DocumentsApiControllerTest {

    @Mock
    private DocumentService documentService;

    @Mock
    private DocumentMapper documentMapper;

    @InjectMocks
    private DocumentsApiController documentsApiController;

    private Document document;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        document = new Document();
        document.setId(1L);
        document.setContent("Test content");
        document.setStatus(DocumentStatus.PENDING);
    }

    @Test
    void testDocumentsPost() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());
        when(documentService.createDocument(any(Document.class))).thenReturn(document);

        // Act
        ResponseEntity<Void> response = documentsApiController.documentsPost(file, null);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        verify(documentService, times(1)).createDocument(any(Document.class));
    }

    @Test
    void testDocumentsDocumentIdPut() {
        // Arrange
        DocumentsDocumentIdPutRequest putRequest = new DocumentsDocumentIdPutRequest();
        putRequest.setTags(List.of("Tag1", "Tag2"));
        when(documentService.getDocumentById(1L)).thenReturn(Optional.of(document));
        when(documentService.updateDocument(1L, document)).thenReturn(document);

        // Act
        ResponseEntity<Void> response = documentsApiController.documentsDocumentIdPut("1", putRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(documentService, times(1)).updateDocument(1L, document);
    }

    @Test
    void testDocumentsDocumentIdDelete() {
        // Arrange
        doNothing().when(documentService).deleteDocument(1L);

        // Act
        ResponseEntity<Void> response = documentsApiController.documentsDocumentIdDelete("1");

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(documentService, times(1)).deleteDocument(1L);
    }

    @Test
    void testDocumentsDocumentIdStatusGet() {
        // Arrange
        DocumentsDocumentIdStatusGet200Response statusResponse = new DocumentsDocumentIdStatusGet200Response();
        statusResponse.setStatus(DocumentsDocumentIdStatusGet200Response.StatusEnum.PENDING); // Verwende den korrekten Enum-Typ
        when(documentService.getDocumentById(1L)).thenReturn(Optional.of(document));
        when(documentMapper.toStatusResponse(document)).thenReturn(statusResponse);

        // Act
        ResponseEntity<DocumentsDocumentIdStatusGet200Response> response = documentsApiController.documentsDocumentIdStatusGet("1");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(DocumentsDocumentIdStatusGet200Response.StatusEnum.PENDING, response.getBody().getStatus());
        verify(documentService, times(1)).getDocumentById(1L);
        verify(documentMapper, times(1)).toStatusResponse(document);
    }

    @Test
    void testDocumentsSearchGet() {
        // Arrange
        String query = "example";
        List<Document> mockResults = List.of(document); // Simulierte Suchergebnisse
        when(documentService.searchDocuments(query)).thenReturn(mockResults);

        // Act
        ResponseEntity<List<DocumentsSearchGet200ResponseInner>> response = documentsApiController.documentsSearchGet(query);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Test content", response.getBody().get(0).getContent());
        verify(documentService, times(1)).searchDocuments(query);
    }
}
