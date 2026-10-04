package com.coding_dui.cestclient.command;

import com.coding_dui.cestclient.CestClient;
import com.coding_dui.cestclient.command.commands.BindCommand;
import com.coding_dui.cestclient.command.commands.ClickMenuCommand;
import com.coding_dui.cestclient.command.commands.DebugCommand;
import com.coding_dui.cestclient.command.commands.DisableCommand;
import com.coding_dui.cestclient.command.commands.EnableCommand;
import com.coding_dui.cestclient.command.commands.HelpCommand;
import com.coding_dui.cestclient.command.commands.PanicCommand;
import com.coding_dui.cestclient.util.ChatUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    public void init() {
        register(new EnableCommand());
        register(new DisableCommand());
        register(new ClickMenuCommand());
        register(new PanicCommand());
        register(new DebugCommand());
        register(new HelpCommand());
        register(new BindCommand());
    }

    private void register(Command command) {
        commands.put(command.getName().toLowerCase(Locale.ROOT), command);
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }

    /** True when the message is a CestClient command (starts with the prefix). */
    public boolean isCommand(String message) {
        return message != null && message.startsWith(CestClient.PREFIX);
    }

    /**
     * Handles a chat message.
     *
     * @return true when the message was a command and must NOT be sent to the server.
     */
    public boolean handle(String message) {
        if (!isCommand(message)) {
            return false;
        }
        String body = message.substring(CestClient.PREFIX.length()).trim();
        if (body.isEmpty()) {
            return true;
        }
        String[] parts = body.split("\\s+");
        String name = parts[0].toLowerCase(Locale.ROOT);
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);
        Command command = commands.get(name);
        if (command == null) {
            ChatUtils.error("Unknown command '" + name + "'. Try " + CestClient.PREFIX + "help");
            return true;
        }
        try {
            command.execute(args);
        } catch (Exception exception) {
            ChatUtils.error("Error executing " + CestClient.PREFIX + name + ": " + exception.getMessage());
            CestClient.LOGGER.error("Command '{}' failed", name, exception);
        }
        return true;
    }
}
