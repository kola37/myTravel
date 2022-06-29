package controller.command.impl;

import controller.PagePath;
import controller.command.Command;
import controller.command.CommandResult;
import controller.command.CommandResultType;
import entity.User;
import entity.constant.OrderStatus;
import entity.constant.UserRole;
import exception.CommandException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * Create new order command
 *
 * @author Anatolii Koliaka
 */
public class ConfirmOrderCommand implements Command {

    private static final long serialVersionUID = 369961646472770051L;

    private static final Logger LOG = LogManager.getLogger(ConfirmOrderCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER = "user";
    private static final String ATTR_TOUR_ID = "tourId";
    private static final String ATTR_DISCOUNT = "discount";
    private static final String ATTR_TOTAL_PRICE = "totalPrice";
    private static final String ATTR_MESSAGE = "message";
    private static final String MSG_ORDER_CONFIRMED = "Congratulations! Your order confirmed successfully!";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {

        LOG.debug("Command started!");

        HttpSession session = req.getSession();
        User user = (User) req.getSession().getAttribute(ATTR_USER);

        if (user == null) {
            throw new CommandException("Please sign up to continue!");
        }

        if(user.getRoleId() != UserRole.USER.getIndex()){
            throw new CommandException("Registered users only can make an order! Please, sign up with user role!");
        }

        if(user.isBlocked()){
            throw new CommandException("Unfortunately, your account has blocked and you can't make an order!");
        }

        String tourIdString = req.getParameter(ATTR_TOUR_ID);
        String discountString = req.getParameter(ATTR_DISCOUNT) != null ? req.getParameter(ATTR_DISCOUNT) : "0";
        String totalPriceString = req.getParameter(ATTR_TOTAL_PRICE);

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try {
            String status = OrderStatus.REGISTERED.getName();
            Date orderDate = new Date();

            OrderService orderService = ServiceFactory.getInstance().getOrderService();
            boolean isConfirmed = orderService.addNewOrder(user.getId(),tourIdString, status, orderDate, discountString, totalPriceString);
            if(isConfirmed){
                session.setAttribute(ATTR_MESSAGE,MSG_ORDER_CONFIRMED);
                LOG.trace("Order successfully confirmed!");
                result = new CommandResult(PagePath.PAGE_ORDER_CONFIRMED, CommandResultType.REDIRECT);
            }

        } catch (ServiceException  e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}
