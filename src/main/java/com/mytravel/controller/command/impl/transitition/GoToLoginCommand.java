package com.mytravel.controller.command.impl.transitition;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.controller.PagePath;
import com.mytravel.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Go to Login page command
 *
 * @author Anatolii Koliaka
 */
public class GoToLoginCommand implements Command {

    private static final long serialVersionUID = -3756868751755278668L;

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp){
        return new CommandResult(PagePath.PAGE_LOGIN, CommandResultType.FORWARD);
    }
}
