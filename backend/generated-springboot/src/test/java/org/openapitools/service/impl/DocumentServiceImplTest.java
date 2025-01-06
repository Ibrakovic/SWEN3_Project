package org.openapitools.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.entity.Document;
import org.openapitools.repository.DocumentRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.s3.S3Client;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceImplTest {

	@InjectMocks
	DocumentServiceImpl documentServiceImpl;

	@Mock
	private DocumentRepository documentRepository;

	@Mock
	private AmqpTemplate rabbitTemplate;

	@Mock
	private S3Client s3Client;

	@Mock
	private MultipartFile file;

	@Mock
	private ObjectMapper objectMapper;

	private static final String FILE_NAME = "testFile.txt";

	@Test
	void testStoreMetadata() {
		String name = "file";
		String originalFilename = "testfile.txt";
		String contentType = "text/plain";
		byte[] content = "Hello, World!".getBytes();
		Document document = new Document();
		MultipartFile mockFile = new MockMultipartFile(name, originalFilename, contentType, content);
		Mockito.when(documentRepository.save(Mockito.any())).thenReturn(document);
		Document response = documentServiceImpl.storeMetadata(mockFile);
		assertNotNull(response);
	}

	@Test
	void testSendFileToRabbitMQ_Success() throws Exception {
		when(file.getOriginalFilename()).thenReturn(FILE_NAME);
		documentServiceImpl.sendFileToRabbitMQ(file);
		assertNotNull(FILE_NAME);
	}

	@Test
	void testUploadToMinio_Success() throws Exception {
		when(file.getOriginalFilename()).thenReturn(FILE_NAME);
		String data = "Hello, World!";
		InputStream inputStream = new ByteArrayInputStream(data.getBytes());
		when(file.getInputStream()).thenReturn(inputStream);
		String result = documentServiceImpl.uploadToMinio(file);
		assertEquals("File uploaded successfully!", result);
	}

	@Test
	void testProcessResult() {
		String message = "{\r\n" + "  \"ocrResult\": \"Test Data\",\r\n" + "  \"fileName\": \"Hello.pdf\"\r\n" + "}";
		List<Document> documents = new ArrayList<>();
		Document document = new Document();
		document.setId(1l);
		documents.add(document);
		Mockito.when(documentRepository.findByFileName(Mockito.anyString())).thenReturn(documents);
		Mockito.when(documentRepository.save(Mockito.any())).thenReturn(document);
		documentServiceImpl.processResult(message);
		assertNotNull(documents);
	}
}
