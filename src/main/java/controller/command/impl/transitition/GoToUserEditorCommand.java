package controller.command.impl.transitition;

import controller.PagePath;
import controller.command.Command;
import controller.command.CommandResult;
import controller.command.CommandResultType;
import entity.Order;
import entity.User;
import entity.constant.UserRole;
import exception.CommandException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import service.ServiceFactory;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Go to user editor admin page command
 *
 * @author Anatolii Koliaka
 */
public class GoToUserEditorCommand implements Command {

    private static final long serialVersionUID = -3774191071153127379L;

    private static final Logger LOG = LogManager.getLogger(GoToUserEditorCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER = "user";
    private static final String ATTR_USERS = "users";
    private static final String ATTR_ORDERS = "orders";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTR_USER);

        if (user == null || user.getRoleId() == UserRole.USER.getIndex() || user.getRoleId() == UserRole.MANAGER.getIndex()) {
            throw new CommandException("Please sign up with admin role to continue!");
        }

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            List<User> users = userService.retrieveAll();
            req.setAttribute(ATTR_USERS, users);

            OrderService orderService = ServiceFactory.getInstance().getOrderService();
            List<Order> orders = orderService.retrieveAll();
            req.setAttribute(ATTR_ORDERS, orders);

            result = new CommandResult(PagePath.PAGE_ADMIN_USER_EDITOR, CommandResultType.FORWARD);
        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}
