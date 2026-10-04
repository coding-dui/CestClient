package com.coding_dui.cestclient.modules.combat;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

/**
 * Jumps just before an attack lands so the hit is a critical hit. This is the
 * classic "packet crits" behaviour; expect it to be detectable on strict servers.
 */
public class Criticals extends Module {
    public Criticals() {
        super("Criticals", "Makes your attacks critical hits", Category.COMBAT);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        if (!mc.options.keyAttack.isDown() || !mc.player.onGround()) {
            return;
        }
        Entity target = mc.crosshairPickEntity;
        if (target != null && target.isAlive()) {
            mc.player.jumpFromGround();
        }
    }
}
