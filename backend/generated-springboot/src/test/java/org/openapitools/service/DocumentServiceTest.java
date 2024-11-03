package org.openapitools.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.doThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.openapitools.exception.DocumentNotFoundException;
import org.openapitools.service.DocumentService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class DocumentServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private DocumentService documentService;

    public DocumentServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteDocumentNotFoundException() {
        // Given
        String invalidDocumentId = "nonExistingDocumentId";

        // When
        Exception exception = assertThrows(DocumentNotFoundException.class, () -> {
            documentService.deleteDocument(invalidDocumentId);
        });

        // Then
        assertEquals("Failed to delete document", exception.getMessage());
    }

}
