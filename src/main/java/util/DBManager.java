package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * DBManager class
 *
 * @author Anatolii Koliaka
 */
public class DBManager {

    private static final Logger LOG = LogManager.getLogger(DBManager.class);

    private static final String DB_CONNECTION_PATH = "app.properties";
    private static final String DB_CONNECTION_URL = "db.connection.url";
    private static final String DB_CONNECTION_USER = "db.username";
    private static final String DB_CONNECTION_PASSWORD = "db.password";

    private static DBManager instance;

    /**
     * Method to get the instance of DBManager class
     *
     * @return DBManager instance
     */
    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() {
    }


    /**
     * Method to get a connection (session) with a specific database.
     * SQL statements are executed and results are returned within the context of a connection.
     *
     * @return a new Connection object
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            Properties properties = new Properties();
            properties.load(DBManager.class.getClassLoader().getResourceAsStream(DB_CONNECTION_PATH));
            String url = properties.getProperty(DB_CONNECTION_URL);
            String user = properties.getProperty(DB_CONNECTION_USER);
            String password = properties.getProperty(DB_CONNECTION_PASSWORD);
            connection = DriverManager.getConnection(url, user, password);
            LOG.info("Connection successfully created!: {}", connection);
        } catch (SQLException e) {
            LOG.error("Failed to create connection!", e);
            throw new IllegalStateException("Failed to create connection!", e);
        } catch (IOException e) {
            LOG.error("Failed to create connection URL!", e);
        }
        return connection;
    }

    /**
     * Method to close Connection object which provides a connection with a specific database
     *
     * @param con connection to be closed
     */
    public static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                LOG.error("Cannot close a connection!", e);
            }
        }
    }

    /**
     * Method to close the Statement object
     *
     * @param stmt statement to be closed
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                LOG.error("Cannot close a statement!", e);
            }
        }
    }

    /**
     * Method to close the ResultSet object
     *
     * @param rs resultSet to be closed
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.error("Cannot close a resultSet!", e);
            }
        }
    }

    /**
     * Method to undoes all changes made in the current transaction.
     * This method should be used only when auto-commit mode has been disabled
     *
     * @param con current connection object
     */
    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                LOG.error("Cannot rollback a transaction!", e);
            }
        }
    }

}
