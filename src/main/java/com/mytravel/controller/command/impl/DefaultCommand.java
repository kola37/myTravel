package com.mytravel.controller.command.impl;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.controller.command.Command;
import com.mytravel.controller.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Default command
 *
 * @author Anatolii Koliaka
 */
public class DefaultCommand implements Command {

    private static final long serialVersionUID = 8240422416512843343L;

    private static final Logger LOG = LogManager.getLogger(DefaultCommand.class);


    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp){
        LOG.debug("Command started!");

        String errorMessage = "No such command found!";
        req.setAttribute("errorMessage", errorMessage);
        LOG.error(errorMessage);

        LOG.debug("Command finished!");
        return new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);
    }
}
