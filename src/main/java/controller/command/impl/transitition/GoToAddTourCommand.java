package controller.command.impl.transitition;

import controller.PagePath;
import controller.command.Command;
import controller.command.CommandResult;
import controller.command.CommandResultType;
import entity.Tour;
import entity.User;
import entity.constant.UserRole;
import exception.CommandException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ServiceFactory;
import service.TourService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class GoToAddTourCommand implements Command {

    private static final long serialVersionUID = -1220558756029813738L;

    private static final Logger LOG = LogManager.getLogger(GoToAddTourCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER = "user";
    private static final String PARAMETER_TOUR_ID = "tourId";

    private static final String ATTR_TOUR = "tour";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTR_USER);

        if (user == null || user.getRoleId() == UserRole.USER.getIndex() || user.getRoleId() == UserRole.MANAGER.getIndex()) {
            throw new CommandException("Please sign up with admin role to continue!");
        }

        String tourId = req.getParameter(PARAMETER_TOUR_ID);

        if (tourId != null) {
            try {
                int id = Integer.parseInt(tourId);
                TourService tourService = ServiceFactory.getInstance().getTourService();
                Optional<Tour> optionalTour = tourService.retrieveTourById(id);
                if(optionalTour.isPresent()){
                    Tour tour = optionalTour.get();
                    req.setAttribute(ATTR_TOUR, tour);
                    return new CommandResult(PagePath.PAGE_ADD_TOUR, CommandResultType.FORWARD);
                }
            } catch (ServiceException e) {
                req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
                return new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);
            }
        }
        LOG.debug("Command finished!");
        return new CommandResult(PagePath.PAGE_ADD_TOUR, CommandResultType.FORWARD);
    }
}
