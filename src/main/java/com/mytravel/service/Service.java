package com.mytravel.service;


import com.mytravel.exception.ServiceException;

import java.util.List;

/**
 * Service interface that performs an abstract methods to handle business requirements with T entity and DAO
 *
 * @author Anatolii Koliaka
 */
public interface Service<T>{

    /**
     * Method to retrieve all entities from database
     * @return List of T entities
     * @throws ServiceException
     */
    List<T> retrieveAll() throws ServiceException;
}
