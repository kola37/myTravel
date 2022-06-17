package dao.impl;

import dao.TourDAO;
import entity.Tour;
import entity.User;
import exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static util.DBManager.close;

/**
 * Class TourDAOIMpl implements TourDAO interface
 * and provides a several methods that performs CRUD operations on Tour objects
 *
 * @author Anatolii Koliaka
 */
public class TourDAOImpl implements TourDAO {

    private static final Logger LOG = LogManager.getLogger(TourDAOImpl.class);

    private static final String SQL_INSERT_TOUR = "INSERT INTO tours VALUES (DEFAULT, ?, ?, ?, ?, ?, DEFAULT, ?, ?, DEFAULT)";
    private static final String SQL_FIND_ALL_TOURS = "SELECT * FROM tours";
    private static final String SQL_FIND_TOUR_BY_ID = "SELECT * FROM tours WHERE id = ?";
    private static final String SQL_FIND_TOUR_BY_NAME = "SELECT * FROM tours WHERE name = ?";
    private static final String SQL_UPDATE_TOUR = "UPDATE tours SET name = ?, description = ?, image = ?, price = ?, tour_type_id = ?" +
            ", num_of_persons = ?, hotel_id = ?, promotion_id= ?, is_hot = ? WHERE id = ?";
    private static final String SQL_DELETE_TOUR = "DELETE FROM tours WHERE id = ?";

    @Override
    public Optional<Tour> findById(Connection con, int id) throws DAOException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_FIND_TOUR_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractTour(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find tour in database! ", e);
            throw new DAOException("Cannot find tour in database! ", e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Tour> findByName(Connection con, String name) throws DAOException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_FIND_TOUR_BY_NAME);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(extractTour(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find tour in database! ", e);
            throw new DAOException("Cannot find tour in database! ", e);
        } finally {
            close(rs);
            close(pstmt);
        }

        return Optional.empty();
    }

    @Override
    public List<Tour> findAll(Connection con) throws DAOException {
        List<Tour> tours = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL_FIND_ALL_TOURS);
            while (rs.next()) {
                tours.add(extractTour(rs));
            }
        } catch (SQLException e) {
            LOG.error("Cannot find tour in database! ", e);
            throw new DAOException("Cannot find tour in database! ", e);
        } finally {
            close(rs);
            close(stmt);
        }
        return tours;
    }

    @Override
    public int insert(Connection con, Tour tour) throws DAOException {
        int result = -1;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(SQL_INSERT_TOUR, Statement.RETURN_GENERATED_KEYS);
            int k = 1;
            pstmt.setString(k++, tour.getName());
            pstmt.setString(k++, tour.getDescription());
            pstmt.setString(k++, tour.getImage());
            pstmt.setInt(k++, tour.getPrice());
            pstmt.setInt(k++, tour.getTourTypeId());
            pstmt.setInt(k++, tour.getNumOfPersons());
            pstmt.setInt(k++, tour.getHotelId());
            pstmt.setInt(k++, tour.getPromotionId());
            pstmt.setBoolean(k, tour.isHot());

            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    tour.setId(rs.getInt(1));
                    result = tour.getId();
                }
            }
        } catch (SQLException e) {
            LOG.error("Cannot insert tour into database! ", e);
            throw new DAOException("Cannot insert tour into database! ", e);
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
            pstmt = con.prepareStatement(SQL_DELETE_TOUR);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Cannot remove tour from database! ", e);
            throw new DAOException("Cannot remove tour from database! ", e);
        } finally {
            close(pstmt);
        }
    }

    @Override
    public boolean update(Connection con, Tour tour) throws DAOException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQL_UPDATE_TOUR);
            int k = 1;
            pstmt.setString(k++, tour.getName());
            pstmt.setString(k++, tour.getDescription());
            pstmt.setString(k++, tour.getImage());
            pstmt.setInt(k++, tour.getPrice());
            pstmt.setInt(k++, tour.getTourTypeId());
            pstmt.setInt(k++, tour.getNumOfPersons());
            pstmt.setInt(k++, tour.getHotelId());
            pstmt.setInt(k++, tour.getPromotionId());
            pstmt.setBoolean(k++, tour.isHot());
            pstmt.setInt(k, tour.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Cannot update tour in database! ", e);
            throw new DAOException("Cannot update tour in database! ", e);
        } finally {
            close(pstmt);
        }
    }

    private static Tour extractTour(ResultSet rs) throws SQLException {
        return new Tour.Builder()
                .withId(rs.getInt("id"))
                .withName(rs.getString("name"))
                .withDescription(rs.getString("description"))
                .withImage(rs.getString("image"))
                .withPrice(rs.getInt("price"))
                .withTourTypeId(rs.getInt("tour_type_id"))
                .withNumOfPersons(rs.getInt("num_of_persons"))
                .withHotelId(rs.getInt("hotel_id"))
                .withPromotionId(rs.getInt("promotion_id"))
                .isHot(rs.getBoolean("is_hot"))
                .build();
    }

}
