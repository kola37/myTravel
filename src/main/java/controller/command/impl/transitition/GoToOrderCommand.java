package controller.command.impl.transitition;

import controller.PagePath;
import controller.command.Command;
import controller.command.CommandResult;
import controller.command.CommandResultType;
import entity.Order;
import entity.Promotion;
import entity.Tour;
import entity.User;
import entity.constant.OrderStatus;
import exception.CommandException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import service.ServiceFactory;
import service.TourService;

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

    private static final Logger LOG = LogManager.getLogger(GoToHomeCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER = "user";
    private static final String ATTR_TOUR = "tour";
    private static final String ATTR_TOUR_ID = "tourId";


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();

        if (session.getAttribute(ATTR_USER) == null) {
            throw new CommandException("Please sign up to continue!");
        }

        User user = (User) req.getSession().getAttribute(ATTR_USER);
        try {
            TourService tourService = ServiceFactory.getInstance().getTourService();
            int tourId = Integer.parseInt(req.getParameter(ATTR_TOUR_ID));

            Optional<Tour> tourOptional = tourService.retrieveTourById(tourId);
            if(tourOptional.isEmpty()){
                throw new CommandException("Cannot find tour by requested parameter!");
            }

            OrderService orderService = ServiceFactory.getInstance().getOrderService();
            List<Order> userOrders = orderService.retrieveUserOrdersByUserIdAndStatus(user.getId(), OrderStatus.PAID.getIndex());
            ///////////////////временно//////////////////
            Promotion promo = new Promotion();
            promo.setDiscountRate(2);
            promo.setMaxDiscount(15);
            int discount = orderService.calculateDiscountAmount(userOrders.size(),promo);
            BigDecimal totalPrice =  orderService.calculateTotalPrice(tourOptional.get().getPrice(), discount);
            req.setAttribute("discount",discount);
            req.setAttribute("totalPrice", totalPrice);
            ///////////////////временно//////////////////

            req.setAttribute(ATTR_USER, user);
            req.setAttribute(ATTR_TOUR, tourOptional.get());

        } catch (ServiceException | NumberFormatException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }


        LOG.debug("Command finished!");
        return new CommandResult(PagePath.PAGE_ORDER, CommandResultType.FORWARD);
    }
}