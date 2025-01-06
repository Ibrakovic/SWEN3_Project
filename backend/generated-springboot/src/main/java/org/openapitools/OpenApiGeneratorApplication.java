package org.openapitools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "org.openapitools.repository" })
@EntityScan(basePackages = { "org.openapitools.*" })
@ComponentScan(basePackages = { "org.openapitools.*" })
public class OpenApiGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenApiGeneratorApplication.class, args);
	}
}