package org.openapitools.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * DocumentsSearchGet200ResponseInner
 */

@JsonTypeName("_documents_search_get_200_response_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-05T12:12:53.257010100+02:00[Europe/Vienna]", comments = "Generator version: 7.8.0")
public class DocumentsSearchGet200ResponseInner {

  private String documentId;

  private String content;

  @Valid
  private List<String> tags = new ArrayList<>();

  public DocumentsSearchGet200ResponseInner documentId(String documentId) {
    this.documentId = documentId;
    return this;
  }

  /**
   * Get documentId
   * @return documentId
   */
  
  @Schema(name = "document_id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("document_id")
  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }

  public DocumentsSearchGet200ResponseInner content(String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
   */
  
  @Schema(name = "content", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public DocumentsSearchGet200ResponseInner tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public DocumentsSearchGet200ResponseInner addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Get tags
   * @return tags
   */
  
  @Schema(name = "tags", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tags")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentsSearchGet200ResponseInner documentsSearchGet200ResponseInner = (DocumentsSearchGet200ResponseInner) o;
    return Objects.equals(this.documentId, documentsSearchGet200ResponseInner.documentId) &&
        Objects.equals(this.content, documentsSearchGet200ResponseInner.content) &&
        Objects.equals(this.tags, documentsSearchGet200ResponseInner.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentId, content, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentsSearchGet200ResponseInner {\n");
    sb.append("    documentId: ").append(toIndentedString(documentId)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

