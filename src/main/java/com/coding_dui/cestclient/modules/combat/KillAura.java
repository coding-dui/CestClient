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
    /** Attack once the vanilla attack cooldown is nearly full, so hits deal real damage. */
    private static final float FULL_CHARGE = 0.9F;

    public KillAura() {
        super("KillAura", "Attacks nearby entities automatically", Category.COMBAT);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.gameMode == null) {
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

        if (target == null) {
            return;
        }

        // Look at the target so the hit is aimed and the swing looks intentional.
        faceTarget(mc, target);

        // Attacking before the cooldown is ready deals heavily reduced damage, so wait for it.
        if (mc.player.getAttackStrengthScale(0.0F) < FULL_CHARGE) {
            return;
        }
        mc.gameMode.attack(mc.player, target);
        mc.player.swing(InteractionHand.MAIN_HAND);
    }

    private static void faceTarget(Minecraft mc, Entity target) {
        double dX = target.getX() - mc.player.getX();
        double dY = target.getY() + target.getEyeHeight() * 0.5D - (mc.player.getY() + mc.player.getEyeHeight());
        double dZ = target.getZ() - mc.player.getZ();
        double horizontal = Math.sqrt(dX * dX + dZ * dZ);

        float yaw = (float) (Math.atan2(dZ, dX) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(dY, horizontal) * 180.0D / Math.PI);
        mc.player.setYRot(yaw);
        mc.player.setXRot(pitch);
    }
}
