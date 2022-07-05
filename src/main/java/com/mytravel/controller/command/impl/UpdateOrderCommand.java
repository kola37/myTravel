package com.mytravel.controller.command.impl;

import com.mytravel.controller.command.CommandResult;
import com.mytravel.controller.command.CommandResultType;
import com.mytravel.exception.CommandException;
import com.mytravel.exception.ServiceException;
import com.mytravel.service.OrderService;
import com.mytravel.service.ServiceFactory;
import com.mytravel.controller.PagePath;
import com.mytravel.controller.command.Command;
import com.mytravel.entity.constant.OrderStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Update order command
 *
 * @author Anatolii Koliaka
 */
public class UpdateOrderCommand implements Command {

    private static final long serialVersionUID = 7992669296797938866L;

    private static final Logger LOG = LogManager.getLogger(UpdateOrderCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ORDER_ID = "orderId";
    private static final String PARAM_STATUS_ID = "statusId";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        String orderIdString = req.getParameter(PARAM_ORDER_ID);
        String statusIdString = req.getParameter(PARAM_STATUS_ID);

        try {
            int orderId = Integer.parseInt(orderIdString);
            int statusId = Integer.parseInt(statusIdString);

            OrderService orderService = ServiceFactory.getInstance().getOrderService();
            boolean isUpdated = orderService.updateOrderStatus(orderId, statusId);
            if(isUpdated){
                LOG.trace("Status for order with orderId=" + orderId + " updated to " + OrderStatus.getStatus(statusId).getName());
                result = new CommandResult(PagePath.PAGE_ADMIN_ORDER_EDITOR, CommandResultType.REDIRECT);
            }
        } catch (ServiceException | NumberFormatException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finish!");
        return result;
    }
}
