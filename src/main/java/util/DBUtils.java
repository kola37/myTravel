package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * DBUtils class
 *
 * @author Anatolii Koliaka
 */
public class DBUtils {

    private static final Logger LOG = LogManager.getLogger(DBUtils.class);

    private static DBUtils instance;
    private DataSource ds;

    /**
     * Method to get the instance of DBUtils class
     *
     * @return DBManager instance
     */
    public static synchronized DBUtils getInstance() {
        if (instance == null) {
            instance = new DBUtils();
        }
        return instance;
    }


    private DBUtils() {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            ds = (DataSource)envContext.lookup("jdbc/TravelAgencyDB");
        } catch (NamingException ex) {
            throw new IllegalStateException("Cannot obtain a data source", ex);
        }
    }

    /**
     * Method to get a connection (session) with a specific database.
     * SQL statements are executed and results are returned within the context of a connection.
     * Before using this method the Date Source and the Connections Pool in
     * WEB_APP_ROOT/META-INF/context.xml file should be configured
     *
     * @return a new Connection object
     */
    public Connection getConnection() {
        Connection con = null;
        try {
            con = ds.getConnection();
            LOG.trace("Connection successfully created!: {}", con);
        } catch (SQLException ex) {
            LOG.error("Failed to create connection!", ex);
            throw new IllegalStateException("Failed to create connection!", ex);
        }
        return con;
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
            } catch (SQLException ex) {
                LOG.error("Cannot close a connection!", ex);
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
            } catch (SQLException ex) {
                LOG.error("Cannot close a statement!", ex);
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
            } catch (SQLException ex) {
                LOG.error("Cannot close a resultSet!", ex);
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
            } catch (SQLException ex) {
                LOG.error("Cannot rollback a transaction!", ex);
            }
        }
    }

}

