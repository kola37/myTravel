package controller.command.impl;

import controller.command.Command;
import exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultCommand implements Command {

    private static final long serialVersionUID = 8240422416512843343L;

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    private static final String ERROR_PAGE = "/WEB-INF/views/error.jsp";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command starts");

        String errorMessage = "No such command found!";
        req.setAttribute("errorMessage", errorMessage);
        LOG.error(errorMessage);

        LOG.debug("Command finished");
        return ERROR_PAGE;
    }
}
