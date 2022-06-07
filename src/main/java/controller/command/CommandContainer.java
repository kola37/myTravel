package controller.command;

import controller.command.impl.DefaultCommand;
import controller.command.impl.LoginCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Class-container to hold Command objects
 *
 * @author Anatolii Koliaka
 */
public class CommandContainer {

    private static final Logger LOG = LogManager.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
//        commands.put(CommandName.HOME_COMMAND, new GoToHomeCommand());
        commands.put(CommandName.LOGIN_COMMAND, new LoginCommand());
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
