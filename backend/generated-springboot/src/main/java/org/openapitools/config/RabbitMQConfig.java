package org.openapitools.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${spring.rabbitmq.queue.documentQueue}")
	private String documentQueue;

	@Value("${spring.rabbitmq.queue.resultQueue}")
	private String resultQueue;

	@Bean
	public Queue documentQueue() {
		return new Queue(documentQueue, false);
	}

	@Bean
	public Queue resultQueue() {
		return new Queue(resultQueue, false);
	}
}
