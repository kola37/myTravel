package controller.command;

import exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public interface Command extends Serializable {

    /**
     * Method executed by controller when specified command is called
     *
     * @param req  incoming request
     * @param resp response for incoming request
     * @return CommandResult of page with routing type
     */
    CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException;
    ///////!!!!!!!! Check for need of IOException, ServletException !!!!!!!/////////////
}
