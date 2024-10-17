package org.openapitools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.model.Document;
import org.openapitools.repository.DocumentRepository;
import org.openapitools.service.DocumentService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;

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
    void testCreateDocument() {
        // Arrange
        when(documentRepository.save(any(Document.class))).thenReturn(document);

        // Act
        Document createdDocument = documentService.createDocument(document);

        // Assert
        assertNotNull(createdDocument);
        assertEquals("Test content", createdDocument.getContent());
        verify(documentRepository, times(1)).save(document);
    }

    @Test
    void testGetDocumentById() {
        // Arrange
        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));

        // Act
        Optional<Document> foundDocument = documentService.getDocumentById(1L);

        // Assert
        assertTrue(foundDocument.isPresent());
        assertEquals(1L, foundDocument.get().getId());
        verify(documentRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateDocument() {
        // Arrange
        Document updatedDocument = new Document();
        updatedDocument.setContent("Updated content");
        updatedDocument.setStatus(DocumentStatus.COMPLETED);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(document));
        when(documentRepository.save(document)).thenReturn(document);

        // Act
        Document result = documentService.updateDocument(1L, updatedDocument);

        // Assert
        assertNotNull(result);
        assertEquals("Updated content", result.getContent());
        assertEquals(DocumentStatus.COMPLETED, result.getStatus());
        verify(documentRepository, times(1)).findById(1L);
        verify(documentRepository, times(1)).save(document);
    }

    @Test
    void testDeleteDocument() {
        // Arrange
        doNothing().when(documentRepository).deleteById(1L);

        // Act
        documentService.deleteDocument(1L);

        // Assert
        verify(documentRepository, times(1)).deleteById(1L);
    }
}
