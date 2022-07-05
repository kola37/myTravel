package com.mytravel.controller.command.impl.transitition;

import com.mytravel.exception.CommandException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.TourService;
import com.mytravel.controller.PagePath;
import com.mytravel.controller.command.Command;
import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.entity.Tour;
import com.mytravel.entity.User;
import com.mytravel.entity.constant.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mytravel.service.ServiceFactory;
import com.mytravel.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GoToTourEditorCommand implements Command {

    private static final long serialVersionUID = -6976915826887198868L;

    private static final Logger LOG = LogManager.getLogger(GoToTourEditorCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_USER = "user";
    private static final String ATTR_USERS = "users";
    private static final String ATTR_TOURS = "tours";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTR_USER);

        if (user == null || user.getRoleId() == UserRole.USER.getIndex()) {
            throw new CommandException("Please sign up with admin or manager role to continue!");
        }

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            List<User> users = userService.retrieveAll();
            req.setAttribute(ATTR_USERS, users);

            TourService tourService = ServiceFactory.getInstance().getTourService();
            List<Tour> tours = tourService.retrieveAll();
            tours = tours.stream().sorted(Comparator.comparing(Tour::getId)).collect(Collectors.toList());
            req.setAttribute(ATTR_TOURS, tours);

            result = new CommandResult(PagePath.PAGE_ADMIN_TOUR_EDITOR, CommandResultType.FORWARD);
        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}
