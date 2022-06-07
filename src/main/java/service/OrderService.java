package service;

import exception.ServiceException;

import java.util.Date;

public interface OrderService {

    /**
     * Method to add new order
     *
     * @param userId user's ID
     * @param tourId tour's ID
     * @param statusId order's status ID
     * @param orderDate order's date of creation
     * @param discount discount amount applicable to this order
     * @param totalPrice order's total price
     * @return true if order was successfully added, false otherwise
     * @throws ServiceException
     */
    boolean addNewOrder(int userId, int tourId, int statusId, Date orderDate,int discount, double totalPrice) throws ServiceException;

}
