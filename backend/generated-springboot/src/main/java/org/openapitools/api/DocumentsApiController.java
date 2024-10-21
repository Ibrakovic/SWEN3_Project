package org.openapitools.api;

import org.openapitools.model.DocumentsDocumentIdPutRequest;
import org.openapitools.model.DocumentsDocumentIdStatusGet200Response;
import org.openapitools.model.DocumentsSearchGet200ResponseInner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-05T12:12:53.257010100+02:00[Europe/Vienna]", comments = "Generator version: 7.8.0")
@Controller
@RequestMapping("${openapi.sWEN.base-path:}")
@CrossOrigin(origins = "*")
public class DocumentsApiController implements DocumentsApi {

    private final NativeWebRequest request;

    @Autowired
    public DocumentsApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

}
