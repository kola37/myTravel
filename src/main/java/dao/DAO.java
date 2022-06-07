package dao;

import exception.DAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Dao interface defines an abstract API
 * that performs CRUD operations on objects of type T
 * @param <T>
 * @author Anatolii Koliaka
 */
public interface DAO<T>{

    /**
     * Method to find an entity object from database using entity ID as param
     *
     * @param con Connection object to provide a connection with a specific database
     * @param id identification field of the entity to return
     * @return an Optional describing the value of this Optional, if a value is present
     * an empty Optional otherwise
     * @throws DAOException
     */
    Optional<T> findById(Connection con, int id) throws DAOException;

    /**
     * Method to find all T objects from database
     * @param con Connection object to provide a connection with a specific database
     * @return the List of T objects
     * @throws DAOException
     */
    List<T> findAll(Connection con) throws DAOException;

    /**
     * Method to add new entity into database
     * @param con Connection object to provide a connection with a specific database
     * @param t entity object to be inserted
     * @return (1) the newly created entity identification number if entity was successfully added into database (2) -1 otherwise
     * @throws DAOException
     */
    int insert(Connection con, T t) throws DAOException;

    /**
     * Method to remove an entity object from database using entity ID as param
     * @param con Connection object to provide a connection with a specific database
     * @param id identification field of the entity to remove
     * @return true if entity was successfully removed from database false otherwise
     * @throws DAOException
     */
    boolean remove(Connection con, int id) throws DAOException;

    /**
     * Method to update an entity object in database
     * @param con Connection object to provide a connection with a specific database
     * @param t entity to be updated
     * @return true if entity was successfully updated false otherwise
     * @throws DAOException
     */
    boolean update(Connection con, T t) throws DAOException;

}
