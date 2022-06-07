package dao.impl;

import dao.HotelDAO;
import entity.Hotel;
import exception.DAOException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.sql.Connection;
import java.util.List;
import java.util.Optional;



public class HotelDAOImpl implements HotelDAO {

    private static final Logger LOG = LogManager.getLogger(HotelDAOImpl.class);



    @Override
    public Optional<Hotel> findById(Connection con, int id) throws DAOException {
        return Optional.empty();
    }

    @Override
    public List<Hotel> findAll(Connection con) throws DAOException {
        return null;
    }

    @Override
    public int insert(Connection con, Hotel hotel) throws DAOException {
        return 0;
    }

    @Override
    public boolean remove(Connection con, int id) throws DAOException {
        return false;
    }

    @Override
    public boolean update(Connection con, Hotel hotel) throws DAOException {
        return false;
    }
}
