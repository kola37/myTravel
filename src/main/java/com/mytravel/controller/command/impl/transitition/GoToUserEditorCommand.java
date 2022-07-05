package com.mytravel.controller.command.impl.transitition;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.entity.Order;
import com.mytravel.entity.User;
import com.mytravel.entity.constant.UserRole;
import com.mytravel.exception.CommandException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.OrderService;
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
import java.util.List;
import java.util.stream.Collectors;

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
    private static final String ATTR_TABLE_TITTLE = "tableTittle";
    private static final String PARAMETER_USER_ROLE = "role";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTR_USER);

        if (user == null || user.getRoleId() == UserRole.USER.getIndex() || user.getRoleId() == UserRole.MANAGER.getIndex()) {
            throw new CommandException("Please sign up with admin role to continue!");
        }

        String userRoleString = req.getParameter(PARAMETER_USER_ROLE);

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            List<User> usersAll = userService.retrieveAll();

            if(userRoleString != null && !userRoleString.isEmpty()){
                int roleId = UserRole.valueOf(userRoleString.toUpperCase()).getIndex();
                List<User> usersFiltered = getAllUsersByRole(usersAll, roleId);
                req.setAttribute(ATTR_TABLE_TITTLE, userRoleString);
                req.setAttribute(ATTR_USERS, usersFiltered);
                LOG.debug("List of users with role " + userRoleString + " retrieved from DB!" );
            }else{
                req.setAttribute(ATTR_USERS, usersAll);
                LOG.debug("All users retrieved from DB!");
            }

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

    private List<User> getAllUsersByRole(List<User> users, int roleId){
        return users.stream().filter(user -> user.getRoleId() == roleId).collect(Collectors.toList());
    }
}
