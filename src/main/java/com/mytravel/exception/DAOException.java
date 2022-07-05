package com.mytravel.exception;

/**
 * An exception that provides information on a database access error or other errors
 *
 * @author Anatolii Koliaka
 */
public class DAOException extends Exception {

    private static final long serialVersionUID = -6221348523112672016L;

    /**
     * Construct a new DAOException object with the specified detail message and cause
     * that provides info about DAO error
     *
     * @param message the detail message
     * @param cause   the underlying reason for this DaoException
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construct a new DAOException object with the specified detail message
     * that provides info about DAO error
     *
     * @param message the detail message
     */
    public DAOException(String message){
        super(message);
    }
}
