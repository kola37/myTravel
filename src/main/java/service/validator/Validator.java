package service.validator;

/**
 * Validator interface that perform abstract method to validate some input fields
 *
 * @author Anatolii Koliaka
 */
public interface Validator {

    /**
     * Method to validate information
     *
     * @param expression information as a string for validation
     * @return result of validation
     */
    boolean isValid(String expression);
}
