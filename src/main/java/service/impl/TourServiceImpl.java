package service.impl;

import dao.DAOFactory;
import dao.TourDAO;
import entity.Hotel;
import entity.Promotion;
import entity.Tour;
import entity.constant.TourType;
import exception.DAOException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.TourService;
import util.DBUtils;

import java.sql.Connection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class-service that handle all business logic related with Tour and DAO
 * implements TourService interface
 *
 * @author Anatolii Koliaka
 */
public class TourServiceImpl implements TourService {

    private static final Logger LOG = LogManager.getLogger(TourServiceImpl.class);

    @Override
    public boolean createNewTour(String name, String description, String imagePath, String priceString, String tourType
            , String numOfPersonsString, String hotelIdString, String promotionIdString, String isHotString) throws ServiceException {

        if (name == null || name.isEmpty() || priceString == null || priceString.isEmpty()
                || tourType == null || tourType.isEmpty()) {
            throw new ServiceException("Please, fill all required fields!");
        }

        Connection con = DBUtils.getInstance().getConnection();
        try {
            int price = Integer.parseInt(priceString);
            int tourTypeId = TourType.valueOf(tourType.toUpperCase()).getIndex();
            int numOfPersons = Integer.parseInt(numOfPersonsString);
            int hotelId = Integer.parseInt(hotelIdString);
            int promotionId = Integer.parseInt(promotionIdString);
            boolean isHot = Boolean.getBoolean(isHotString);

            Tour tour = createTour(name, description, imagePath, price, tourTypeId, numOfPersons, hotelId, promotionId, isHot);
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
    public List<Tour> retrieveAllTours() throws ServiceException {
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
            result = tourDAO.findById(con,tourId);
            return result;
        } catch (DAOException e) {
            LOG.error("Unable to retrieve tour by id!");
            throw new ServiceException("Unable to retrieve tour by id!", e);
        }
    }

    @Override
    public List<Tour> searchTourByType(List<Tour> tours, TourType type){
        return tours.stream()
                .filter(tour -> tour.getTourTypeId() == type.getIndex())
//                .sorted(Comparator.comparing(Tour::isHot).reversed())
                .collect(Collectors.toList());
    }

    private Tour createTour(String name, String description, String imagePath, int price, int tourTypeId, int numOfPersons
            , int hotelId, int promotionId, boolean isHot) {
        return new Tour.Builder()
                .withName(name)
                .withDescription(description)
                .withImage(imagePath)
                .withPrice(price)
                .withTourTypeId(tourTypeId)
                .withNumOfPersons(numOfPersons)
                .withHotelId(hotelId)
                .withPromotionId(promotionId)
                .isHot(isHot)
                .build();
    }
}
