package service;

import entity.Hotel;
import entity.Tour;
import exception.ServiceException;

import java.util.List;

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
     * Method to retrieve all hotels
     *
     * @return List of hotels
     * @throws ServiceException
     */
    List<Hotel> retrieveAllHotels() throws ServiceException;

}
