package com.mytravel.controller.command.impl;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.exception.CommandException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.ServiceFactory;
import com.mytravel.service.UserService;
import com.mytravel.controller.PagePath;
import com.mytravel.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Admin update user command allows to update user isBlocked status
 *
 * @author Anatolii Koliaka
 */
public class AdminUpdateUserCommand implements Command {

    private static final long serialVersionUID = -3841847416675353902L;

    private static final Logger LOG = LogManager.getLogger(AdminUpdateUserCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_IS_BLOCKED_STATUS = "isBlocked";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        String userIdString = req.getParameter(PARAM_USER_ID);
        String isBlockedString = req.getParameter(PARAM_IS_BLOCKED_STATUS);

        try {
            UserService userService = ServiceFactory.getInstance().getUserService();
            boolean isUpdated = userService.updateUserIsBlocked(userIdString, isBlockedString);
            if(isUpdated){
                LOG.trace("User with userId=" + userIdString + " changed isBlocked status for " + isBlockedString + "!");
                result = new CommandResult(PagePath.PAGE_ADMIN_USER_EDITOR, CommandResultType.REDIRECT);
            }
        } catch (ServiceException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finish!");

        return result;
    }
}
