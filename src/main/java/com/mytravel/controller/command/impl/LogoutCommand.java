package com.mytravel.controller.command.impl;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.exception.CommandException;
import com.mytravel.controller.PagePath;
import com.mytravel.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Logout command
 *
 * @author Anatolii Koliaka
 */
public class LogoutCommand implements Command {

    private static final long serialVersionUID = -5381842695857500194L;

    private static final Logger LOG = LogManager.getLogger(LogoutCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command starts");

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LOG.debug("Command finished");
        return new CommandResult(PagePath.PAGE_LOGOUT_SUCCESS, CommandResultType.REDIRECT);
    }
}
