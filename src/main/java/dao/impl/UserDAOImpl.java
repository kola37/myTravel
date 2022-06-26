package dao.impl;

import dao.UserDAO;
import entity.User;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static util.DBManager.close;

/**
 * Class UserDAOIMpl implements UserDAO interface
 * and provides a several methods that performs CRUD operations on User objects
 *
 * @author Anatolii Koliaka
 */
public class UserDAOImpl implements UserDAO {

    private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class);

    private static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?,DEFAULT)";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SQL_UPDATE_USER = "UPDATE users SET login = ?, password = ?, first_name = ?, last_name = ?, email = ?, role_id = ?, is_blocked = ? WHERE id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";

    @Override
    public Optional<User> findById(Connection con, int id) throws DAOException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractUser(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find user in database! ", e);
            throw new DAOException("Cannot find user in database! ", e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(Connection con, String login) throws DAOException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractUser(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find user in database! ", e);
            throw new DAOException("Cannot find user in database! ", e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll(Connection con) throws DAOException {
        List<User> users = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_USERS);
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find user in database! ", e);
            throw new DAOException("Cannot find user in database! ", e);
        } finally {
            close(rs);
            close(stmt);
        }
        return users;
    }

    @Override
    public int insert(Connection con, User user) throws DAOException {

        int result = -1;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setString(k++, user.getEmail());
            pstmt.setInt(k, user.getRoleId());

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                    result = user.getId();
                }
            }
        } catch (SQLException e) {
            LOG.error("Cannot insert user into database! ", e);
            throw new DAOException("Cannot insert user into database! ", e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return result;
    }

    @Override
    public boolean remove(Connection con, int id) throws DAOException {

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_DELETE_USER);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Cannot remove user from database! ", e);
            throw new DAOException("Cannot remove user from database! ", e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public boolean update(Connection con, User user) throws DAOException {

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_UPDATE_USER);
            int k = 1;
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setString(k++, user.getEmail());
            pstmt.setInt(k++, user.getRoleId());
            pstmt.setBoolean(k++, user.isBlocked());
            pstmt.setInt(k, user.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Cannot update user in database! ", e);
            throw new DAOException("Cannot update user in database! ", e);
        } finally {
            close(pstmt);
        }
    }


    private static User extractUser(ResultSet rs) throws SQLException {
        return new User.Builder()
                .withId(rs.getInt("id"))
                .withLogin(rs.getString("login"))
                .withPassword(rs.getString("password"))
                .withFirstName(rs.getString("first_name"))
                .withLastName(rs.getString("last_name"))
                .withEmail(rs.getString("email"))
                .withRoleId(rs.getInt("role_id"))
                .withBlockedStatus(rs.getBoolean("is_blocked"))
                .build();
    }

}
