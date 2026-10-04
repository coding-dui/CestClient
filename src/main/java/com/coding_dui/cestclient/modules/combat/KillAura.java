package com.coding_dui.cestclient.modules.combat;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * Attacks the closest living entity within range. Very obvious to any
 * server-side anti-cheat; treat it as a singleplayer / anarchy tool.
 */
public class KillAura extends Module {
    private static final double RANGE = 3.0D;
    /** Ticks between hits, so it does not attack faster than a vanilla player. */
    private static final int COOLDOWN_TICKS = 10;

    private int cooldown;

    public KillAura() {
        super("KillAura", "Attacks nearby entities automatically", Category.COMBAT);
    }

    @Override
    public void onDisable() {
        cooldown = 0;
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.gameMode == null) {
            return;
        }
        if (cooldown > 0) {
            cooldown--;
            return;
        }

        Entity target = null;
        double closest = RANGE * RANGE;
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == mc.player || !entity.isAlive() || !(entity instanceof LivingEntity)) {
                continue;
            }
            if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) {
                continue;
            }
            double distance = mc.player.distanceToSqr(entity);
            if (distance <= closest) {
                closest = distance;
                target = entity;
            }
        }

        if (target != null) {
            mc.gameMode.attack(mc.player, target);
            mc.player.swing(InteractionHand.MAIN_HAND);
            cooldown = COOLDOWN_TICKS;
        }
    }
}
