package dao.impl;

import dao.UserDao;
import entity.User;
import entity.constant.UserRole;
import exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static util.DBManager.close;


public class UserDaoImpl implements UserDao {

    private static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);

    private static final String SQL_INSERT_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    private static final String SQL_UPDATE_USER = "UPDATE users SET login = ?, password = ?, first_name = ?, last_name = ?, email = ?, role = ?, WHERE id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";

    @Override
    public Optional<User> findById(Connection con, int id) throws DaoException {

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
            throw new DaoException("Cannot find user in database! ", e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(Connection con, String login) throws DaoException {
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
            throw new DaoException("Cannot find user in database! ", e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll(Connection con) throws DaoException {
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
            throw new DaoException("Cannot find user in database! ", e);
        } finally {
            close(rs);
            close(stmt);
        }
        return users;
    }

    @Override
    public int insert(Connection con, User user) throws DaoException {

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
            pstmt.setInt(k, user.getRole().getIndex());

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                    result = user.getUserId();
                }
            }
        } catch (SQLException e) {
            LOG.error("Cannot insert user into database! ", e);
            throw new DaoException("Cannot insert user into database! ", e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return result;
    }

    @Override
    public boolean remove(Connection con, int id) throws DaoException {

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_DELETE_USER);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Cannot remove user from database! ", e);
            throw new DaoException("Cannot remove user from database! ", e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public boolean update(Connection con, User user) throws DaoException {

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_UPDATE_USER);
            int k = 1;
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setString(k++, user.getEmail());
            pstmt.setInt(k++, user.getRole().getIndex());
            pstmt.setInt(k, user.getUserId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Cannot update user in database! ", e);
            throw new DaoException("Cannot update user in database! ", e);
        } finally {
            close(pstmt);
        }
    }



    private static User extractUser(ResultSet rs) throws SQLException {
        User user = new User.Builder()
                .withUserId(rs.getInt("id"))
                .withLogin(rs.getString("login"))
                .withPassword(rs.getString("password"))
                .withFirstName(rs.getString("first_name"))
                .withLastName(rs.getString("last_name"))
                .withEmail(rs.getString("email"))
                .withRole(UserRole.getRole(rs.getInt("role")))
                .build();
        return user;
    }

}
