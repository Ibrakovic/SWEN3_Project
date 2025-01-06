package com.paperless.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paperless.service.impl.ElasticSearchIntegrationServiceImpl;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class TestController {

	@Autowired
	ElasticSearchIntegrationServiceImpl elasticsearchIntegration;

	   /**
     * This method is used for internal testing to simulate the integration with Elasticsearch.
     * It is invoked by sending a PUT request with 'fileName' and 'fileContent' as parameters.
     * The method calls the 'integrateElasticSearch' method from the ElasticSearchIntegration service 
     * to index the provided content into Elasticsearch.
     *
     * @param fileName Name of the file (used for indexing or identifying the document).
     * @param fileContent Content of the file (this is the data to be indexed into Elasticsearch).
     * @return A ResponseEntity containing the response from Elasticsearch integration.
     * @throws IOException If there is an error during the Elasticsearch operation (e.g., network issues, invalid content).
     */
	@Operation(summary = "send a text content with fileName to elastic")
	@PutMapping(value = "/elastic", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<Object> getElastic(@RequestParam("fileName") String fileName,
			@RequestParam("fileContent") String fileContent) throws IOException {
		Object response = elasticsearchIntegration.integrateElasticSearch(fileContent, fileName);
		return ResponseEntity.ok(response);
	}
}
