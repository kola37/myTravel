package com.mytravel.controller.command.impl.transitition;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.entity.Order;
import com.mytravel.entity.Tour;
import com.mytravel.entity.User;
import com.mytravel.entity.constant.OrderStatus;
import com.mytravel.entity.constant.UserRole;
import com.mytravel.exception.CommandException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.OrderService;
import com.mytravel.service.ServiceFactory;
import com.mytravel.service.TourService;
import com.mytravel.controller.PagePath;
import com.mytravel.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Go to Order page command
 *
 * @author Anatolii Koliaka
 */
public class GoToOrderCommand implements Command {

    private static final long serialVersionUID = -8453815537654921260L;

    private static final Logger LOG = LogManager.getLogger(GoToOrderCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER = "user";
    private static final String ATTR_TOUR = "tour";
    private static final String ATTR_TOUR_ID = "tourId";


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

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try {
            TourService tourService = ServiceFactory.getInstance().getTourService();
            int tourId = Integer.parseInt(req.getParameter(ATTR_TOUR_ID));

            Optional<Tour> tourOptional = tourService.retrieveTourById(tourId);
            if(tourOptional.isEmpty()){
                throw new CommandException("Cannot find tour by requested parameter!");
            }

            OrderService orderService = ServiceFactory.getInstance().getOrderService();
            List<Order> userOrders = orderService.retrieveUserOrdersByUserIdAndStatus(user.getId(), OrderStatus.PAID.getIndex());

            Tour tour = tourOptional.get();
            int discount = orderService.calculateDiscountAmount(userOrders.size(),tour.getDiscountRate(), tour.getMaxDiscount());
            BigDecimal totalPrice =  orderService.calculateTotalPrice(tourOptional.get().getPrice(), discount);
            req.setAttribute("discount",discount);
            req.setAttribute("totalPrice", totalPrice);

            req.setAttribute(ATTR_USER, user);
            req.setAttribute(ATTR_TOUR, tourOptional.get());
            result = new CommandResult(PagePath.PAGE_ORDER, CommandResultType.FORWARD);

        } catch (ServiceException | NumberFormatException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}
