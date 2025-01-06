package com.paperless.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

@ExtendWith(MockitoExtension.class)
public class MinioServiceImplTest {

	@InjectMocks
	MinioServiceImpl minioServiceImpl;

	@Mock
	private S3Client s3Client;

	@Test
	void testFetchFileFromMinio() {
		String fileName = "test.pdf";
		String bucketName = "test-bucket";
		Mockito.when(s3Client.getObject(Mockito.any(GetObjectRequest.class))).thenReturn(null);
		InputStream response = minioServiceImpl.fetchFileFromMinio(fileName, bucketName);
		assertNotNull(fileName);
	}
}
