package com.paperless.service;

import org.springframework.messaging.handler.annotation.Payload;

public interface OCRWorkerService {

	void processDocument(@Payload String message);

}
