package controller.command;

import controller.command.impl.*;
import controller.command.impl.transitition.GoToHomeCommand;
import controller.command.impl.transitition.GoToLoginCommand;
import controller.command.impl.transitition.GoToOrderCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class-container to hold Command objects
 *
 * @author Anatolii Koliaka
 */
public class CommandContainer {

    private static final Logger LOG = LogManager.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put(CommandName.HOME_PAGE_COMMAND, new GoToHomeCommand());
        commands.put(CommandName.LOGIN_PAGE_COMMAND, new GoToLoginCommand());
        commands.put(CommandName.LOGIN_COMMAND, new LoginCommand());
        commands.put(CommandName.LOGOUT_COMMAND, new LogoutCommand());
        commands.put(CommandName.REGISTER_COMMAND, new RegisterCommand());
        commands.put(CommandName.ORDER_PAGE_COMMAND, new GoToOrderCommand());
        commands.put(CommandName.CREATE_ORDER_COMMAND, new CreateOrderCommand());
        commands.put(CommandName.DEFAULT_COMMAND, new DefaultCommand());
    }

    /**
     * Method to get specified command from command container
     *
     * @param name command's name
     * @return specified command if existed, default command otherwise
     */
    public static Command getCommand(String name) {
        if (name == null || !commands.containsKey(name)) {
            LOG.trace("No such command found by this name: " + name);
            return commands.get(CommandName.DEFAULT_COMMAND);
        }
        return commands.get(name);
    }
}
