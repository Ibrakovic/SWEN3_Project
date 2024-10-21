package org.openapitools.api;

import org.openapitools.dto.UserDTO;
import org.openapitools.model.AuthLoginPostRequest;
import org.openapitools.model.AuthRegisterPostRequest;


import org.openapitools.service.AuthService;
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
public class AuthApiController implements AuthApi {

    private final NativeWebRequest request;
    @Autowired
    private AuthService authService;

    @Autowired
    public AuthApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Void> authRegisterPost(@Valid @RequestBody AuthRegisterPostRequest authRegisterPostRequest) {
        System.out.println("Registration request received22: " + authRegisterPostRequest.toString());
        try {
            authService.register(authRegisterPostRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> authLoginPost(@Valid @RequestBody AuthLoginPostRequest authLoginPostRequest) {
        UserDTO userDTO = authService.login(authLoginPostRequest.getEmail(), authLoginPostRequest.getPassword());
        if (userDTO != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }


}