package com.coding_dui.cestclient.command.commands;

import com.coding_dui.cestclient.CestClient;
import com.coding_dui.cestclient.command.Command;
import com.coding_dui.cestclient.module.Module;
import com.coding_dui.cestclient.util.ChatUtils;

public class DisableCommand extends Command {
    public DisableCommand() {
        super("disable", "Disables a module", "@disable <module>");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            ChatUtils.error("Usage: " + getUsage());
            return;
        }
        Module module = CestClient.INSTANCE.getModuleManager().getModule(args[0]);
        if (module == null) {
            ChatUtils.error("Unknown module: " + args[0]);
            return;
        }
        if (!module.isEnabled()) {
            ChatUtils.info(module.getName() + " is already disabled.");
            return;
        }
        module.setEnabled(false);
        ChatUtils.success("Disabled " + module.getName() + ".");
    }
}
