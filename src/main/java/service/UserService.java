package service;

import entity.User;
import entity.constant.UserRole;
import exception.ServiceException;

import java.util.Optional;

/**
 * UserService interface that extends interface Service
 * and performs an abstract methods to handle business requirements with User and DAO
 *
 * @author Anatolii Koliaka
 */
public interface UserService extends Service<User>{

    /**
     * Method to authenticate user
     *
     * @param login    user's email
     * @param password user's password
     * @return optional of User
     * @throws ServiceException
     */
    Optional<User> login(String login, String password) throws ServiceException;

    /**
     * Method to register new user
     *
     * @param login     user's login
     * @param password  user's password
     * @param firstName user's firstName
     * @param lastName  user's lastName
     * @param email     user's email
     * @param role     user's role
     * @return true if registration is successful, false otherwise
     * @throws ServiceException
     */
    boolean register(String login, String password, String firstName, String lastName, String email, UserRole role) throws ServiceException;

}