package org.openapitools.mapper;

import org.mapstruct.Mapper;
import org.openapitools.model.Document;
import org.openapitools.model.DocumentsDocumentIdPutRequest;
import org.openapitools.model.DocumentsDocumentIdStatusGet200Response;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    // Mapping von DTO zu Entität
    Document toEntity(DocumentsDocumentIdPutRequest documentDto);

    // Mapping von Entität zu DTO
    DocumentsDocumentIdStatusGet200Response toStatusResponse(Document document);
}
