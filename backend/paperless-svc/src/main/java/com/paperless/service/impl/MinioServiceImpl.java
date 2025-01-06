package com.paperless.service.impl;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class MinioServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(MinioServiceImpl.class);

	/**
	 * Autowired S3Client to interact with MinIO (S3-compatible object storage)
	 * 
	 */
	@Autowired
	private S3Client s3Client;

	/**
	 * This method retrieves a file from MinIO (S3-compatible object storage) as an
	 * InputStream.
	 * 
	 * @param filename   The name of the file to fetch from the MinIO bucket.
	 * @param bucketName The name of the bucket where the file is stored.
	 * @return An InputStream of the file content, or null if there was an error
	 *         during the retrieval process.
	 */
	public InputStream fetchFileFromMinio(String filename, String bucketName) {
		try {
			// Create the GetObjectRequest to fetch the file from MinIO
			GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(filename).build();

			// Fetch the file content as an InputStream
			return s3Client.getObject(getObjectRequest);
		} catch (S3Exception e) {
			logger.error("Error fetching file {} from MinIO: {}", filename, e.getMessage());
			return null;
		}
	}
}
