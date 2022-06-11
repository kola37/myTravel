package service;

import entity.Tour;

/**
 * TourService interface that extends interface Service
 * and performs an abstract methods to handle business requirements with Tour object and DAO
 *
 * @author Anatolii Koliaka
 */
public interface TourService extends Service<Tour>{

    boolean createTour();
}
