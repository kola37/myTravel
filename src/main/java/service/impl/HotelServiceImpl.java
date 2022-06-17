package service.impl;

import dao.DAOFactory;
import dao.HotelDAO;
import entity.Hotel;
import entity.constant.HotelType;
import exception.DAOException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.HotelService;
import util.DBUtils;

import java.sql.Connection;
import java.util.List;

/**
 * Class-service that handle all business logic related with Hotel and DAO
 * implements HotelService interface
 *
 * @author Anatolii Koliaka
 */
public class HotelServiceImpl implements HotelService {

    private static final Logger LOG = LogManager.getLogger(HotelServiceImpl.class);

    @Override
    public boolean addNewHotel(String name, String hotelType) throws ServiceException {

        if (name == null || name.isEmpty() || hotelType == null || hotelType.isEmpty()) {
            throw new ServiceException("Please, fill all required fields!");
        }

        Connection con = DBUtils.getInstance().getConnection();
        try {
            int hotelTypeId = HotelType.valueOf(hotelType.toUpperCase()).getIndex();
            Hotel hotel = createHotel(name, hotelTypeId);
            HotelDAO hotelDAO = DAOFactory.getInstance().getHotelDAO();

            if (hotelDAO.findByName(con, name).isPresent()) {
                LOG.debug("Hotel with name " + name + " already exist!");
                throw new ServiceException("Hotel with name " + name + " already exist!");
            }

            hotelDAO.insert(con, hotel);
            LOG.trace("New hotel added successfully!");
            return true;

        } catch (DAOException | IllegalArgumentException e) {
            LOG.error("Failed to add a new hotel!", e);
            throw new ServiceException("Failed to add a new hotel!", e);
        }finally {
            DBUtils.close(con);
        }

    }

    @Override
    public List<Hotel> retrieveAllHotels() throws ServiceException {
        Connection con = DBUtils.getInstance().getConnection();
        try {
            HotelDAO hotelDAO = DAOFactory.getInstance().getHotelDAO();
            List<Hotel> result = hotelDAO.findAll(con);
            return result;

        } catch (DAOException e) {
            LOG.error("Unable to retrieve all hotels!");
            throw new ServiceException("Unable to retrieve all hotels!", e);
        } finally {
            DBUtils.close(con);
        }
    }

    private Hotel createHotel(String name, int hotelTypeId) {
        return new Hotel.Builder()
                .withName(name)
                .withHotelTypeId(hotelTypeId)
                .build();
    }
}
