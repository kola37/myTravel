package com.mytravel.service.validator.impl;

import com.mytravel.service.validator.AbstractValidator;
import com.mytravel.service.validator.impl.NameValidatorImpl;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NameValidatorTest {

    private final AbstractValidator validator = new NameValidatorImpl();
    private static final String CORRECT_NAME = "Test";
    private static final String INCORRECT_NAME = "test first name";

    @Test
    public void testNameValidatorShouldReturnTrueWhenDataIsCorrect() {
        boolean actual = validator.isValid(CORRECT_NAME);
        assertTrue(actual);
    }

    @Test
    public void testNameValidatorShouldReturnFalseWhenDataIsNotCorrect() {
        boolean actual = validator.isValid(INCORRECT_NAME);
        assertFalse(actual);
    }
}
