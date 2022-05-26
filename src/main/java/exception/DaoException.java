package exception;

/**
 * An exception that provides information on a database access error or other errors
 *
 * @author Anatolii Koliaka
 */
public class DaoException extends Exception {

    private static final long serialVersionUID = -6221348523112672016L;

    /**
     * Construct a new DaoException object with the specified detail message and cause
     *
     * @param message the detail message
     * @param cause   the underlying reason for this DaoException
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
