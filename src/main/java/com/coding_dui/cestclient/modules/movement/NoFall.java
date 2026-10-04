package com.coding_dui.cestclient.modules.movement;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;

/**
 * Negates fall damage by clearing the accumulated fall distance while falling.
 * This is the classic client-side "NoFall" used to avoid both fall damage and
 * the landing packets anti-cheats look for.
 */
public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "Prevents fall damage", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.player.getAbilities().flying || mc.player.onGround()) {
            return;
        }
        if (mc.player.getDeltaMovement().y < 0.0D && mc.player.fallDistance > 2.0D) {
            mc.player.fallDistance = 0.0F;
            mc.player.resetFallDistance();
        }
    }
}
