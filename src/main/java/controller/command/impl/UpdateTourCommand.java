package controller.command.impl;

import controller.PagePath;
import controller.command.Command;
import controller.command.CommandResult;
import controller.command.CommandResultType;
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
import java.io.IOException;
import java.util.Map;

/**
 * Update tour command
 *
 * @author Anatolii Koliaka
 */
public class UpdateTourCommand implements Command {

    private static final long serialVersionUID = 2855045189352234443L;

    private static final Logger LOG = LogManager.getLogger(UpdateTourCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_TOUR_ID = "tourId";
    private static final String PARAM_DISCOUNT_RATE = "discountRate";
    private static final String PARAM_MAX_DISCOUNT = "maxDiscount";
    private static final String PARAM_IS_HOT = "isHot";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {

        LOG.debug("Command started!");

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        String tourIdString = req.getParameter(PARAM_TOUR_ID);
        String discRateString = req.getParameter(PARAM_DISCOUNT_RATE);
        String maxDiscString = req.getParameter(PARAM_MAX_DISCOUNT);
        String isHotString = req.getParameter(PARAM_IS_HOT);

        try {
            TourService tourService = ServiceFactory.getInstance().getTourService();
            boolean isUpdated = tourService.updateDiscountAndHot(tourIdString, discRateString, maxDiscString, isHotString);
            if(isUpdated){
                LOG.trace("Tour with tourId=" + tourIdString + "successfully updated!");
                result = new CommandResult(PagePath.PAGE_ADMIN_TOUR_EDITOR, CommandResultType.REDIRECT);
            }
        } catch (ServiceException | NumberFormatException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finish!");

        return result;
    }
}
