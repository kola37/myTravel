package dao.impl;

import dao.HotelDao;
import entity.Hotel;
import exception.DaoException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.sql.Connection;
import java.util.List;
import java.util.Optional;



public class HotelDaoImpl implements HotelDao {

    private static final Logger LOG = LogManager.getLogger(HotelDaoImpl.class);



    @Override
    public Optional<Hotel> findById(Connection con, int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Hotel> findAll(Connection con) throws DaoException {
        return null;
    }

    @Override
    public int insert(Connection con, Hotel hotel) throws DaoException {
        return 0;
    }

    @Override
    public boolean remove(Connection con, int id) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Connection con, Hotel hotel) throws DaoException {
        return false;
    }
}
