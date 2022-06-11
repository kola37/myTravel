package controller.command.impl.transitition;

import controller.PagePath;
import controller.command.Command;
import controller.command.CommandResult;
import controller.command.CommandResultType;
import exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Go to Login page command
 *
 * @author Anatolii Koliaka
 */
public class GoToLoginCommand implements Command {

    private static final long serialVersionUID = -3756868751755278668L;

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        return new CommandResult(PagePath.PAGE_LOGIN, CommandResultType.FORWARD);
    }
}
