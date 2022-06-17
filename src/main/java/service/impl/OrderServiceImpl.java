package service.impl;

import dao.DAOFactory;
import dao.OrderDAO;
import dao.TourDAO;
import entity.Order;
import entity.Promotion;
import entity.Tour;
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
    public boolean addNewOrder(String userIdString, String tourIdString, String status, Date orderDate, String discountString, String totalPriceString) throws ServiceException {

        Connection con = DBUtils.getInstance().getConnection();
        try {
            int userId = Integer.parseInt(userIdString);
            int tourId = Integer.parseInt(tourIdString);
            int statusId = OrderStatus.valueOf(status.toUpperCase()).getIndex();
            int discount = Integer.parseInt(discountString);
            BigDecimal totalPrice = new BigDecimal(totalPriceString);

            Order order = createOrder(userId, tourId, statusId, orderDate, discount, totalPrice);
            OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();

            orderDAO.insert(con, order);
            LOG.trace("New tour created successfully!");
            return true;
        } catch (DAOException e) {
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
    public BigDecimal calculateTotalPrice(int price, int discount) throws ServiceException {
        double temp = price * (1-(double)discount/100);
        return new BigDecimal(temp).setScale(2, RoundingMode.HALF_UP);
    }

    public int calculateDiscountAmount(int numOfPaidOrders, Promotion promotion){
        int discountAmount = promotion.getDiscountRate() * numOfPaidOrders;
        return Math.min(discountAmount, promotion.getMaxDiscount());
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
