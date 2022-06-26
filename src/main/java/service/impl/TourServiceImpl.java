package service.impl;

import dao.DAOFactory;
import dao.TourDAO;
import dao.UserDAO;
import entity.Hotel;
import entity.Tour;
import entity.User;
import entity.constant.TourType;
import exception.DAOException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.HotelService;
import service.ServiceFactory;
import service.TourService;
import util.DBUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Class-service that handle all business logic related with Tour and DAO
 * implements TourService interface
 *
 * @author Anatolii Koliaka
 */
public class TourServiceImpl implements TourService {

    private static final Logger LOG = LogManager.getLogger(TourServiceImpl.class);

    private static final String DEFAULT_IMAGE = "/images/default-icon.png";

    @Override
    public boolean createNewTour(String name, String description, String imagePath, String priceString, String tourType
            , String numOfPersonsString, String hotelIdString, String discountRateString, String maxDiscountString, String isHotString) throws ServiceException {

        if (!checkRequiredFields(name, priceString, tourType, hotelIdString)) {
            throw new ServiceException("Please, fill all required fields!");
        }

        if (imagePath == null || imagePath.isEmpty()) {
            imagePath = DEFAULT_IMAGE;
        }

        Connection con = DBUtils.getInstance().getConnection();
        try {
            int price = Integer.parseInt(priceString);
            int tourTypeId = TourType.valueOf(tourType.toUpperCase()).getIndex();
            int numOfPersons = Integer.parseInt(numOfPersonsString);
            int hotelId = Integer.parseInt(hotelIdString);
            int discountRate = Integer.parseInt((discountRateString == null || discountRateString.isEmpty()) ? "0" : discountRateString);
            int maxDiscount = Integer.parseInt((maxDiscountString == null || maxDiscountString.isEmpty()) ? "0" : maxDiscountString);
            boolean isHot = Boolean.parseBoolean(isHotString);

            Tour tour = createTour(name, description, imagePath, price, tourTypeId, numOfPersons, hotelId, discountRate, maxDiscount, isHot);
            TourDAO tourDAO = DAOFactory.getInstance().getTourDAO();

            if (tourDAO.findByName(con, name).isPresent()) {
                LOG.debug("Tour with name " + name + " already exist!");
                throw new ServiceException("Tour with name " + name + " already exist!");
            }

            tourDAO.insert(con, tour);
            LOG.trace("New tour created successfully!");
            return true;
        } catch (DAOException | IllegalArgumentException e) {
            LOG.error("Failed to create a new tour!", e);
            throw new ServiceException("Failed to create a new tour!", e);
        } finally {
            DBUtils.close(con);
        }
    }

    @Override
    public List<Tour> retrieveAll() throws ServiceException {
        Connection con = DBUtils.getInstance().getConnection();
        try {
            TourDAO tourDAO = DAOFactory.getInstance().getTourDAO();
            List<Tour> result = tourDAO.findAll(con);
            return result;
        } catch (DAOException e) {
            LOG.error("Unable to retrieve all tours!");
            throw new ServiceException("Unable to retrieve all tours!", e);
        } finally {
            DBUtils.close(con);
        }
    }

    @Override
    public Optional<Tour> retrieveTourById(int tourId) throws ServiceException {
        Connection con = DBUtils.getInstance().getConnection();
        try {
            TourDAO tourDAO = DAOFactory.getInstance().getTourDAO();
            Optional<Tour> result;
            result = tourDAO.findById(con, tourId);
            return result;
        } catch (DAOException e) {
            LOG.error("Unable to retrieve tour by id!");
            throw new ServiceException("Unable to retrieve tour by id!", e);
        }
    }

//    @Override
//    public List<Tour> searchTourByType(List<Tour> tours, TourType type){
//        return tours.stream()
//                .filter(tour -> tour.getTourTypeId() == type.getIndex())
////                .sorted(Comparator.comparing(Tour::isHot).reversed())
//                .collect(Collectors.toList());
//    }

    @Override
    public boolean deleteTour(int tourId) throws ServiceException {
        Connection con = DBUtils.getInstance().getConnection();
        try {
            TourDAO tourDAO = DAOFactory.getInstance().getTourDAO();
            return tourDAO.remove(con, tourId);
        } catch (DAOException e) {
            LOG.error("Unable to delete tour from DB!");
            throw new ServiceException("Unable to tour from DB!", e);
        } finally {
            DBUtils.close(con);
        }
    }

