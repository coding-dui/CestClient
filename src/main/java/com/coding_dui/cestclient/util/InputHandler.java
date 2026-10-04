package com.coding_dui.cestclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Tracks raw GLFW key state and reports a press only once per physical press,
 * so held keys do not repeatedly trigger a toggle.
 */
public class InputHandler {
    private final Set<Integer> heldKeys = new HashSet<>();

    /**
     * @return true on the single tick the key transitions from released to pressed.
     */
    public boolean consumePress(int keyCode) {
        if (keyCode <= 0) {
            return false;
        }
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || mc.getWindow() == null) {
            return false;
        }
        boolean down = InputUtil.isKeyPressed(mc.getWindow().getHandle(), keyCode);
        boolean wasDown = heldKeys.contains(keyCode);
        if (down && !wasDown) {
            heldKeys.add(keyCode);
            return true;
        }
        if (!down && wasDown) {
            heldKeys.remove(keyCode);
        }
        return false;
    }
}
