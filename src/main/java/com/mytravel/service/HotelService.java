package com.mytravel.service;

import com.mytravel.entity.Hotel;
import com.mytravel.exception.ServiceException;

import java.util.Optional;

/**
 * HotelService interface that extends interface Service
 * and performs an abstract methods to handle business requirements with Hotel object and DAO
 *
 * @author Anatolii Koliaka
 */
public interface HotelService extends Service<Hotel> {

    /**
     * Method to add new hotel
     *
     * @param name hotel's name
     * @param hotelType hotel's type String value
     * @return true if new hotel added successfully, false otherwise
     * @throws ServiceException
     */
    boolean addNewHotel(String name, String hotelType) throws ServiceException;

    /**
     * Method to retrieve hotel by hotel's name
     *
     * @return an Optional of THotel if present
     *      * an empty Optional otherwise
     * @throws ServiceException
     */
    Optional<Hotel> retrieveHotelByName(String hotelName) throws ServiceException;


}
