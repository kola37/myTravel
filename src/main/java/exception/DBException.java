package exception;

/**
 * An exception that provides information on a database connection error
 */
public class DBException extends Exception {

    private static final long serialVersionUID = 679644386903225920L;

    /**
     * Construct a new DBException object with the specified detail message and cause
     *
     * @param message the detail message
     * @param cause   the underlying reason for this DaoException
     */
    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
