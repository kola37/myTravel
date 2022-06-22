package tag;

import controller.command.impl.transitition.GoToTourCommand;
import entity.Hotel;
import entity.Order;
import entity.Tour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TourEntityTagHandler extends TagSupport {

    private static final long serialVersionUID = -2804044289445814052L;

    private static final Logger LOG = LogManager.getLogger(TourEntityTagHandler.class);

    private static final String ATTR_TOURS = "tours";
    private static final String ATTR_HOTELS = "hotels";
    private static final String ATTR_ORDERS = "orders";

    private String tourName;
    private String HotelName;
    private int person;
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
            HttpSession session = request.getSession();
            JspWriter out = pageContext.getOut();

//            List<Tour> tours = (List<Tour>) session.getAttribute(ATTR_TOURS);
//            List<Hotel> hotels = (List<Hotel>) session.getAttribute(ATTR_HOTELS);

//            tourName = findTourName(tours);
//            System.out.println(tourName);
//
//                out.write(findTourName(tours));
//                out.print(tourName);
//            tourName = "Hello";
            out.print(tourName);



        } catch (IOException e) {
            LOG.error("Unable to write to output stream!", e);
            throw new JspException("Unable to write to output stream!", e);
        }
        return SKIP_BODY;
    }

    private String findTourName(List<Tour> tours){
        return tours.stream().filter(tour -> tour.getId()== order.getTourId())
                .map(String::valueOf).collect(Collectors.joining());
    }
}
