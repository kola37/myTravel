package service.impl;

import dao.DAOFactory;
import dao.UserDAO;
import dao.impl.UserDAOImpl;
import entity.User;
import entity.constant.UserRole;
import exception.DAOException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
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
    public Optional<User> login(String login) throws ServiceException {
        Connection con = DBUtils.getInstance().getConnection();
        try {
            UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
            return userDAO.findByLogin(con, login);
        } catch (DAOException e) {
            LOG.error("Unable to login user!", e);
            throw new ServiceException("Unable to login user", e);
        }finally {
            DBUtils.close(con);
        }
    }

    @Override
    public boolean register(String login, String password, String firstName, String lastName, String email, UserRole role) throws ServiceException {

        //check for all non-null fields and valid email and other
        //check if user exist
        //userRole should come from request: create 3 different login for ADMIN, MANAGER, USER
        //close connection

        Connection con = DBUtils.getInstance().getConnection();
        User user = createUser(login,password,firstName,lastName,email,role);
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            if (userDAO.findByLogin(con,login).isPresent()) {
                LOG.debug("User with login " + login + " already exist!");
                return false;
            }
            userDAO.insert(con,user);
            LOG.trace("New user registered successfully!");
            return true;
        } catch (DAOException e) {
            LOG.error("Failed to register new user!", e);
            throw new ServiceException("Failed to register new user!", e);
        }finally {
            DBUtils.close(con);
        }
    }



    private User createUser(String login, String password, String firstName, String lastName, String email, UserRole role){
        return new User.Builder()
                .withLogin(login)
                .withPassword(password)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmail(email)
                .withRoleId(role.getIndex())
                .build();
    }
}
