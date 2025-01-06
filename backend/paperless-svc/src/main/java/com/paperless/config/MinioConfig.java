package com.paperless.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class MinioConfig {

	@Value(value = "${spring.minio.host}")
	String host;

	@Value(value = "${spring.minio.username}")
	String userName;

	@Value(value = "${spring.minio.password}")
	String password;

	@Bean
	public S3Client s3Client() {
		return S3Client.builder().region(Region.US_EAST_1).endpointOverride(URI.create(host))
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(userName, password)))
				.forcePathStyle(true).build();
	}

}
