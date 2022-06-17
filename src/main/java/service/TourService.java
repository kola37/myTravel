package service;

import entity.Hotel;
import entity.Promotion;
import entity.Tour;
import entity.constant.TourType;
import exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * TourService interface that extends interface Service
 * and performs an abstract methods to handle business requirements with Tour object and DAO
 *
 * @author Anatolii Koliaka
 */
public interface TourService extends Service<Tour> {

    /**
     * Method to create new tour
     *
     * @param name tour's name
     * @param description tour's description
     * @param imagePath tour's image path
     * @param priceString tour's price String value
     * @param tourType tour's type String value
     * @param numOfPersonsString tour's number of person String value
     * @param hotelIdString tour's hotel id String value
     * @param promotionIdString tour's promotion id String value
     * @param isHot tour's is hot tour status String value
     * @return true if new tour created successfully, false otherwise
     * @throws ServiceException
     */
    boolean createNewTour(String name, String description, String imagePath, String priceString, String tourType, String numOfPersonsString
            , String hotelIdString, String promotionIdString, String isHot) throws ServiceException;


    /**
     * Method to retrieve all tours
     *
     * @return List of tours
     * @throws ServiceException
     */
    List<Tour> retrieveAllTours() throws ServiceException;

    /**
     * Method to retrieve tour by tour's id
     *
     * @return an Optional of Tour if present
     *      * an empty Optional otherwise
     * @throws ServiceException
     */
    Optional<Tour> retrieveTourById(int tourId) throws ServiceException;
}
