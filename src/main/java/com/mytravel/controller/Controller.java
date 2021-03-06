package com.mytravel.controller;


import com.mytravel.controller.command.Command;
import com.mytravel.controller.command.CommandContainer;
import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller class with @MultipartConfig annotation to accept uploading images
 *
 * @author Anatolii Koliaka
 */
@MultipartConfig
public class Controller extends HttpServlet {

    private static final long serialVersionUID = -1951358628804251994L;

    private static final Logger LOG = LogManager.getLogger(Controller.class);

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

        LOG.debug("Controller started!");
        String name = req.getParameter(COMMAND);
        LOG.trace("Request parameter: command name is " + name);
        Command command = CommandContainer.getCommand(name);
        LOG.trace("The command is " + command);

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        try{
            result = command.execute(req,resp);
            dispatch(result, req, resp);
        } catch (CommandException e) {
            req.setAttribute("errorMessage", e.getMessage());
            dispatch(result,req,resp);
        }

        LOG.debug("Controller finished!");
    }

    private void dispatch(CommandResult result, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (result.isRedirect()) {
            resp.sendRedirect(req.getContextPath() + result.getPage());
            LOG.trace("Redirected to: " + req.getContextPath() + result.getPage());
        } else {
            req.getRequestDispatcher(result.getPage()).forward(req, resp);
            LOG.trace("Forward to: " + req.getContextPath() + result.getPage());
        }
    }

}
