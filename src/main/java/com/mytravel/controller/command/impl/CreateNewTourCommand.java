package com.mytravel.controller.command.impl;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.entity.constant.UserRole;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateNewTourCommand implements Command {

    private static final long serialVersionUID = 7362818175803500078L;

    private static final Logger LOG = LogManager.getLogger(CreateNewTourCommand.class);

    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_IMAGE_PATH = "imagePath";
    private static final String PARAMETER_PRICE = "price";
    private static final String PARAMETER_TOUR_TYPE = "tourType";
    private static final String PARAMETER_NUM_OF_PERSONS = "person";
    private static final String PARAMETER_HOTEL_ID = "hotelId";
    private static final String PARAMETER_DISCOUNT_RATE = "discountRate";
    private static final String PARAMETER_MAX_DISCOUNT = "maxDiscount";
    private static final String PARAMETER_IS_HOT_TOUR = "isHot";
    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER_ROLE = "userRole";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();

        if (session.getAttribute(ATTR_USER_ROLE) == null
                || !session.getAttribute(ATTR_USER_ROLE).equals(UserRole.USER.getName())) {
            throw new CommandException("You have no access to create or update a tour!");
        }

        String name = req.getParameter(PARAMETER_NAME);
        String description = req.getParameter(PARAMETER_DESCRIPTION);
        String imagePath = req.getParameter(PARAMETER_IMAGE_PATH);
        String priceString = req.getParameter(PARAMETER_PRICE);
        String tourTypeString = req.getParameter(PARAMETER_TOUR_TYPE);
        String personString = req.getParameter(PARAMETER_NUM_OF_PERSONS);
        String hotelIdString = req.getParameter(PARAMETER_HOTEL_ID);
        String discountRateString = req.getParameter(PARAMETER_DISCOUNT_RATE);
        String maxDiscountString = req.getParameter(PARAMETER_MAX_DISCOUNT);
        String isHot = req.getParameter(PARAMETER_IS_HOT_TOUR);

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try {
            TourService tourService = ServiceFactory.getInstance().getTourService();
            tourService.createNewTour(name, description, imagePath, priceString, tourTypeString, personString, hotelIdString, discountRateString,maxDiscountString, isHot);

            LOG.trace("New tour successfully created!");

            result = new CommandResult(PagePath.PAGE_ADMIN_TOUR_EDITOR, CommandResultType.REDIRECT);

        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}

