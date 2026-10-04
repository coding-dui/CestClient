package com.coding_dui.cestclient.command.commands;

import com.coding_dui.cestclient.CestClient;
import com.coding_dui.cestclient.command.Command;
import com.coding_dui.cestclient.util.ChatUtils;

public class ClickMenuCommand extends Command {
    public ClickMenuCommand() {
        super("clickmenu", "Opens the CestClient menu", "@clickmenu");
    }

    @Override
    public void execute(String[] args) {
        CestClient.INSTANCE.openClickGui();
        ChatUtils.info("Opening the CestClient menu...");
    }
}
