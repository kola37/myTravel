package com.mytravel.controller.command.impl;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.exception.CommandException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.ServiceFactory;
import com.mytravel.service.TourService;
import com.mytravel.controller.PagePath;
import com.mytravel.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Manager update tour command allows to update only discount rate, max discount and isHot parameters of tour
 *
 * @author Anatolii Koliaka
 */
public class ManagerUpdateTourCommand implements Command {

    private static final long serialVersionUID = 2855045189352234443L;

    private static final Logger LOG = LogManager.getLogger(ManagerUpdateTourCommand.class);

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
        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finish!");

        return result;
    }
}
