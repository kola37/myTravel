package service;

import entity.Order;
import exception.ServiceException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * OrderService interface that extends interface Service
 * and performs an abstract methods to handle business requirements with Order and DAO
 *
 * @author Anatolii Koliaka
 */
public interface OrderService extends Service<Order>{

    /**
     * Method to add new order
     *
     * @param userId user's ID
     * @param tourIdString tour's ID String value
     * @param status order's status String value
     * @param orderDate order's date of creation
     * @param discountString discount amount applicable to this order String value
     * @param totalPriceString order's total price String value
     * @return true if order was successfully added, false otherwise
     * @throws ServiceException
     */
    boolean addNewOrder(int userId, String tourIdString, String status, Date orderDate, String discountString, String totalPriceString) throws ServiceException;

    /**
     * Method to retrieve User orders by User's id and order status
     *
     * @return list of Orders
     * @throws ServiceException
     */
    List<Order> retrieveUserOrdersByUserIdAndStatus(int userId, int statusId) throws ServiceException;

    /**
     * Method to retrieve User orders by User's id
     *
     * @return list of Orders
     * @throws ServiceException
     */
    List<Order> retrieveUserOrdersByUserId(int userId) throws ServiceException;

    /**
     * Method to delete order from database using order id like argument
     * @param orderId order's id
     * @return true if order successfully deleted from database, false otherwise
     * @throws ServiceException
     */
    boolean deleteOrder(int orderId) throws ServiceException;

    /**
     * Method to update order status from database using order id like argument
     * @param orderId order's id
     * @param statusId order's status id
     * @return true if order successfully updated, false otherwise
     * @throws ServiceException
     */
    boolean updateOrderStatus(int orderId, int statusId) throws ServiceException;

    BigDecimal calculateTotalPrice(int price, int discount) throws ServiceException;

    int calculateDiscountAmount(int numOfPaidOrders, int discountRate, int maxDiscount) throws ServiceException;
}
