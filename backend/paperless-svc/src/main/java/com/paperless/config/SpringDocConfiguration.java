package com.paperless.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfiguration {

	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title("Paperless Service")
				.description("Paperless service to perform the OCR-recognition").version("1.0.0"));
	}
}