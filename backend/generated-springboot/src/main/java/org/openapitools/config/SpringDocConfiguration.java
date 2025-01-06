package org.openapitools.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfiguration {

	@Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
	OpenAPI apiInfo() {
		return new OpenAPI().info(new Info().title("SWEN API")
				.description("API for managing documents with OCR, tagging, and search functionality.")
				.version("1.0.0"));
	}
}