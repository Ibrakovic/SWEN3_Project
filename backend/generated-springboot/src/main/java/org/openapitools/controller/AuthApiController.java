package org.openapitools.controller;

import java.util.Optional;

import javax.annotation.processing.Generated;
import javax.validation.Valid;

import org.openapitools.dto.EntityResponse;
import org.openapitools.dto.UserDTO;
import org.openapitools.model.AuthLoginPostRequest;
import org.openapitools.model.AuthRegisterPostRequest;
import org.openapitools.service.AuthApi;
import org.openapitools.service.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-10-05T12:12:53.257010100+02:00[Europe/Vienna]", comments = "Generator version: 7.8.0")
@Controller
@RestController
@RequestMapping("${openapi.sWEN.base-path:}")
@CrossOrigin(origins = "*")
public class AuthApiController implements AuthApi {

	private final NativeWebRequest request;

	private AuthService authService;

	@Autowired
	public AuthApiController(NativeWebRequest request, AuthService authService) {
		this.request = request;
		this.authService = authService;
	}

	@Override
	public Optional<NativeWebRequest> getRequest() {
		return Optional.ofNullable(request);
	}

	@Override
	public EntityResponse authRegisterPost(@Valid @RequestBody AuthRegisterPostRequest authRegisterPostRequest) {
		System.out.println("Registration request received22: " + authRegisterPostRequest.toString());
		return authService.register(authRegisterPostRequest);
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