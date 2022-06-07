package dao.impl;

import dao.OrderDAO;
import entity.Order;
import exception.DAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public Optional<Order> findById(Connection con, int id) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<Order> findAll(Connection con) throws DAOException {
        return null;
    }

    @Override
    public int insert(Connection con, Order order) throws DAOException {
        return 0;
    }

    @Override
    public boolean remove(Connection con, int id) throws DAOException {
        return false;
    }

    @Override
    public boolean update(Connection con, Order order) throws DAOException {
        return false;
    }
}
