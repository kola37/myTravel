package dao;

import entity.Hotel;
import entity.Tour;
import exception.DAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * HotelDAO interface defines an abstract API
 * that extends interface DAO and
 * performs CRUD operations on Hotel objects
 *
 * @author Anatolii Koliaka
 */
public interface HotelDAO extends DAO<Hotel> {

    /**
     * Method to find Hotel object from database using hotel's name as param
     *
     * @param con Connection object to provide a connection with a specific database
     * @param name hotel's name
     * @return an Optional of Hotel if present
     * an empty Optional otherwise
     * @throws DAOException
     */
    Optional<Hotel> findByName(Connection con, String name) throws DAOException;

//    /**
//     * Method to find all Hotels from database that assigned to Tour
//     * @param con
//     * @return list of Hotels
//     * @throws DAOException
//     */
//    List<Hotel> findAllFromTours(Connection con) throws DAOException;
}
