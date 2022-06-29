package controller.command.impl;

import controller.PagePath;
import controller.command.Command;
import controller.command.CommandResult;
import controller.command.CommandResultType;
import entity.constant.OrderStatus;
import exception.CommandException;
import exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
