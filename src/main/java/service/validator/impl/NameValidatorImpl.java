package service.validator.impl;

import service.validator.AbstractValidator;

/**
 * Class NameValidator extends AbstractValidator and provide a logic to validate User's Firstname and Lastname
 *
 * @author Anatolii Koliaka
 */
public class NameValidatorImpl extends AbstractValidator {
//    private static final String NAME_REGEX = "^(?=^.{1,20}$)([A-Z][a-z\\-\\']+)+|(?=^.{1,20}$)([А-ЯЁIЇҐЄ][а-яёіїґє\\-\\']+)+$";
    private static final String NAME_REGEX = "^(?=^[A-Za-z\\-\\']{1,20}$)([A-Z][a-z\\-\\']+)+|(?=^[A-Za-z\\-\\']{1,20}$)([А-ЯЁIЇҐЄ][а-яёіїґє\\-\\']+)+$";

    @Override
    protected String getRegex() {
        return NAME_REGEX;
    }
}
