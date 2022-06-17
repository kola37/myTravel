package service.impl;

import dao.DAOFactory;
import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import entity.constant.TourType;
import entity.constant.UserRole;
import exception.CommandException;
import exception.DAOException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import service.validator.Validator;
import service.validator.impl.EmailValidatorImpl;
import service.validator.impl.NameValidatorImpl;
import util.DBUtils;

import java.sql.Connection;
import java.util.Optional;

/**
 * Class-service that handle all business logic related with User and DAO
 * implements UserService interface
 *
 * @author Anatolii Koliaka
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class);


    @Override
    public Optional<User> login(String login, String password) throws ServiceException {

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            throw new ServiceException("Please, enter login and password to authorize!");
        }

        Connection con = DBUtils.getInstance().getConnection();
        try {
            UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
            return userDAO.findByLogin(con, login);
        } catch (DAOException e) {
            LOG.error("Unable to login user!", e);
            throw new ServiceException("Unable to login user", e);
        } finally {
            DBUtils.close(con);
        }
    }

    @Override
    public Optional<User> register(String login, String password, String firstName, String lastName, String email, String role) throws ServiceException {

        if (login == null || login.isEmpty() || password == null || password.isEmpty()
                || firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()
                || email == null || email.isEmpty() || role == null) {
            throw new ServiceException("Please, fill all required fields!");
        }

        if (!isUserEmailValid(email)) {
            throw new ServiceException("Please, enter a valid email!");
        }

        if (!isUserInfoValid(firstName, lastName)) {
            throw new ServiceException("Your name looks strange! Please, check his spelling!");
        }

        Connection con = DBUtils.getInstance().getConnection();
        try {
            int roleId = UserRole.valueOf(role.toUpperCase()).getIndex();

            User user = createUser(login, password, firstName, lastName, email, roleId);
            UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

            if (userDAO.findByLogin(con, login).isPresent()) {
                LOG.debug("User with login " + login + " already exist!");
                throw new ServiceException("User with login " + login + " already exist!");
            }
            int userId = userDAO.insert(con, user);
            user.setId(userId);
            LOG.trace("New user registered successfully!");
            return Optional.of(user);
        } catch (DAOException | IllegalArgumentException e) {
            LOG.error("Failed to register new user!", e);
            throw new ServiceException("Failed to register new user!", e);
        } finally {
            DBUtils.close(con);
        }
    }


    private User createUser(String login, String password, String firstName, String lastName, String email, int roleId) {
        return new User.Builder()
                .withLogin(login)
                .withPassword(password)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withRoleId(roleId)
                .build();
    }


    private boolean isUserEmailValid(String email) {
        return new EmailValidatorImpl().isValid(email);
    }

    private boolean isUserInfoValid(String firstName, String lastName) {
        Validator userInfoValidator = new NameValidatorImpl();
        return (userInfoValidator.isValid(firstName) && userInfoValidator.isValid(lastName));
    }
}
