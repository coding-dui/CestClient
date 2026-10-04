package com.coding_dui.cestclient.command.commands;

import com.coding_dui.cestclient.CestClient;
import com.coding_dui.cestclient.command.Command;
import com.coding_dui.cestclient.util.ChatUtils;

public class DebugCommand extends Command {
    public DebugCommand() {
        super("debug", "Toggles the debug HUD overlay", "@debug");
    }

    @Override
    public void execute(String[] args) {
        boolean state = CestClient.INSTANCE.toggleDebug();
        ChatUtils.success("Debug mode " + (state ? "enabled" : "disabled") + ".");
    }
}
