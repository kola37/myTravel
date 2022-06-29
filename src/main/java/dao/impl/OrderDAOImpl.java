package dao.impl;

import dao.OrderDAO;
import entity.Order;
import entity.User;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static util.DBUtils.close;

/**
 * Class OrderDAOIMpl implements OrderDAO interface
 * and provides a several methods that performs CRUD operations on Order objects
 *
 * @author Anatolii Koliaka
 */
public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOG = LogManager.getLogger(OrderDAOImpl.class);

    private static final String SQL_INSERT_ORDER = "INSERT INTO orders VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL_ORDERS = "SELECT * FROM orders";
    private static final String SQL_FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";
    private static final String SQL_FIND_ORDERS_BY_USER_ID = "SELECT * FROM orders WHERE user_id = ?";

    private static final String SQL_FIND_ORDERS_BY_USER_ID_AND_ORDER_STATUS = "SELECT * FROM orders WHERE user_id = ? AND status_id = ?";
    private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE orders SET status_id = ? WHERE id = ?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id = ?";

    @Override
    public Optional<Order> findById(Connection con, int id) throws DAOException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_FIND_ORDER_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractOrder(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find order in database! ", e);
            throw new DAOException("Cannot find order in database! ", e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return Optional.empty();
    }

    @Override
    public List<Order> findOrdersByUserId(Connection con, int userId) throws DAOException {
        List<Order> orders = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_FIND_ORDERS_BY_USER_ID);
            int k = 1;
            pstmt.setInt(k, userId);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                orders.add(extractOrder(rs));
            }
        }catch (SQLException e){
            LOG.error("Cannot find order in database! ", e);
            throw new DAOException("Cannot find order in database! ", e);
        }finally {
            close(rs);
            close(pstmt);
        }
        return orders;
    }

    @Override
    public List<Order> findOrdersByUserIdAndStatusId(Connection con, int userId, int statusId) throws DAOException {
        List<Order> orders = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_FIND_ORDERS_BY_USER_ID_AND_ORDER_STATUS);
            int k = 1;
            pstmt.setInt(k++, userId);
            pstmt.setInt(k, statusId);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                orders.add(extractOrder(rs));
            }
        }catch (SQLException e){
            LOG.error("Cannot find order in database! ", e);
            throw new DAOException("Cannot find order in database! ", e);
        }finally {
            close(rs);
            close(pstmt);
        }
        return orders;
    }

    @Override
    public List<Order> findAll(Connection con) throws DAOException {
        List<Order> orders = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_ORDERS);
            while (rs.next()) {
                orders.add(extractOrder(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find order in database! ", e);
            throw new DAOException("Cannot find order in database! ", e);
        } finally {
            close(rs);
            close(stmt);
        }
        return orders;
    }

    @Override
    public int insert(Connection con, Order order) throws DAOException {
        Date sqlDate = new Date(order.getOrderDate().getTime());

        int result = -1;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setInt(k++, order.getUserId());
            pstmt.setInt(k++, order.getTourId());
            pstmt.setInt(k++, order.getStatusId());
            pstmt.setDate(k++, sqlDate);
            pstmt.setInt(k++,order.getDiscount());
            pstmt.setBigDecimal(k, order.getTotalPrice());

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    order.setId(rs.getInt(1));
                    result = order.getId();
                }
            }
        } catch (SQLException e) {
            LOG.error("Cannot insert order into database! ", e);
            throw new DAOException("Cannot insert order into database! ", e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return result;
    }

    @Override
    public boolean remove(Connection con, int id) throws DAOException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_DELETE_ORDER);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Cannot remove order from database! ", e);
            throw new DAOException("Cannot remove order from database! ", e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public boolean update(Connection con, Order order) throws DAOException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_UPDATE_ORDER_STATUS);
            int k = 1;
            pstmt.setInt(k++, order.getStatusId());
            pstmt.setInt(k, order.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Cannot update order in database! ", e);
            throw new DAOException("Cannot update order in database! ", e);
        } finally {
            close(pstmt);
        }
    }

    private static Order extractOrder(ResultSet rs) throws SQLException {
        return new Order.Builder()
                .withId(rs.getInt("id"))
                .withUserId(rs.getInt("user_id"))
                .withTourId(rs.getInt("tour_id"))
                .withOrderStatusId(rs.getInt("status_id"))
                .withOrderDate(rs.getDate("order_date"))
                .withDiscount(rs.getInt("discount"))
                .withTotalPrice(rs.getBigDecimal("total_price"))
                .build();
    }

}
