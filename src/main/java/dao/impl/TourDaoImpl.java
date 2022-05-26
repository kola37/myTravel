package dao.impl;

import dao.TourDao;
import entity.Tour;
import exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;


public class TourDaoImpl implements TourDao {

    @Override
    public Optional<Tour> findById(Connection con, int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Tour> findAll(Connection con) throws DaoException {
        return null;
    }

    @Override
    public int insert(Connection con, Tour tour) throws DaoException {
        return 0;
    }

    @Override
    public boolean remove(Connection con, int id) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Connection con, Tour tour) throws DaoException {
        return false;
    }
}
