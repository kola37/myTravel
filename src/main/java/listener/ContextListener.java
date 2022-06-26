package listener;

import controller.PagePath;
import controller.command.CommandName;
import entity.Hotel;
import entity.Tour;
import exception.CommandException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.HotelService;
import service.ServiceFactory;
import service.TourService;
import util.DBUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final String ATTR_TOURS_LIST = "tours";
    private static final String ATTR_HOTELS_LIST = "hotels";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        String path = ctx.getRealPath("/WEB-INF/logs/log4j2.log");
        System.setProperty("logFile", path);

        final Logger LOG = LogManager.getLogger(ContextListener.class);
        LOG.debug("path = " + path);
        try {
            Connection con = DBUtils.getInstance().getConnection();

            //try to retrieve data from DB for welcome page
            TourService tourService = ServiceFactory.getInstance().getTourService();
            List<Tour> tours = tourService.retrieveAll();

            HotelService hotelService = ServiceFactory.getInstance().getHotelService();
            List<Hotel> hotels = hotelService.retrieveAll();

            ctx.setAttribute(ATTR_TOURS_LIST, tours);
            ctx.setAttribute(ATTR_HOTELS_LIST, hotels);

            con.close();
        } catch (SQLException e) {
            LOG.error("Cannot obtain a connection from DB!", e);
            throw new IllegalStateException("Cannot obtain a connection from DB!", e);
        } catch (ServiceException e) {
            LOG.error("Cannot retrieve a data from DB!", e);
        }
    }
}
