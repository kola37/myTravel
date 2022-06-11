package controller.command.impl;

import controller.command.Command;
import controller.PagePath;
import controller.command.CommandResult;
import controller.command.CommandResultType;
import exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Default command
 *
 * @author Anatolii Koliaka
 */
public class DefaultCommand implements Command {

    private static final long serialVersionUID = 8240422416512843343L;

    private static final Logger LOG = LogManager.getLogger(LoginCommand.class);


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        String errorMessage = "No such command found!";
        req.setAttribute("errorMessage", errorMessage);
        LOG.error(errorMessage);

        LOG.debug("Command finished!");
        return new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);
    }
}
