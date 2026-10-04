package com.coding_dui.cestclient.modules.movement;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;

public class BunnyHop extends Module {
    public BunnyHop() {
        super("BunnyHop", "Automatically jumps while you move forward", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.input == null || !mc.player.onGround()) {
            return;
        }
        if (mc.player.input.hasForwardImpulse() && !mc.player.isShiftKeyDown()) {
            mc.player.jumpFromGround();
        }
    }
}
