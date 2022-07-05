package com.mytravel.controller.command.impl;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.entity.User;
import com.mytravel.entity.constant.UserRole;
import com.mytravel.exception.CommandException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.ServiceFactory;
import com.mytravel.service.UserService;
import com.mytravel.controller.command.Command;
import com.mytravel.controller.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Login command
 *
 * @author Anatolii Koliaka
 */
public class LoginCommand implements Command {

    private static final long serialVersionUID = -3240440488341003325L;

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER = "user";
    private static final String ATTR_USER_ROLE = "userRole";
    private static final String ATTR_USER_LOGIN = "userLogin";
    private static final String COMMAND_PREVIOUS = "commandPrevious";


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();

        if(!session.isNew() && session.getAttribute(ATTR_USER) != null){
            throw new CommandException("Hi, " + session.getAttribute(ATTR_USER_LOGIN) + "! You are already logged in!");
        }

        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        UserService userService = ServiceFactory.getInstance().getUserService();

        try {
            Optional<User> optionalUser = userService.login(login, password);
            if (optionalUser.isEmpty() || !optionalUser.get().getPassword().equals(password)) {
                throw new CommandException("Entered login or password is invalid!");
            }
            User user = optionalUser.get();
            if (user.isBlocked()){
                LOG.trace("User is blocked and have no access to account: " + user);
                throw new CommandException("Unfortunately, your account has been blocked!");
            }
            UserRole role = UserRole.getRole(user.getRoleId());
            LOG.trace("Found user in DB: " + user);

            session.setAttribute(ATTR_USER, user);
            LOG.trace("Set the session attribute 'user': " + user);

            session.setAttribute(ATTR_USER_LOGIN, user.getLogin());
            LOG.trace("Set the session attribute 'userLogin': " + user.getLogin());

            session.setAttribute(ATTR_USER_ROLE, role.getName());
            LOG.trace("Set the session attribute 'userRole': " + role.getName());


            String command = (String) session.getAttribute(COMMAND_PREVIOUS);
            if (command != null){
                session.removeAttribute(COMMAND_PREVIOUS);
                //redirect to page user came from before authorization
                result = new CommandResult(PagePath.PAGE_LOGIN_SUCCESS
                        + command, CommandResultType.REDIRECT);
            }else{
                result = new CommandResult(PagePath.PAGE_LOGIN_SUCCESS, CommandResultType.REDIRECT);
            }

        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}
