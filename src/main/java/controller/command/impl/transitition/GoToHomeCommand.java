package controller.command.impl.transitition;

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
 * Go to Home page command
 *
 * @author Anatolii Koliaka
 */
public class GoToHomeCommand implements Command {

    private static final long serialVersionUID = -7294538466477178707L;

    private static final Logger LOG = LogManager.getLogger(GoToHomeCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");
        //logic to find all tours and place them on home page should be here


        LOG.debug("Command finished!");
        return new CommandResult(PagePath.PAGE_HOME, CommandResultType.FORWARD);
    }
}
