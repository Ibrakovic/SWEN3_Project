package org.openapitools.api;

import org.openapitools.model.AuthLoginPostRequest;
import org.openapitools.model.AuthRegisterPostRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
public class AuthApiController implements AuthApi {

    private final NativeWebRequest request;

    @Autowired
    public AuthApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Void> authRegisterPost(@RequestBody AuthRegisterPostRequest authRegisterPostRequest) {
        // Prüfe auf die Dummy-Daten: username = "test", password = "test"
        if ("test".equals(authRegisterPostRequest.getUsername()) && "test".equals(authRegisterPostRequest.getPassword())) {
            // Gebe HTTP 201 zurück, wenn die Daten korrekt sind
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            // Gebe HTTP 400 zurück, wenn die Daten nicht korrekt sind
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
