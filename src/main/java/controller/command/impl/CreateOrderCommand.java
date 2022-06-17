package controller.command.impl;

import controller.command.Command;
import controller.command.CommandResult;
import exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateOrderCommand implements Command {

    private static final long serialVersionUID = 369961646472770051L;

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        return null;
    }
}
