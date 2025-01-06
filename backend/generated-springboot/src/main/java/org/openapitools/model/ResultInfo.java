
package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultInfo {

	private String ocrResult;

	public String getOcrResult() {
		return ocrResult;
	}

	public void setOcrResult(String ocrResult) {
		this.ocrResult = ocrResult;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private String fileName;

	@JsonCreator
	public ResultInfo(@JsonProperty("ocrResult") String ocrResult, @JsonProperty("fileName") String fileName) {
		this.ocrResult = ocrResult;
		this.fileName = fileName;
	}

}
