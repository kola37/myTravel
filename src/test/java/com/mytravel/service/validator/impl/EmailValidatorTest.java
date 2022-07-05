package com.mytravel.service.validator.impl;

import com.mytravel.service.validator.AbstractValidator;
import com.mytravel.service.validator.impl.EmailValidatorImpl;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailValidatorTest {

    private final AbstractValidator validator = new EmailValidatorImpl();
    private static final String CORRECT_EMAIL = "test.email@gmail.com";
    private static final String INCORRECT_EMAIL = "test.email@com";

    @Test
    public void testEmailValidatorShouldReturnTrueWhenDataIsCorrect() {
        boolean actual = validator.isValid(CORRECT_EMAIL);
        assertTrue(actual);
    }

    @Test
    public void testEmailValidatorShouldReturnFalseWhenDataIsNotCorrect() {
        boolean actual = validator.isValid(INCORRECT_EMAIL);
        assertFalse(actual);
    }
}
