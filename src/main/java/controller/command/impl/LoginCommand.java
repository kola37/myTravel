package controller.command.impl;

import controller.command.Command;
import controller.PagePath;
import controller.command.CommandResult;
import controller.command.CommandResultType;
import entity.User;
import entity.constant.UserRole;
import exception.CommandException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.UserService;

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
//    private static final String COMMAND = "my-travel?command=";


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();

        if(!session.isNew() && session.getAttribute(ATTR_USER) != null){
            throw new CommandException("Hi, " + session.getAttribute(ATTR_USER_LOGIN) + "! You are already logged in!");
        }

        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR,CommandResultType.FORWARD);

        UserService userService = ServiceFactory.getInstance().getUserService();

        try {
            Optional<User> optionalUser = userService.login(login, password);
            if (optionalUser.isEmpty() || !optionalUser.get().getPassword().equals(password)) {
                throw new CommandException("Entered login or password is invalid!");
            }
            User user = optionalUser.get();
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
