package controller.command.impl;

import controller.PagePath;
import controller.command.Command;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * Admin add new manager command adds new manager to database
 *
 * @author Anatolii Koliaka
 */
public class AdminAddNewManagerCommand implements Command {

    private static final long serialVersionUID = 9038030710947130668L;

    private static final Logger LOG = LogManager.getLogger(AdminAddNewManagerCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER = "user";
    private static final String PARAMETER_COMMAND_NEXT = "commandNext";
    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_FIRST_NAME = "firstName";
    private static final String PARAMETER_LAST_NAME = "lastName";
    private static final String PARAMETER_EMAIL = "email";
    private static final String PARAMETER_ROLE = "role";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTR_USER);

        if (user == null || user.getRoleId() == UserRole.USER.getIndex() || user.getRoleId() == UserRole.MANAGER.getIndex()) {
            throw new CommandException("Please sign up with admin role to continue!");
        }

        String command = req.getParameter(PARAMETER_COMMAND_NEXT);

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
                LOG.trace("New manager added to DB!");
                result = new CommandResult(PagePath.PAGE_SUCCESS + command, CommandResultType.REDIRECT);
            }
        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}