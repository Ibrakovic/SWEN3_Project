package com.paperless.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
public class ElasticSearchIntegrationServiceImplTest {

	@InjectMocks
	ElasticSearchIntegrationServiceImpl elasticSearchIntegrationServiceImpl;

	@Mock
	private RestTemplate restTemplate;

	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(elasticSearchIntegrationServiceImpl, "elasticHost", "localhost");
		ReflectionTestUtils.setField(elasticSearchIntegrationServiceImpl, "elasticPort", "8080");
		ReflectionTestUtils.setField(elasticSearchIntegrationServiceImpl, "elasticIndex", "my-index");
		ReflectionTestUtils.setField(elasticSearchIntegrationServiceImpl, "userName", "username");
		ReflectionTestUtils.setField(elasticSearchIntegrationServiceImpl, "password", "passkey");
	}

	@Test
	void testIntegrateElasticSearch() {
		String resultText = "This is test data";
		String fileName = "test.pdf";
		ResponseEntity<Object> mockResponse = ResponseEntity.ok(new Object());
		when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
				.thenReturn(mockResponse);
		Object response = elasticSearchIntegrationServiceImpl.integrateElasticSearch(resultText, fileName);
		assertNotNull(response);
	}

	@Test
	void testIntegrateElasticSearch_exception() {
		String resultText = "This is test data";
		String fileName = "test.pdf";
		Object response = elasticSearchIntegrationServiceImpl.integrateElasticSearch(resultText, fileName);
		assertNull(response);
	}

}
