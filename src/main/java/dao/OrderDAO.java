package dao;

import entity.Order;
import entity.User;
import exception.DAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * OrderDAO interface defines an abstract API
 * that extends interface DAO and
 * performs CRUD operations on Order objects
 *
 * @author Anatolii Koliaka
 */
public interface OrderDAO extends DAO<Order> {

    /**
     *  Method to find all Orders from database that assigned to specified User
     *
     * @param con Connection object to provide a connection with a specific database
     * @param userId user's id
     * @return list of Orders
     * @throws DAOException
     */
    List<Order> findOrdersByUserId(Connection con, int userId) throws DAOException;

    /**
     *  Method to find all Orders from database that assigned to specified User and specified order status
     *
     * @param con Connection object to provide a connection with a specific database
     * @param userId user's id
     * @param userId order's status id
     * @return list of Orders
     * @throws DAOException
     */
    List<Order> findOrdersByUserIdAndStatusId(Connection con, int userId, int statusId) throws DAOException;


}
