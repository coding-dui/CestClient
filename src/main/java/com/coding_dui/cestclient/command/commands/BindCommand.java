package com.coding_dui.cestclient.command.commands;

import com.coding_dui.cestclient.CestClient;
import com.coding_dui.cestclient.command.Command;
import com.coding_dui.cestclient.module.Module;
import com.coding_dui.cestclient.util.ChatUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;

public class BindCommand extends Command {
    public BindCommand() {
        super("bind", "Binds a module to a key", "@bind <module> <key|none>");
    }

    @Override
    public void execute(String[] args) {
        // No arguments: list every bound module.
        if (args.length == 0) {
            ChatUtils.info("Active binds:");
            boolean any = false;
            for (Module module : CestClient.INSTANCE.getModuleManager().getModules()) {
                if (module.getKeybind() != Module.NO_KEY) {
                    any = true;
                    ChatUtils.send("  " + ChatFormatting.AQUA + module.getName() + ChatFormatting.GRAY + " -> "
                            + InputConstants.Type.KEYSYM.getOrCreate(module.getKeybind()).getDisplayName().getString());
                }
            }
            if (!any) {
                ChatUtils.info("No modules are bound. Use " + getUsage());
            }
            return;
        }

        Module module = CestClient.INSTANCE.getModuleManager().getModule(args[0]);
        if (module == null) {
            ChatUtils.error("Unknown module: " + args[0]);
            return;
        }
        if (args.length < 2) {
            ChatUtils.error("Usage: " + getUsage());
            return;
        }

        String keyName = args[1];
        if (keyName.equalsIgnoreCase("none") || keyName.equalsIgnoreCase("clear")) {
            module.setKeybind(Module.NO_KEY);
            ChatUtils.success("Cleared the keybind for " + module.getName() + ".");
            return;
        }

        try {
            InputConstants.Key key = parseKey(keyName);
            module.setKeybind(key.getValue());
            ChatUtils.success("Bound " + module.getName() + " to " + key.getDisplayName().getString() + ".");
        } catch (Exception exception) {
            ChatUtils.error("Unknown key: " + keyName + " (try a letter like 'r', or 'none' to clear)");
        }
    }

    /** Accepts either a full key key ("key.keyboard.r") or a bare name ("r"). */
    private static InputConstants.Key parseKey(String name) {
        String candidate = name;
        if (!candidate.contains(".")) {
            candidate = "key.keyboard." + candidate.toLowerCase();
        }
        return InputConstants.getKey(candidate);
    }
}
