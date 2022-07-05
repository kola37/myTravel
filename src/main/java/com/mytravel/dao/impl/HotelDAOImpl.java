package com.mytravel.dao.impl;

import com.mytravel.entity.Hotel;
import com.mytravel.exception.DAOException;
import com.mytravel.util.DBUtils;
import com.mytravel.dao.HotelDAO;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mytravel.util.DBUtils.close;


/**
 * Class HotelDAOIMpl implements HotelDAO interface
 * and provides a several methods that performs CRUD operations on Hotel objects
 *
 * @author Anatolii Koliaka
 */
public class HotelDAOImpl implements HotelDAO {

    private static final Logger LOG = LogManager.getLogger(HotelDAOImpl.class);

    private static final String SQL_INSERT_HOTEL = "INSERT INTO hotels VALUES (DEFAULT, ?, ?)";
    private static final String SQL_FIND_ALL_HOTEL = "SELECT * FROM hotels";
    private static final String SQL_FIND_HOTEL_BY_ID = "SELECT * FROM hotels WHERE id = ?";
    private static final String SQL_FIND_HOTEL_BY_NAME = "SELECT * FROM hotels WHERE name = ?";
//    private static final String SQL_FIND_ALL_HOTEL_FROM_TOURS = "SELECT * FROM hotels JOIN tours WHERE tours.hotel_id = hotels.id";
    private static final String SQL_UPDATE_HOTEL = "UPDATE hotels SET name = ?, hotel_type_id = ? WHERE id = ?";
    private static final String SQL_DELETE_HOTEL = "DELETE FROM hotels WHERE id = ?";





    @Override
    public Optional<Hotel> findById(Connection con, int id) throws DAOException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_FIND_HOTEL_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractHotel(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find hotel in database! ", e);
            throw new DAOException("Cannot find hotel in database! ", e);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Hotel> findByName(Connection con, String name) throws DAOException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_FIND_HOTEL_BY_NAME);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractHotel(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find hotel in database! ", e);
            throw new DAOException("Cannot find hotel in database! ", e);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }

        return Optional.empty();
    }

    @Override
    public List<Hotel> findAll(Connection con) throws DAOException {
        List<Hotel> hotels = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_HOTEL);
            while (rs.next()) {
                hotels.add(extractHotel(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find hotel in database! ", e);
            throw new DAOException("Cannot find hotel in database! ", e);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(stmt);
        }
        return hotels;
    }

    @Override
    public int insert(Connection con, Hotel hotel) throws DAOException {
        int result = -1;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_INSERT_HOTEL, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, hotel.getName());
            pstmt.setInt(k, hotel.getHotelTypeId());

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    hotel.setId(rs.getInt(1));
                    result = hotel.getId();
                }
            }
        } catch (SQLException e) {
            LOG.error("Cannot insert hotel into database! ", e);
            throw new DAOException("Cannot insert hotel into database! ", e);
        } finally {
            DBUtils.close(rs);
            DBUtils.close(pstmt);
        }
        return result;
    }

    @Override
    public boolean remove(Connection con, int id) throws DAOException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_DELETE_HOTEL);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Cannot remove hotel from database! ", e);
            throw new DAOException("Cannot remove hotel from database! ", e);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    @Override
    public boolean update(Connection con, Hotel hotel) throws DAOException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_UPDATE_HOTEL);
            int k = 1;
            pstmt.setString(k++, hotel.getName());
            pstmt.setInt(k, hotel.getHotelTypeId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Cannot update hotel in database! ", e);
            throw new DAOException("Cannot update hotel in database! ", e);
        } finally {
            DBUtils.close(pstmt);
        }
    }

    private static Hotel extractHotel(ResultSet rs) throws SQLException {
        return new Hotel.Builder()
                .withId(rs.getInt("id"))
                .withName(rs.getString("name"))
                .withHotelTypeId(rs.getInt("hotel_type_id"))
                .build();
    }
}
