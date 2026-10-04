package com.coding_dui.cestclient.modules.movement;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", "Keeps you sprinting while moving forward", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.input == null) {
            return;
        }
        if (mc.player.input.hasForwardImpulse() && !mc.player.isShiftKeyDown()) {
            mc.player.setSprinting(true);
        }
    }
}
