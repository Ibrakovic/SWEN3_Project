package org.openapitools.api;

import org.openapitools.model.AuthLoginPostRequest;
import org.openapitools.model.AuthRegisterPostRequest;
import org.openapitools.model.User;
import org.openapitools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.sWEN.base-path:}")
public class AuthApiController implements AuthApi {

    private final NativeWebRequest request;
    private final UserService userService;

    @Autowired
    public AuthApiController(NativeWebRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Void> authRegisterPost(@RequestBody @Valid AuthRegisterPostRequest authRegisterPostRequest) {
        // Überprüfe, ob der Benutzer bereits existiert
        Optional<User> existingUser = userService.findUserByEmail(authRegisterPostRequest.getEmail());
        if (existingUser.isPresent()) {
            // Gebe HTTP 400 zurück, wenn der Benutzer bereits existiert
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Erstelle und speichere einen neuen Benutzer
        User newUser = new User(authRegisterPostRequest.getUsername(), authRegisterPostRequest.getEmail(), authRegisterPostRequest.getPassword());
        userService.createUser(newUser);

        // Gebe HTTP 201 zurück, wenn die Registrierung erfolgreich war
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
