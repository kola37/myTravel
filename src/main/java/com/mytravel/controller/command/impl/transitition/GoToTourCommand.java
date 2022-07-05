package com.mytravel.controller.command.impl.transitition;

import com.mytravel.controller.PagePath;
import com.mytravel.controller.command.Command;
import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.entity.Tour;
import com.mytravel.exception.CommandException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.ServiceFactory;
import com.mytravel.service.TourService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Go to Tour page command
 *
 * @author Anatolii Koliaka
 */
public class GoToTourCommand implements Command {

    private static final long serialVersionUID = -2288921599821895216L;

    private static final Logger LOG = LogManager.getLogger(GoToTourCommand.class);

    private static final String ATTR_TOUR_ID = "tourId";
    private static final String ATTR_TOUR = "tour";
    private static final String ATTR_ERROR_MESSAGE = "errorMessage";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try {
            TourService tourService = ServiceFactory.getInstance().getTourService();
            int tourId = Integer.parseInt(req.getParameter(ATTR_TOUR_ID));

            Optional<Tour> tourOptional = tourService.retrieveTourById(tourId);
            if (tourOptional.isEmpty()) {
                throw new CommandException("Cannot find tour by requested parameter!");
            }
            req.setAttribute(ATTR_TOUR, tourOptional.get());
            result = new CommandResult(PagePath.PAGE_TOUR, CommandResultType.FORWARD);
        } catch (ServiceException | NumberFormatException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}
