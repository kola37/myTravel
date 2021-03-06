package com.mytravel.exception;

/**
 * An exception that provides information about commands error or other errors
 *
 * @author Anatolii Koliaka
 */
public class CommandException extends Exception {

    private static final long serialVersionUID = 2451299462251261776L;

    /**
     * Construct a new CommandException object with the specified detail message and cause
     * that provides info about application error
     *
     * @param message the detail message
     * @param cause   the underlying reason for this DaoException
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct a new CommandException object with the specified detail message
     * that provides info about application error
     *
     * @param message the detail message
     */
    public CommandException(String message) {
        super(message);
    }
}
