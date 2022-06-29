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
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final String ATTR_TOURS_LIST = "tours";
    private static final String ATTR_HOTELS_LIST = "hotels";
    private static final String PARAMETER_LOCALES = "locales";
    private static final String ATTR_LOCALES = "locales";

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
            obtainAllTours(ctx);
            obtainAllHotels(ctx);
            //try to obtain locales for app
            obtainLocales(ctx);

            con.close();
        } catch (SQLException e) {
            LOG.error("Cannot obtain a connection from DB!", e);
            //in case we could not obtain a connection with DB throw IllegalStateException and app will not start
            throw new IllegalStateException("Cannot obtain a connection from DB!", e);
        } catch (ServiceException e) {
            LOG.error("Cannot retrieve a data from DB!", e);
        } catch (IOException e) {
            LOG.error("Cannot obtain locales settings!", e);
        }
    }

    /**
     * Method to retrieve all tours from DB for welcome page and save result as attribute in application context
     * @param context servlet context
     * @throws ServiceException
     */
    private static void obtainAllTours(ServletContext context) throws ServiceException {
        TourService tourService = ServiceFactory.getInstance().getTourService();
        List<Tour> tours = tourService.retrieveAll();
        context.setAttribute(ATTR_TOURS_LIST, tours);
    }

    /**
     * Method to retrieve all tours from DB for welcome page and save result as attribute in application context
     * @param context servlet context
     * @throws ServiceException
     */
    private static void obtainAllHotels(ServletContext context) throws ServiceException {
        HotelService hotelService = ServiceFactory.getInstance().getHotelService();
        List<Hotel> hotels = hotelService.retrieveAll();
        context.setAttribute(ATTR_HOTELS_LIST, hotels);
    }

    /**
     * Method to obtain locales and save result as attribute in application context
     * @param context servlet context
     * @throws IOException
     */
    private static void obtainLocales(ServletContext context) throws IOException {
        // obtain file name with locales descriptions
        String localesFileName = context.getInitParameter(PARAMETER_LOCALES);
        // obtain real path on server
        String localesFileRealPath = context.getRealPath(localesFileName);
        // load descriptions
        Properties locales = new Properties();
        locales.load(new FileInputStream(localesFileRealPath));
        // save descriptions to servlet context
        context.setAttribute(ATTR_LOCALES, locales);
//        locales.list(System.out);
    }
}
