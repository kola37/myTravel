package com.mytravel.dao;

import com.mytravel.entity.User;
import com.mytravel.exception.DAOException;

import java.sql.Connection;
import java.util.Optional;

/**
 * UserDAO interface defines an abstract API
 * that extends interface DAO and
 * performs CRUD operations on User objects
 *
 * @author Anatolii Koliaka
 */
public interface UserDAO extends DAO<User> {

    /**
     * Method to find User object from database using user's login as param
     *
     * @param con Connection object to provide a connection with a specific database
     * @param login user's login
     * @return an Optional of User if present
     * an empty Optional otherwise
     * @throws DAOException
     */
    Optional<User> findByLogin(Connection con, String login) throws DAOException;


}
