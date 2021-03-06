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

/**
 * Go to user cabinet page command
 *
 * @author Anatolii Koliaka
 */
public class GoToUserCabinetCommand implements Command {

    private static final long serialVersionUID = 4101287109734561570L;

    private static final Logger LOG = LogManager.getLogger(GoToUserCabinetCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER = "user";
    private static final String ATTR_USER_ORDERS = "userOrders";
    private static final String ATTR_MENU_CHAPTER = "menu";
    private static final String MENU_CHAPTER_MY_ORDERS = "myOrders";
    private static final String MENU_CHAPTER_MY_INFO = "myInfo";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTR_USER);

        if (user == null || user.getRoleId() == UserRole.ADMIN.getIndex()) {
            LOG.error("User cabinet allowed for users with role 'user' and 'manager' only!");
            throw new CommandException("Please, login with user or manager account to continue!");
        }

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);
        String menuChapter = req.getParameter(ATTR_MENU_CHAPTER);

        try {
            if(menuChapter != null && menuChapter.equals(MENU_CHAPTER_MY_ORDERS) && user.getRoleId() == UserRole.USER.getIndex()) {
                OrderService orderService = ServiceFactory.getInstance().getOrderService();
                List<Order> userOrders = orderService.retrieveUserOrdersByUserId(user.getId());
                req.setAttribute(ATTR_USER_ORDERS, userOrders);
                req.setAttribute(ATTR_MENU_CHAPTER, MENU_CHAPTER_MY_ORDERS);
                result = new CommandResult(PagePath.PAGE_USER_CABINET, CommandResultType.FORWARD);
            }
            if(menuChapter != null && menuChapter.equals(MENU_CHAPTER_MY_INFO)){
                req.setAttribute(ATTR_MENU_CHAPTER, MENU_CHAPTER_MY_INFO);
                result = new CommandResult(PagePath.PAGE_USER_CABINET, CommandResultType.FORWARD);
            }

        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }

        LOG.debug("Command finished!");
        return result;
    }
}
