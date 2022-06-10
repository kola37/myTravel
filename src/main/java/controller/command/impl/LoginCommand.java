package controller.command.impl;

import controller.command.Command;
import exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {

    private static final long serialVersionUID = -3240440488341003325L;

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);

    private static final String ERROR_PAGE = "WEB-INF/views/error.jsp";
    private static final String LOGIN_PAGE = "WEB-INF/views/login.jsp";
    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_ROLE = "role";


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws CommandException {

        LOG.debug("Command started!");

        HttpSession session = req.getSession();


        return null;
    }
}