    @Override
    public boolean updateDiscountAndHot(String tourIdString, String discRateString, String maxDiscString, String isHotString) throws ServiceException {

        Connection con = DBUtils.getInstance().getConnection();
        try {
            int tourId = Integer.parseInt(tourIdString);
            int discRate = Integer.parseInt(discRateString);
            int maxDisc = Integer.parseInt(maxDiscString);
            boolean isHot = Boolean.parseBoolean(isHotString);

            TourDAO tourDAO = DAOFactory.getInstance().getTourDAO();
            Optional<Tour> optTour = tourDAO.findById(con, tourId);
            if (optTour.isPresent()) {
                Tour tour = optTour.get();
                tour.setDiscountRate(discRate);
                tour.setMaxDiscount(maxDisc);
                if (!isHotString.isEmpty())
                    tour.setHot(isHot);//in case we have got an empty string isHotString skip set operation
                return tourDAO.update(con, tour);
            }
        } catch (DAOException | NumberFormatException e) {
            LOG.error("Unable to update tour in DB!");
            throw new ServiceException("Unable to update tour in DB!", e);
        } finally {
            DBUtils.close(con);
        }
        return false;
    }

    @Override
    public boolean updateTour(String tourIdString, String name, String description, String image, String priceString, String discRateString, String maxDiscString, String tourTypeString, String numOfPersonsString, String hotelIdString, String isHotString) throws ServiceException {

        if (!checkRequiredFields(name, priceString, tourTypeString, hotelIdString)) {
            throw new ServiceException("Please, fill all required fields!");
        }

        Connection con = DBUtils.getInstance().getConnection();
        try {
            int tourId = Integer.parseInt(tourIdString);
            int price = Integer.parseInt(priceString);
            int tourTypeId = TourType.valueOf(tourTypeString.toUpperCase()).getIndex();
            int numOfPersons = Integer.parseInt(numOfPersonsString);
            int hotelId = Integer.parseInt(hotelIdString);
            int discountRate = Integer.parseInt((discRateString == null || discRateString.isEmpty()) ? "0" : discRateString);
            int maxDiscount = Integer.parseInt((maxDiscString == null || maxDiscString.isEmpty()) ? "0" : maxDiscString);
            boolean isHot = Boolean.parseBoolean(isHotString);

            TourDAO tourDAO = DAOFactory.getInstance().getTourDAO();

            if (image == null || image.isEmpty()) {
                image = getTourPhotoIfExist(con, tourDAO, tourId);
            }

            Tour tour = createTour(name, description, image, price, tourTypeId, numOfPersons, hotelId, discountRate, maxDiscount, isHot);
            tour.setId(tourId);

            if (isTourNameExist(con, tourDAO, tour)) {
                LOG.debug("Tour with name " + name + " already exist!");
                throw new ServiceException("Tour with name " + name + " already exist! Please, try another one!");
            }
            return tourDAO.update(con, tour);
        } catch (DAOException |
                 IllegalArgumentException e) {
            LOG.error("Failed to update tour information!", e);
            throw new ServiceException("Failed to update tour information!", e);
        } finally {
            DBUtils.close(con);
        }

    }

    private Tour createTour(String name, String description, String imagePath, int price, int tourTypeId, int numOfPersons
            , int hotelId, int discountRate, int maxDiscount, boolean isHot) {
        return new Tour.Builder()
                .withName(name)
                .withDescription(description)
                .withImage(imagePath)
                .withPrice(price)
                .withTourTypeId(tourTypeId)
                .withNumOfPersons(numOfPersons)
                .withHotelId(hotelId)
                .withDiscountRate(discountRate)
                .withMaxDiscount(maxDiscount)
                .isHot(isHot)
                .build();
    }

    private int getHotelIdByName(String hotelName) throws ServiceException {
        HotelService hotelService = ServiceFactory.getInstance().getHotelService();
        Optional<Hotel> optHotel = hotelService.retrieveHotelByName(hotelName);
        if (optHotel.isEmpty()) throw new ServiceException("Cannot find tour with name " + hotelName);
        return optHotel.get().getId();
    }

    private boolean checkRequiredFields(String name, String priceString, String tourTypeString, String hotelIdString) {
        return (name != null && !name.isEmpty() && priceString != null && !priceString.isEmpty()
                && tourTypeString != null && !tourTypeString.isEmpty()
                && hotelIdString != null && !hotelIdString.isEmpty());
    }

    /**
     * Method that find in database tour with supposed tour name and check if this name is assigned to editable tour
     *
     * @param con  connection object
     * @param dao  tourDao
     * @param tour editable tour
     * @return true if supposed name exist and assigned to another user, false otherwise
     * @throws DAOException
     */
    private static boolean isTourNameExist(Connection con, TourDAO dao, Tour tour) throws DAOException {
        Optional<Tour> tourWithName = dao.findByName(con, tour.getName());
        return (tourWithName.isPresent() && tourWithName.get().getId() != tour.getId());
    }

    /**
     * Method to find and return string path of tour image if tour image already exists and has value other than default
     *
     * @param con    connection object
     * @param dao    tourDao
     * @param tourId editable tour id
     * @return tour image path
     * @throws DAOException
     */
    private static String getTourPhotoIfExist(Connection con, TourDAO dao, int tourId) throws DAOException {
        Optional<Tour> optTour = dao.findById(con, tourId);
        return (optTour.isPresent() && !optTour.get().getImage().equals(DEFAULT_IMAGE)) ? optTour.get().getImage() : DEFAULT_IMAGE;
    }
}
