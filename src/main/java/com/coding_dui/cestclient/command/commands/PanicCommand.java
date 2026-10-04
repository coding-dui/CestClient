package com.coding_dui.cestclient.command.commands;

import com.coding_dui.cestclient.CestClient;
import com.coding_dui.cestclient.command.Command;
import com.coding_dui.cestclient.util.ChatUtils;

public class PanicCommand extends Command {
    public PanicCommand() {
        super("panic", "Disables every enabled module", "@panic");
    }

    @Override
    public void execute(String[] args) {
        int disabled = CestClient.INSTANCE.panic();
        if (disabled == 0) {
            ChatUtils.info("Nothing was enabled.");
        } else {
            ChatUtils.success("Panic! Disabled " + disabled + " module(s).");
        }
    }
}
