package org.openapitools.config;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;

@Configuration
public class AppConfig {

	@Bean
	public Module jsonNullableModule() {
		return new JsonNullableModule();
	}

}
