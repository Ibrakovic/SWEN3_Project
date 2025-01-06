package com.paperless;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan({ "com.paperless.*" })
public class PaperlessSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaperlessSvcApplication.class, args);
	}
}
