package controller.command.impl;

import controller.PagePath;
import controller.command.Command;
import controller.command.CommandResult;
import controller.command.CommandResultType;
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

/**
 * Delete tour command
 *
 * @author Anatolii Koliaka
 */

public class DeleteTourCommand implements Command {

    private static final long serialVersionUID = -750964416481072096L;

    private static final Logger LOG = LogManager.getLogger(DeleteTourCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_TOUR_ID = "tourId";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        String tourIdString = req.getParameter(PARAM_TOUR_ID);

        try {
            int tourId = Integer.parseInt(tourIdString);
            TourService tourService = ServiceFactory.getInstance().getTourService();
            boolean isDeleted = tourService.deleteTour(tourId);
            if(isDeleted){
                LOG.trace("Tour with tourId=" + tourId + " deleted from DB!");
                result = new CommandResult(PagePath.PAGE_ADMIN_TOUR_EDITOR, CommandResultType.REDIRECT);
            }
        } catch (ServiceException | NumberFormatException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}
