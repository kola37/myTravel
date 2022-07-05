package com.mytravel.controller.command.impl.transitition;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.entity.Hotel;
import com.mytravel.entity.Tour;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.ServiceFactory;
import com.mytravel.service.TourService;
import com.mytravel.controller.command.Command;
import com.mytravel.controller.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mytravel.service.HotelService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
            List<Tour> tours = tourService.retrieveAll();

            HotelService hotelService = ServiceFactory.getInstance().getHotelService();
            List<Hotel> hotels = hotelService.retrieveAll();

            //Filter only hotels that assigned to tours
//            List<Hotel> hotels = hotelsTemp.stream()
//                    .filter(hotel -> tours.stream().map(Tour::getHotelId)
//                            .collect(Collectors.toList())
//                            .contains(hotel.getId()))
//                    .collect(Collectors.toList());

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
