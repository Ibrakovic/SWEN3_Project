package org.openapitools.model;

import java.util.Objects;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DocumentsDocumentIdStatusGet200Response
 */

@JsonTypeName("_documents__documentId__status_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-05T12:12:53.257010100+02:00[Europe/Vienna]", comments = "Generator version: 7.8.0")
public class DocumentsDocumentIdStatusGet200Response {

	/**
	 * Gets or Sets status
	 */
	public enum StatusEnum {
		PENDING("pending"),

		PROCESSING("processing"),

		COMPLETED("completed"),

		FAILED("failed");

		private String value;

		StatusEnum(String value) {
			this.value = value;
		}

		@JsonValue
		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static StatusEnum fromValue(String value) {
			for (StatusEnum b : StatusEnum.values()) {
				if (b.value.equals(value)) {
					return b;
				}
			}
			throw new IllegalArgumentException("Unexpected value '" + value + "'");
		}
	}

	private StatusEnum status;

	public DocumentsDocumentIdStatusGet200Response status(StatusEnum status) {
		this.status = status;
		return this;
	}

	/**
	 * Get status
	 * 
	 * @return status
	 */

	@Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("status")
	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		DocumentsDocumentIdStatusGet200Response documentsDocumentIdStatusGet200Response = (DocumentsDocumentIdStatusGet200Response) o;
		return Objects.equals(this.status, documentsDocumentIdStatusGet200Response.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(status);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class DocumentsDocumentIdStatusGet200Response {\n");
		sb.append("    status: ").append(toIndentedString(status)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
