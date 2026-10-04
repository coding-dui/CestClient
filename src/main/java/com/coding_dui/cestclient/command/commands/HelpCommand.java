package com.coding_dui.cestclient.command.commands;

import com.coding_dui.cestclient.CestClient;
import com.coding_dui.cestclient.command.Command;
import com.coding_dui.cestclient.util.ChatUtils;
import net.minecraft.ChatFormatting;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Lists all CestClient commands", "@help");
    }

    @Override
    public void execute(String[] args) {
        ChatUtils.info("CestClient commands:");
        for (Command command : CestClient.INSTANCE.getCommandManager().getCommands()) {
            ChatUtils.send("  " + ChatFormatting.AQUA + command.getUsage() + ChatFormatting.GRAY + " - " + command.getDescription());
        }
    }
}
