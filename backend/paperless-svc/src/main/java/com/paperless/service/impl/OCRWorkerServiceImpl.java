package com.paperless.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paperless.model.DocumentInfo;
import com.paperless.model.ResultInfo;
import com.paperless.service.OCRWorkerService;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class OCRWorkerServiceImpl implements OCRWorkerService {

	private static final Logger logger = LoggerFactory.getLogger(OCRWorkerServiceImpl.class);

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	MinioServiceImpl minioService;

	@Value(value = "${spring.rabbitmq.queue.documentQueue}")
	String documentQueue;

	@Value(value = "${TESSDATA_PREFIX}")
	String tessdataPrefix;

	@Value(value = "${spring.rabbitmq.queue.resultQueue}")
	String resultQueue;

	@Autowired
	ElasticSearchIntegrationServiceImpl elasticSearchIntegrationServiceImpl;

	/**
	 * This method listens for messages on the "documentQueue" and processes
	 * documents for OCR extraction and indexing. It deserializes the message,
	 * fetches the file from MinIO, performs OCR on the file, and sends the result
	 * to Elasticsearch. Finally, it sends the result to another RabbitMQ queue.
	 * 
	 * @param message The incoming message from RabbitMQ containing document info.
	 */
	@Override
	@RabbitListener(queues = "documentQueue")
	public void processDocument(@Payload String message) {
		try {
			logger.info("message received on queue : {}", message);
			DocumentInfo documentInfo = deserializeMessage(message);
			String fileName = documentInfo.getFilename();
			String bucketName = documentInfo.getBucketName();
			InputStream inputStream = minioService.fetchFileFromMinio(fileName, bucketName);
			logger.info("file received from minio : {} ", inputStream);
			String ocrResultText = performOCR(inputStream, fileName);

			elasticSearchIntegrationServiceImpl.integrateElasticSearch(ocrResultText, fileName);

			ResultInfo resultInfo = new ResultInfo();
			resultInfo.setFileName(fileName);
			resultInfo.setOcrResult(ocrResultText);
			ObjectMapper mapper = new ObjectMapper();
			String messageJSON = mapper.writeValueAsString(resultInfo);
			rabbitTemplate.convertAndSend(resultQueue, messageJSON);
		} catch (Exception e) {
			logger.error("Exception occurred while processing OCR document : {}", e.getMessage());
		}
	}

	/**
	 * This method deserializes the incoming message into a DocumentInfo object.
	 * This object contains information like the file name and the bucket name where
	 * the document is stored.
	 * 
	 * @param message The message to be deserialized.
	 * @return The deserialized DocumentInfo object.
	 * @throws StreamReadException, DatabindException, IOException If an error
	 *                              occurs during deserialization.
	 */
	private DocumentInfo deserializeMessage(String message) throws StreamReadException, DatabindException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(message, DocumentInfo.class);
	}

	/**
	 * This method performs OCR (Optical Character Recognition) on the provided file
	 * (either an image or a PDF). It uses Tesseract to extract text from the file.
	 * 
	 * @param inputStream The input stream of the file to process.
	 * @param fileName    The name of the file (used to determine file type).
	 * @return The OCR result as text.
	 * @throws IOException If an error occurs during reading or processing the file.
	 */
	private String performOCR(InputStream inputStream, String fileName) throws IOException {
		try {
			
			logger.info("performing OCR on file");
			
			if (fileName.endsWith(".png")) {

				BufferedImage image = ImageIO.read(inputStream);
				ITesseract tesseract = new Tesseract();
				tesseract.setDatapath(tessdataPrefix);
				tesseract.setLanguage("eng");
				String ocrResult = tesseract.doOCR(image);
				return ocrResult;

			} else if (fileName.endsWith(".pdf")) {

				PDDocument document = PDDocument.load(inputStream);
				PDFRenderer pdfRenderer = new PDFRenderer(document);

				// Convert the first page to an image (page index 0)
				BufferedImage image = pdfRenderer.renderImage(0);
				ITesseract tesseract = new Tesseract();
				tesseract.setDatapath(tessdataPrefix);
				tesseract.setLanguage("eng");
				String ocrResult = tesseract.doOCR(image);

				return ocrResult;
			}
			return null;
		} catch (TesseractException e) {
			throw new RuntimeException("Error performing OCR", e);
		}
	}
}
