package com.coding_dui.cestclient.modules.movement;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.MinecraftClient;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", "Keeps you sprinting while moving forward", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.player.input == null) {
            return;
        }
        if (mc.player.input.hasForwardMovement() && !mc.player.isSneaking()) {
            mc.player.setSprinting(true);
        }
    }
}
