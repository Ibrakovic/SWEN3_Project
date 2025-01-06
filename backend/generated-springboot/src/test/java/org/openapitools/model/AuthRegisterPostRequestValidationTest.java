package org.openapitools.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthRegisterPostRequestValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenUsernameIsBlank_thenValidationFails() {
        AuthRegisterPostRequest request = new AuthRegisterPostRequest("", "test@example.com", "securepassword");

        Set<ConstraintViolation<AuthRegisterPostRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Username is mandatory")));
    }

    @Test
    public void whenEmailIsInvalid_thenValidationFails() {
        AuthRegisterPostRequest request = new AuthRegisterPostRequest("testuser", "invalid-email", "securepassword");

        Set<ConstraintViolation<AuthRegisterPostRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Email should be valid")));
    }

    @Test
    public void whenPasswordIsTooShort_thenValidationFails() {
        AuthRegisterPostRequest request = new AuthRegisterPostRequest("testuser", "test@example.com", "123");

        Set<ConstraintViolation<AuthRegisterPostRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Password must be at least 6 characters")));
    }

    @Test
    public void whenAllFieldsAreValid_thenValidationSucceeds() {
        AuthRegisterPostRequest request = new AuthRegisterPostRequest("testuser", "test@example.com", "securepassword");

        Set<ConstraintViolation<AuthRegisterPostRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }
}
