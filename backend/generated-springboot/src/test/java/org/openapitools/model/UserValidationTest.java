package org.openapitools.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenUsernameIsBlank_thenValidationFails() {
        User user = new User();
        user.setUsername("");  // Leerer Benutzername
        user.setEmail("test@example.com");
        user.setPassword("securepassword");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Überprüfen, ob 2 Verstöße gefunden wurden
        assertEquals(2, violations.size());

        // Überprüfen, ob die erwarteten Fehlermeldungen vorhanden sind
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Username is mandatory")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Username must be between 3 and 50 characters")));
    }

    @Test
    public void whenEmailIsInvalid_thenValidationFails() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("invalid-email");
        user.setPassword("securepassword");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Email should be valid")));
    }

    @Test
    public void whenPasswordIsTooShort_thenValidationFails() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Password must be at least 6 characters")));
    }

    @Test
    public void whenAllFieldsAreValid_thenValidationSucceeds() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("securepassword");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }
}
