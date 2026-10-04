package com.coding_dui.cestclient.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * Sends messages to the local chat HUD only. Nothing here touches the network,
 * so feedback is never visible to other players.
 */
public final class ChatUtils {
    private static final String PREFIX = ChatFormatting.AQUA + "[CestClient] " + ChatFormatting.RESET;

    private ChatUtils() {}

    public static void send(String message) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.gui == null || mc.gui.hud == null) {
            return;
        }
        mc.gui.hud.getChat().addClientSystemMessage(Component.literal(PREFIX + message));
    }

    public static void info(String message) {
        send(ChatFormatting.GRAY + message);
    }

    public static void success(String message) {
        send(ChatFormatting.GREEN + message);
    }

    public static void error(String message) {
        send(ChatFormatting.RED + message);
    }

    public static void warn(String message) {
        send(ChatFormatting.YELLOW + message);
    }
}
