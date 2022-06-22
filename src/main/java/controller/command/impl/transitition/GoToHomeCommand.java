package controller.command.impl.transitition;

import controller.command.Command;
import controller.PagePath;
import controller.command.CommandResult;
import controller.command.CommandResultType;
import entity.Hotel;
import entity.Tour;
import entity.constant.TourType;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.HotelService;
import service.ServiceFactory;
import service.TourService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

/**
 * Go to Home page command
 *
 * @author Anatolii Koliaka
 */
public class GoToHomeCommand implements Command {

    private static final long serialVersionUID = -7294538466477178707L;

    private static final Logger LOG = LogManager.getLogger(GoToHomeCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_TOURS_LIST = "tours";
    private static final String ATTR_HOTELS_LIST = "hotels";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("Command started!");

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try {
            TourService tourService = ServiceFactory.getInstance().getTourService();
            List<Tour> tours = tourService.retrieveAllTours();

            HotelService hotelService = ServiceFactory.getInstance().getHotelService();
            List<Hotel> hotelsTemp = hotelService.retrieveAllHotels();

            //Filter only hotels that assigned to tours
            List<Hotel> hotels = hotelsTemp.stream()
                    .filter(hotel -> tours.stream().map(Tour::getHotelId)
                            .collect(Collectors.toList())
                            .contains(hotel.getId()))
                    .collect(Collectors.toList());

            req.getSession().setAttribute(ATTR_TOURS_LIST, tours);
            req.getSession().setAttribute(ATTR_HOTELS_LIST, hotels);

            result = new CommandResult(PagePath.PAGE_HOME, CommandResultType.FORWARD);
        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }


        LOG.debug("Command finished!");
        return result;
    }
}
