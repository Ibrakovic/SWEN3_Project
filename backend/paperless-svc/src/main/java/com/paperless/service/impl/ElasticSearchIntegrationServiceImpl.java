package com.paperless.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paperless.model.ElasticRequest;

@Service
public class ElasticSearchIntegrationServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchIntegrationServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Value(value = "${spring.elastic.host}")
	String elasticHost;

	@Value(value = "${spring.elastic.port}")
	String elasticPort;

	@Value(value = "${spring.elastic.index}")
	String elasticIndex;

	@Value(value = "${spring.elastic.username}")
	String userName;

	@Value(value = "${spring.elastic.password}")
	String password;

	/**
	 * This method integrates the OCR result text and file name into Elasticsearch
	 * as a document. It constructs an Elasticsearch request object and sends it to
	 * a specific index in Elasticsearch.
	 * 
	 * @param ocrResultText The text extracted by OCR from a file (content to be
	 *                      indexed).
	 * @param fileName      The name of the file (used for indexing the document).
	 * @return The response from Elasticsearch after attempting to index the
	 *         document.
	 */
	public Object integrateElasticSearch(String ocrResultText, String fileName) {
		Object response = null;
		try {
			ElasticRequest request = new ElasticRequest();
			request.setFileContent(ocrResultText);
			request.setFileName(fileName);
			request.setTimeStamp(Instant.now().toString());

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(request);

			logger.info("Request JSON : {}", json);

			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append("https://").append(elasticHost).append(":").append(elasticPort).append("/")
					.append(elasticIndex).append("/_doc/").append(generateUUID());
			String url = urlBuilder.toString();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Basic " + encodeBase64(userName + ":" + password));
			HttpEntity<ElasticRequest> entity = new HttpEntity<>(request, headers);

			ResponseEntity<Object> responseObject = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);

			response = responseObject.getBody();

		} catch (Exception e) {
			logger.error("Error occurred while integration with Elastic : ()", e.getMessage());
		}
		return response;
	}

	/**
	 * Encodes the given string into Base64 format.
	 * 
	 * This method takes the input string, converts it into bytes, and then encodes
	 * the bytes using Base64 encoding. Base64 encoding is commonly used to encode
	 * binary data in text form, which can be safely transmitted in text-based
	 * protocols (such as HTTP, email) or stored in databases.
	 * 
	 * @param value The string to be encoded in Base64 format.
	 * @return A Base64-encoded string.
	 */
	private static String encodeBase64(String value) {
		return java.util.Base64.getEncoder().encodeToString(value.getBytes());
	}

	/**
	 * Generates a random UUID (Universally Unique Identifier).
	 * 
	 * This method generates a unique identifier based on the standard UUID format,
	 * which is a 128-bit value. The generated UUID is represented as a 32-character
	 * hexadecimal string, which is often used as a unique identifier in systems
	 * such as databases or distributed applications.
	 * 
	 * @return A randomly generated UUID in string format.
	 */
	private static String generateUUID() {
		return UUID.randomUUID().toString();
	}
}
