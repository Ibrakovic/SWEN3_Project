
package com.paperless.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentInfo {

	private String filename;
	private String bucketName;

	@JsonCreator
	public DocumentInfo(@JsonProperty("filename") String filename, @JsonProperty("bucketName") String bucketName) {
		this.filename = filename;
		this.bucketName = bucketName;
	}

	// Getters and Setters
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
}
