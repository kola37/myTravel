package dao;

import entity.User;
import exception.DaoException;

import java.sql.Connection;
import java.util.Optional;

/**
 * Dao interface defines an abstract API
 * that extends interface Dao and
 * performs CRUD operations on User objects
 *
 * @author Anatolii Koliaka
 */
public interface UserDao extends Dao<User>{

    /**
     * Method to find User object from database using user's login as param
     *
     * @param con Connection object to provide a connection with a specific database
     * @param login user's login
     * @return an Optional of User if present
     * an empty Optional otherwise
     * @throws DaoException
     */
    Optional<User> findByLogin(Connection con, String login) throws DaoException;


}
