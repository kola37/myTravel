package com.mytravel.service.validator.impl;

import com.mytravel.service.validator.AbstractValidator;

/**
 * Class EmailValidator extends AbstractValidator and provide a logic to validate email addresses
 *
 * @author Anatolii Koliaka
 */
public class EmailValidatorImpl extends AbstractValidator {
    private static final String EMAIL_REGEX = "^(?=^.{5,40}$)(([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6})$";

    @Override
    protected String getRegex() {
        return EMAIL_REGEX;
    }

}
