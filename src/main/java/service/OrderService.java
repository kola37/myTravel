package service;

import entity.Order;
import entity.Promotion;
import entity.Tour;
import exception.ServiceException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * OrderService interface that extends interface Service
 * and performs an abstract methods to handle business requirements with Order and DAO
 *
 * @author Anatolii Koliaka
 */
public interface OrderService {

    /**
     * Method to add new order
     *
     * @param userIdString user's ID String value
     * @param tourIdString tour's ID String value
     * @param status order's status String value
     * @param orderDate order's date of creation
     * @param discountString discount amount applicable to this order String value
     * @param totalPriceString order's total price String value
     * @return true if order was successfully added, false otherwise
     * @throws ServiceException
     */
    boolean addNewOrder(String userIdString, String tourIdString, String status, Date orderDate, String discountString, String totalPriceString) throws ServiceException;

    /**
     * Method to retrieve orders by User's id and order status
     *
     * @return list of Orders
     * @throws ServiceException
     */
    List<Order> retrieveUserOrdersByUserIdAndStatus(int userId, int statusId) throws ServiceException;

    BigDecimal calculateTotalPrice(int price, int discount) throws ServiceException;

    int calculateDiscountAmount(int numOfPaidOrders, Promotion promotion) throws ServiceException;
}
