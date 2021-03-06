package com.mytravel.controller.command.impl;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.entity.User;
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
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


/**
 * New tour command adds new tour to database or update existed
 *
 * @author Anatolii Koliaka
 */

public class AdminAddNewTourCommand implements Command {

    private static final long serialVersionUID = 7451159402431449938L;

    private static final Logger LOG = LogManager.getLogger(AdminAddNewTourCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER = "user";
    private static final String PARAMETER_COMMAND_NEXT = "commandNext";
    private static final String PARAMETER_TOUR_ID = "tourId";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_FILE = "file";
    private static final String PARAMETER_PRICE = "price";
    private static final String PARAMETER_PERSON = "numOfPersons";
    private static final String PARAMETER_TYPE = "tourType";
    private static final String PARAMETER_HOTEL = "hotelName";
    private static final String PARAMETER_DISCOUNT_RATE = "discountRate";
    private static final String PARAMETER_MAX_DISCOUNT = "maxDiscount";
    private static final String PARAMETER_IS_HOT = "isHot";


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTR_USER);

        if (user == null || user.getRoleId() == UserRole.USER.getIndex() || user.getRoleId() == UserRole.MANAGER.getIndex()) {
            throw new CommandException("Please sign up with admin role to continue!");
        }

        String command = req.getParameter(PARAMETER_COMMAND_NEXT);

        String tourIdString = req.getParameter(PARAMETER_TOUR_ID);
        String name = req.getParameter(PARAMETER_NAME);
        String description = req.getParameter(PARAMETER_DESCRIPTION);
        String imagePath = "";
        String priceString = req.getParameter(PARAMETER_PRICE);
        String personString = req.getParameter(PARAMETER_PERSON);
        String tourTypeString = req.getParameter(PARAMETER_TYPE);
        String hotelIdString = req.getParameter(PARAMETER_HOTEL);
        String discountRateString = req.getParameter(PARAMETER_DISCOUNT_RATE);
        String maxDiscountString = req.getParameter(PARAMETER_MAX_DISCOUNT);
        String isHotString = req.getParameter(PARAMETER_IS_HOT);

        Part filePart = req.getPart(PARAMETER_FILE);

        String fileName =
                Paths.get(filePart.getSubmittedFileName())
                        .getFileName()
                        .toString();

        if (!fileName.isEmpty()) {
            String outputFile = req.getServletContext()
                    .getRealPath("images/")
                    .concat(fileName);

            InputStream fileContent = filePart.getInputStream();
            Files.copy(fileContent,
                    Paths.get(outputFile),
                    StandardCopyOption.REPLACE_EXISTING);

            //write to database path like "/images/ + file name"
            imagePath = "/images/".concat(fileName);
        }

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try {
            TourService tourService = ServiceFactory.getInstance().getTourService();
            boolean isSuccess;
            if (tourIdString == null || tourIdString.isEmpty()) {//create new tour
                LOG.debug("Try to create new tour!");
                isSuccess = tourService.createNewTour(name, description, imagePath, priceString, tourTypeString, personString, hotelIdString, discountRateString, maxDiscountString, isHotString);
            } else {//update existed tour
                LOG.debug("Try to update tour information!");
                isSuccess = tourService.updateTour(tourIdString, name, description, imagePath, priceString, discountRateString, maxDiscountString, tourTypeString, personString, hotelIdString, isHotString);
            }
            if (isSuccess) {
                LOG.trace("Tour created/updated successfully!");
                result = new CommandResult(PagePath.PAGE_SUCCESS + command, CommandResultType.REDIRECT);
            }

        } catch (ServiceException e) {
            LOG.debug("Unable to create/update tour", e);
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}
