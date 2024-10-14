package org.openapitools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
    public void testValidUser() {
        User user = new User("validUser", "validuser@example.com", "validPassword");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty(), "Es sollten keine Validierungsfehler auftreten.");
    }

    @Test
    public void testUsernameTooShort() {
        User user = new User("u", "validuser@example.com", "validPassword");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size(), "Es sollte genau eine Verletzung der Validierung geben.");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Username muss zwischen 2 und 30 Zeichen lang sein.", violation.getMessage());
    }

    @Test
    public void testUsernameTooLong() {
        User user = new User("thisisaverylongusernamewhichexceedsthelimit", "validuser@example.com", "validPassword");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size(), "Es sollte genau eine Verletzung der Validierung geben.");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Username muss zwischen 2 und 30 Zeichen lang sein.", violation.getMessage());
    }

    @Test
    public void testInvalidEmail() {
        User user = new User("validUser", "invalid-email", "validPassword");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size(), "Es sollte genau eine Verletzung der Validierung geben.");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Bitte geben Sie eine gültige E-Mail-Adresse ein.", violation.getMessage());
    }

    @Test
    public void testPasswordTooShort() {
        User user = new User("validUser", "validuser@example.com", "short");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size(), "Es sollte genau eine Verletzung der Validierung geben.");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Das Passwort muss mindestens 8 Zeichen lang sein.", violation.getMessage());
    }
}
