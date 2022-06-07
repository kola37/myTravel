package dao.impl;

import dao.TourDAO;
import entity.Tour;
import exception.DAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;


public class TourDAOImpl implements TourDAO {

    @Override
    public Optional<Tour> findById(Connection con, int id) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<Tour> findAll(Connection con) throws DAOException {
        return null;
    }

    @Override
    public int insert(Connection con, Tour tour) throws DAOException {
        return 0;
    }

    @Override
    public boolean remove(Connection con, int id) throws DAOException {
        return false;
    }

    @Override
    public boolean update(Connection con, Tour tour) throws DAOException {
        return false;
    }
}
