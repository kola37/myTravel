package service.impl;

import dao.DAOFactory;
import dao.OrderDAO;
import entity.Order;
import entity.constant.OrderStatus;
import exception.DAOException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import util.DBUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Class-service that handle all business logic related with Order and DAO
 * implements OrderService interface
 *
 * @author Anatolii Koliaka
 */
public class OrderServiceImpl implements OrderService {

    private static final Logger LOG = LogManager.getLogger(OrderServiceImpl.class);

    @Override
    public boolean addNewOrder(int userId, String tourIdString, String status, Date orderDate, String discountString, String totalPriceString) throws ServiceException {

        Connection con = DBUtils.getInstance().getConnection();
        try {
            int tourId = Integer.parseInt(tourIdString);
            int statusId = OrderStatus.valueOf(status.toUpperCase()).getIndex();
            int discount = Integer.parseInt(discountString);
            BigDecimal totalPrice = new BigDecimal(totalPriceString);

            Order order = createOrder(userId, tourId, statusId, orderDate, discount, totalPrice);
            OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();

            orderDAO.insert(con, order);
            LOG.trace("New tour created successfully!");
            return true;
        } catch (DAOException | NumberFormatException e) {
            LOG.error("Failed to create a new order!", e);
            throw new ServiceException("Failed to create a new order!", e);
        }finally {
            DBUtils.close(con);
        }
    }

    @Override
    public List<Order> retrieveUserOrdersByUserIdAndStatus(int userId, int statusId) throws ServiceException {
        Connection con = DBUtils.getInstance().getConnection();
        try {
            OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
            List<Order> result = orderDAO.findOrdersByUserIdAndStatusId(con, userId, statusId);
            return result;
        } catch (DAOException e) {
            LOG.error("Unable to retrieve user orders by user id and order status id!");
            throw new ServiceException("Unable to retrieve user orders by user id and order status id!", e);
        }
    }

    @Override
    public List<Order> retrieveUserOrdersByUserId(int userId) throws ServiceException {
        Connection con = DBUtils.getInstance().getConnection();
        try {
            OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
            List<Order> result = orderDAO.findOrdersByUserId(con, userId);
            return result;
        } catch (DAOException e) {
            LOG.error("Unable to retrieve user orders by user id!");
            throw new ServiceException("Unable to retrieve user orders by user id!", e);
        }
    }


    public List<Order> retrieveAll() throws ServiceException {
        Connection con = DBUtils.getInstance().getConnection();
        try {
            OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
            List<Order> result = orderDAO.findAll(con);
            return result;
        } catch (DAOException e) {
            LOG.error("Unable to retrieve all orders!");
            throw new ServiceException("Unable to retrieve all orders!", e);
        } finally {
            DBUtils.close(con);
        }
    }

    @Override
    public boolean deleteOrder(int orderId) throws ServiceException {
        Connection con = DBUtils.getInstance().getConnection();
        try {
            OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
            return  orderDAO.remove(con, orderId);
        } catch (DAOException e) {
            LOG.error("Unable to delete order from DB!");
            throw new ServiceException("Unable to delete order from DB!", e);
        } finally {
            DBUtils.close(con);
        }
    }

    @Override
    public boolean updateOrderStatus(int orderId, int statusId) throws ServiceException {
        Connection con = DBUtils.getInstance().getConnection();
        try {
            OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
            Optional<Order> optOrder = orderDAO.findById(con, orderId);
            if (optOrder.isPresent()){
                Order order = optOrder.get();
                order.setStatusId(statusId);
                return  orderDAO.update(con, order);
            }
        } catch (DAOException e) {
            LOG.error("Unable to update order from DB!");
            throw new ServiceException("Unable to update order from DB!", e);
        } finally {
            DBUtils.close(con);
        }
        return false;
    }

    @Override
    public BigDecimal calculateTotalPrice(int price, int discount) throws ServiceException {
        double temp = price * (1-(double)discount/100);
        return new BigDecimal(temp).setScale(2, RoundingMode.HALF_UP);
    }

    public int calculateDiscountAmount(int numOfPaidOrders, int discountRate, int maxDiscount){
        int discountAmount = discountRate * numOfPaidOrders;
        return Math.min(discountAmount, maxDiscount);
    }

    private Order createOrder(int userId, int tourId, int statusId, Date orderDate, int discount, BigDecimal totalPrice) {
        return new Order.Builder()
                .withUserId(userId)
                .withTourId(tourId)
                .withOrderStatusId(statusId)
                .withOrderDate(orderDate)
                .withDiscount(discount)
                .withTotalPrice(totalPrice)
                .build();
    }
}
