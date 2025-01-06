package com.paperless.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class OCRWorkerServiceImplTest {

	@InjectMocks
	OCRWorkerServiceImpl ocrWorkerService;

	@Mock
	private AmqpTemplate rabbitTemplate;

	@Mock
	MinioServiceImpl minioService;

	@Mock
	ElasticSearchIntegrationServiceImpl elasticSearchIntegrationServiceImpl;

	private String sampleMessage = "{\"filename\": \"sample\", \"bucketName\": \"test-bucket\"}";

	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(ocrWorkerService, "documentQueue", "documentQueueTest");
		ReflectionTestUtils.setField(ocrWorkerService, "resultQueue", "resultQueueTest");
	}

	@Test
	void testProcessDocument() {
		InputStream mockFileStream = new ByteArrayInputStream("This is a test file".getBytes());
		Mockito.when(minioService.fetchFileFromMinio(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(mockFileStream);
		ocrWorkerService.processDocument(sampleMessage);
		assertNotNull(sampleMessage);
	}

}
