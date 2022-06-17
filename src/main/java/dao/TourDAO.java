package dao;

import entity.Tour;
import entity.User;
import exception.DAOException;

import java.sql.Connection;
import java.util.Optional;

/**
 * TourDAO interface defines an abstract API
 * that extends interface DAO and
 * performs CRUD operations on Tour objects
 *
 * @author Anatolii Koliaka
 */
public interface TourDAO extends DAO<Tour> {

    /**
     * Method to find Tour object from database using tour's name as param
     *
     * @param con Connection object to provide a connection with a specific database
     * @param name tour's name
     * @return an Optional of Tour if present
     * an empty Optional otherwise
     * @throws DAOException
     */
    Optional<Tour> findByName(Connection con, String name) throws DAOException;

}
