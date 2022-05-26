package dao.impl;

import dao.Dao;
import dao.OrderDao;
import entity.Order;
import exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

    @Override
    public Optional<Order> findById(Connection con, int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Order> findAll(Connection con) throws DaoException {
        return null;
    }

    @Override
    public int insert(Connection con, Order order) throws DaoException {
        return 0;
    }

    @Override
    public boolean remove(Connection con, int id) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Connection con, Order order) throws DaoException {
        return false;
    }
}
