package com.mytravel.controller.command.impl;

import com.mytravel.controller.command.CommandResultType;
import com.mytravel.exception.CommandException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.ServiceFactory;
import com.mytravel.service.TourService;
import com.mytravel.controller.PagePath;
import com.mytravel.controller.command.Command;
import com.mytravel.controller.command.CommandResult;
import com.mytravel.entity.Tour;
import com.mytravel.entity.constant.HotelType;
import com.mytravel.entity.constant.TourType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Search tour command
 *
 * @author Anatolii Koliaka
 */
public class SearchTourCommand implements Command {

    private static final long serialVersionUID = -2412040301405623811L;

    private static final Logger LOG = LogManager.getLogger(SearchTourCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_TOURS_LIST = "tours";
    private static final String PARAM_SEARCH_BY_TOUR_TYPE = "type";
    private static final String PARAM_SEARCH_BY_PRICE = "price";
    private static final String PARAM_SEARCH_BY_PERSON = "person";
    private static final String PARAM_SEARCH_BY_HOTEL_TYPE = "hotel";
    private static final String PARAM_SEARCH_BY_NAME = "name";
    private static final String ATTR_PARAM_MESSAGE = "paramMessage";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        try {
            TourService tourService = ServiceFactory.getInstance().getTourService();
            List<Tour> toursNotSorted = tourService.retrieveAll();
            List<Tour> sortedTours;

            if (req.getParameter(PARAM_SEARCH_BY_TOUR_TYPE) != null) {
                String param = req.getParameter(PARAM_SEARCH_BY_TOUR_TYPE);
                sortedTours = searchByTourType(toursNotSorted, param);
                req.setAttribute(ATTR_TOURS_LIST, sortedTours);
                req.setAttribute(ATTR_PARAM_MESSAGE, PARAM_SEARCH_BY_TOUR_TYPE + " " + param);
                return new CommandResult(PagePath.PAGE_HOME, CommandResultType.FORWARD);
            }

            if (req.getParameter(PARAM_SEARCH_BY_PRICE) != null) {
                String param = req.getParameter(PARAM_SEARCH_BY_PRICE);
                sortedTours = searchByPrice(toursNotSorted, param);
                req.setAttribute(ATTR_TOURS_LIST, sortedTours);
                req.setAttribute(ATTR_PARAM_MESSAGE, PARAM_SEARCH_BY_PRICE + " " + param);
                return new CommandResult(PagePath.PAGE_HOME, CommandResultType.FORWARD);
            }

            if (req.getParameter(PARAM_SEARCH_BY_PERSON) != null) {
                String param = req.getParameter(PARAM_SEARCH_BY_PERSON);
                sortedTours = searchByPerson(toursNotSorted, param);
                req.setAttribute(ATTR_TOURS_LIST, sortedTours);
                req.setAttribute(ATTR_PARAM_MESSAGE, PARAM_SEARCH_BY_PERSON + " " + param);
                return new CommandResult(PagePath.PAGE_HOME, CommandResultType.FORWARD);
            }

            if (req.getParameter(PARAM_SEARCH_BY_HOTEL_TYPE) != null) {
                String param = req.getParameter(PARAM_SEARCH_BY_HOTEL_TYPE);
                sortedTours = searchByHotel(toursNotSorted, param);
                req.setAttribute(ATTR_TOURS_LIST, sortedTours);
                req.setAttribute(ATTR_PARAM_MESSAGE, PARAM_SEARCH_BY_HOTEL_TYPE + " " + param);
                return new CommandResult(PagePath.PAGE_HOME, CommandResultType.FORWARD);
            }

            if (req.getParameter(PARAM_SEARCH_BY_NAME) != null) {
                String param = req.getParameter(PARAM_SEARCH_BY_NAME);
                sortedTours = searchByName(toursNotSorted, param);
                req.setAttribute(ATTR_TOURS_LIST, sortedTours);
                req.setAttribute(ATTR_PARAM_MESSAGE, PARAM_SEARCH_BY_NAME + " " + param);
                return new CommandResult(PagePath.PAGE_HOME, CommandResultType.FORWARD);
            }
        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        return new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);
    }

    private List<Tour> searchByTourType(List<Tour> tourList, String param) {
        switch (param) {
            case ("holidays"):
                return tourList.stream()
                        .filter(tour -> tour.getTourTypeId() == TourType.HOLIDAYS.getIndex())
                        .collect(Collectors.toList());
            case ("excursion"):
                return tourList.stream()
                        .filter(tour -> tour.getTourTypeId() == TourType.EXCURSION.getIndex())
                        .collect(Collectors.toList());
            case ("shopping"):
                return tourList.stream()
                        .filter(tour -> tour.getTourTypeId() == TourType.SHOPPING.getIndex())
                        .collect(Collectors.toList());
        }
        return null;
    }

    private List<Tour> searchByPrice(List<Tour> tourList, String param) {
        switch (param) {
            case ("desc"):
                return tourList.stream()
                        .sorted(Comparator.comparing(Tour::getPrice).thenComparing(Tour::isHot).reversed())
                        .collect(Collectors.toList());
            case ("asc"):
                return tourList.stream()
                        .sorted(Comparator.comparing(Tour::getPrice).reversed().thenComparing(Tour::isHot).reversed())
                        .collect(Collectors.toList());
        }
        return null;
    }

    private List<Tour> searchByPerson(List<Tour> tourList, String param) {
        switch (param) {
            case ("one"):
                return tourList.stream()
                        .filter(tour -> tour.getNumOfPersons() == 1)
                        .collect(Collectors.toList());
            case ("two"):
                return tourList.stream()
                        .filter(tour -> tour.getNumOfPersons() == 2)
                        .collect(Collectors.toList());
            case ("family"):
                return tourList.stream()
                        .filter(tour -> tour.getNumOfPersons() >= 3 && tour.getNumOfPersons() <= 5)
                        .collect(Collectors.toList());
            case ("group"):
                return tourList.stream()
                        .filter(tour -> tour.getNumOfPersons() >= 6)
                        .collect(Collectors.toList());
        }
        return null;
    }

    private List<Tour> searchByHotel(List<Tour> tourList, String param) {
        switch (param) {
            case ("apartment"):
                return tourList.stream()
                        .filter(tour -> tour.getHotelId() == HotelType.APARTMENT.getIndex())
                        .collect(Collectors.toList());
            case ("hostel"):
                return tourList.stream()
                        .filter(tour -> tour.getHotelId() == HotelType.HOSTEL.getIndex())
                        .collect(Collectors.toList());
            case ("tourist"):
                return tourList.stream()
                        .filter(tour -> tour.getHotelId() == HotelType.TOURIST_HOTEL.getIndex())
                        .collect(Collectors.toList());
            case ("comfort"):
                return tourList.stream()
                        .filter(tour -> tour.getHotelId() == HotelType.COMFORT_HOTEL.getIndex())
                        .collect(Collectors.toList());
            case ("premium"):
                return tourList.stream()
                        .filter(tour -> tour.getHotelId() == HotelType.PREMIUM_HOTEL.getIndex())
                        .collect(Collectors.toList());
            case ("boutique"):
                return tourList.stream()
                        .filter(tour -> tour.getHotelId() == HotelType.BOUTIQUE_HOTEL.getIndex())
                        .collect(Collectors.toList());
        }
        return null;
    }

    private List<Tour> searchByName(List<Tour> tourList, String param){
        return tourList.stream()
                .filter(tour -> tour.getName().toUpperCase().contains(param.toUpperCase()))
                .collect(Collectors.toList());
    }
}
