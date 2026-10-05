package com.coding_dui.cestclient.modules.combat;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;

public class AutoClicker extends Module {
    /** Attack once the vanilla attack cooldown is nearly full, so hits deal real damage. */
    private static final float FULL_CHARGE = 0.9F;

    public AutoClicker() {
        super("AutoClicker", "Attacks the entity under your crosshair", Category.COMBAT);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null) {
            return;
        }
        if (!mc.options.keyAttack.isDown()) {
            return;
        }
        // Attacking before the cooldown is ready deals heavily reduced damage, so wait for it.
        if (mc.player.getAttackStrengthScale(0.0F) < FULL_CHARGE) {
            return;
        }
        Entity target = mc.crosshairPickEntity;
        if (target != null && target.isAlive()) {
            mc.gameMode.attack(mc.player, target);
            mc.player.swing(InteractionHand.MAIN_HAND);
        }
    }
}
