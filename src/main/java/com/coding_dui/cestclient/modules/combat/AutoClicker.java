package com.coding_dui.cestclient.modules.combat;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;

public class AutoClicker extends Module {
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
        Entity target = mc.crosshairPickEntity;
        if (target != null && target.isAlive()) {
            mc.gameMode.attack(mc.player, target);
            mc.player.swing(InteractionHand.MAIN_HAND);
        }
    }
}
