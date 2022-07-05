package com.mytravel.controller.command.impl;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.entity.User;
import com.mytravel.exception.CommandException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.ServiceFactory;
import com.mytravel.service.UserService;
import com.mytravel.controller.PagePath;
import com.mytravel.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Register command
 *
 * @author Anatolii Koliaka
 */
public class RegisterCommand implements Command {

    private static final long serialVersionUID = 6050322266040581845L;

    private static final Logger LOG = LogManager.getLogger(RegisterCommand.class);

    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_FIRST_NAME = "firstName";
    private static final String PARAMETER_LAST_NAME = "lastName";
    private static final String PARAMETER_EMAIL = "email";
    private static final String PARAMETER_ROLE = "role";
    private static final String ATTR_USER = "user";
    private static final String ATTR_USER_ROLE = "userRole";
    private static final String ATTR_USER_LOGIN = "userLogin";
    private static final String ATTR_ERROR_MESSAGE = "errorMessage";


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();

        if (!session.isNew() && session.getAttribute(ATTR_USER) != null) {
            throw new CommandException("Hi, " + session.getAttribute(ATTR_USER_LOGIN) + "! You are already logged in!");
        }

        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);
        String firstName = req.getParameter(PARAMETER_FIRST_NAME);
        String lastName = req.getParameter(PARAMETER_LAST_NAME);
        String email = req.getParameter(PARAMETER_EMAIL);
        String role = req.getParameter(PARAMETER_ROLE);

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            Optional<User> userOptional = userService.register(login, password, firstName, lastName, email, role);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                LOG.trace("Registered new user: " + user);

                session.setAttribute(ATTR_USER, user);
                LOG.trace("Set the session attribute 'user': " + user);

                session.setAttribute(ATTR_USER_LOGIN, user.getLogin());
                LOG.trace("Set the session attribute 'userLogin': " + user.getLogin());

                session.setAttribute(ATTR_USER_ROLE, role);
                LOG.trace("Set the session attribute 'userRole': " + role);

                result = new CommandResult(PagePath.PAGE_REGISTER_SUCCESS, CommandResultType.REDIRECT);
            }
        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }

}
