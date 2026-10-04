package com.coding_dui.cestclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Sends messages to the local chat HUD only. Nothing here touches the network,
 * so feedback is never visible to other players.
 */
public final class ChatUtils {
    private static final String PREFIX = Formatting.AQUA + "[CestClient] " + Formatting.RESET;

    private ChatUtils() {}

    public static void send(String message) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || mc.inGameHud == null) {
            return;
        }
        mc.inGameHud.getChatHud().addMessage(Text.literal(PREFIX + message));
    }

    public static void info(String message) {
        send(Formatting.GRAY + message);
    }

    public static void success(String message) {
        send(Formatting.GREEN + message);
    }

    public static void error(String message) {
        send(Formatting.RED + message);
    }

    public static void warn(String message) {
        send(Formatting.YELLOW + message);
    }
}
