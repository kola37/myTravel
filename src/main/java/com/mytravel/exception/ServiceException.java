package com.mytravel.exception;

/**
 * ServiceException class
 *
 * @author Anatolii Koliaka
 */
public class ServiceException extends Exception{

    private static final long serialVersionUID = 8670135969660230761L;

    /**
     * Construct a new ServiceException object with the specified detail message and cause
     * that provides info about service error
     *
     * @param message the detail message
     * @param cause   the underlying reason for this ServiceException
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct a new ServiceException object with the specified detail message and cause
     * that provides info about service error
     *
     * @param message the detail message
     */
    public ServiceException(String message) {
        super(message);
    }
}
