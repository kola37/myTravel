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
 * Edit user information command class
 *
 * @author Anatolii Koliaka
 */
public class EditUserCommand implements Command {

    private static final long serialVersionUID = -8878689196372580028L;

    private static final Logger LOG = LogManager.getLogger(EditUserCommand.class);

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
    private static final String ATTR_MESSAGE = "message";
    private static final String MSG_USER_INFO_UPDATED = "Your information successfully updated!";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();
        User editableUser = (User) session.getAttribute(ATTR_USER);

        if (editableUser == null || editableUser.getRoleId() == UserRole.ADMIN.getIndex()) {
            LOG.error("Users with role 'user' and 'manager' only can edit user info!");
            throw new CommandException("Please, login with user or manager account to continue!");
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
            Optional<User> userOptional = userService.editUserInfo(editableUser.getId() ,login, password, firstName, lastName, email, role);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                LOG.trace("Updated info of user: " + user);

                session.setAttribute(ATTR_USER, user);
                LOG.trace("Set the session attribute 'user': " + user);

                session.setAttribute(ATTR_USER_LOGIN, user.getLogin());
                LOG.trace("Set the session attribute 'userLogin': " + user.getLogin());

                session.setAttribute(ATTR_USER_ROLE, role);
                LOG.trace("Set the session attribute 'userRole': " + role);

                session.setAttribute(ATTR_MESSAGE,MSG_USER_INFO_UPDATED);

                result = new CommandResult(PagePath.PAGE_REGISTER_SUCCESS, CommandResultType.REDIRECT);

            }
        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}
