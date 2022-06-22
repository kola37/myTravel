package controller.command.impl;

import controller.PagePath;
import controller.command.Command;
import controller.command.CommandResult;
import controller.command.CommandResultType;
import controller.command.impl.transitition.GoToOrderEditorCommand;
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

/**
 * Delete order command
 *
 * @author Anatolii Koliaka
 */
public class DeleteOrderCommand implements Command {

    private static final long serialVersionUID = -6619590207511024750L;

    private static final Logger LOG = LogManager.getLogger(DeleteOrderCommand.class);

    private static final String ATTR_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_ORDER_ID = "orderId";

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, CommandException {
        LOG.debug("Command started!");

        CommandResult result = new CommandResult(PagePath.PAGE_ERROR, CommandResultType.FORWARD);

        String orderIdString = req.getParameter(PARAM_ORDER_ID);

        try {
            int orderId = Integer.parseInt(orderIdString);
            OrderService orderService = ServiceFactory.getInstance().getOrderService();
            boolean isDeleted = orderService.deleteOrder(orderId);
            if(isDeleted){
                LOG.trace("Order with orderId=" + orderId + " deleted from DB!");
                result = new CommandResult(PagePath.PAGE_ADMIN_ORDER_EDITOR, CommandResultType.REDIRECT);
            }
        } catch (ServiceException | NumberFormatException e) {
            req.setAttribute(ATTR_ERROR_MESSAGE, e.getMessage());
        }
        LOG.debug("Command finished!");
        return result;
    }
}
