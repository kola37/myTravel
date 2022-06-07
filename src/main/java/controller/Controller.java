package controller;


import controller.command.Command;
import controller.command.CommandContainer;
import exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller class
 *
 * @author Anatolii Koliaka
 */
public class Controller extends HttpServlet {

    private static final long serialVersionUID = -1951358628804251994L;

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private static final String ERROR_PAGE = "/WEB-INF/view/error.jsp";
    private static final String HOME_PAGE = "/WEB-INF/view/home.jsp";
    private static final String COMMAND = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOG.debug("Controller started");
        String name = req.getParameter(COMMAND);
        LOG.trace("Request parameter: command name is " + name);
        Command command = CommandContainer.getCommand(name);
        LOG.trace("The command is " + command);

        String result = ERROR_PAGE;
        try {
//            if (name == null || "".equals(name)) {
//                getServletContext().getRequestDispatcher(result).forward(req,resp);
//            }else {
                result = command.execute(req, resp);
//            }
        } catch (CommandException e) {
            req.setAttribute("errorMessage", e.getMessage());
        }
        LOG.trace("Forward to address " + result);
        LOG.debug("Controller finished");

        req.getRequestDispatcher(result).forward(req, resp);
//        getServletContext().getRequestDispatcher(result).forward(req,resp);
    }

}
