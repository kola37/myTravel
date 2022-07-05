package com.mytravel.service;

import com.mytravel.entity.Tour;
import com.mytravel.exception.ServiceException;

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
     * @param discountRateString tour's discount rate String value
     * @param maxDiscountString tour's max discount String value
     * @param isHot tour's is hot tour status String value
     * @return true if new tour created successfully, false otherwise
     * @throws ServiceException
     */
    boolean createNewTour(String name, String description, String imagePath, String priceString, String tourType, String numOfPersonsString
            , String hotelIdString, String discountRateString, String maxDiscountString, String isHot) throws ServiceException;

    /**
     * Method to retrieve tour by tour's id
     *
     * @return an Optional of Tour if present
     *      * an empty Optional otherwise
     * @throws ServiceException
     */
    Optional<Tour> retrieveTourById(int tourId) throws ServiceException;

//    /**
//     * Method to search tours by tour type.
//     * This method of sorting is default
//     * @param tours List to sort
//     * @return sorted List of tours
//     */
//    List<Tour> searchTourByType(List<Tour> tours, TourType type);

    /**
     * Method to delete tour from database using tour id like argument
     * @param tourId tour's id
     * @return true if tour successfully deleted from database, false otherwise
     * @throws ServiceException
     */
    boolean deleteTour(int tourId) throws ServiceException;

    /**
     * Method to update tour discount and isHot information in database
     * @param tourIdString tour's id String value
     * @param discRateString tour's discount rate String value
     * @param maxDiscString tour's max discount String value
     * @param isHotString tour's isHot String value
     * @return true if tour successfully updated, false otherwise
     * @throws ServiceException
     */
    boolean updateDiscountAndHot(String tourIdString, String discRateString, String maxDiscString, String isHotString) throws ServiceException;

    /**
     * Method to update tour information in database
     *
     * @param tourIdString tour's id String value
     * @param name tour's name
     * @param description tour's description
     * @param image tour's image path
     * @param priceString tour's price String value
     * @param discRateString tour's discount rate String value
     * @param maxDiscString tour's max discount String value
     * @param tourTypeString tour's type String value
     * @param numOfPersonsString tour's number of persons String value
     * @param hotelIdString tour's hotel id String value
     * @param isHotString tour's isHot String value
     * @return true if tour successfully updated, false otherwise
     * @throws ServiceException
     */
    boolean updateTour(String tourIdString, String name, String description, String image, String priceString, String discRateString,
                       String maxDiscString, String tourTypeString, String numOfPersonsString, String hotelIdString, String isHotString)throws ServiceException;

}
