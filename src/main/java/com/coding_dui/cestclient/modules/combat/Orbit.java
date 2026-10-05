package com.coding_dui.cestclient.modules.combat;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Orbits around the closest entity to the player
 * Highly noticed by anti-cheats; Do NOT use on public servers.
 */
public class Orbit extends Module {
    private static final double ORBIT_RANGE = 4.5D;
    private static final double ORBIT_SPEED = 0.15D;
    private static final double ORBIT_RADIUS = 2.5D;

    private double currentAngle = 0.0D;

    public Orbit() {
        super("Orbit", "Circles around the nearest target automatically", Category.COMBAT);
    }

    @Override
    public void onDisable() {
        currentAngle = 0.0D;
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            return;
        }

        // Find the closest living entity within range.
        Entity target = null;
        double closest = ORBIT_RANGE * ORBIT_RANGE;
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

        // Advance around the target and keep the angle within one full turn.
        currentAngle += ORBIT_SPEED;
        if (currentAngle > Math.PI * 2) {
            currentAngle -= Math.PI * 2;
        }

        double targetX = target.getX() + Math.cos(currentAngle) * ORBIT_RADIUS;
        double targetZ = target.getZ() + Math.sin(currentAngle) * ORBIT_RADIUS;

        Vec3 motion = new Vec3(targetX - mc.player.getX(), 0, targetZ - mc.player.getZ());
        if (motion.lengthSqr() > 1.0D) {
            motion = motion.normalize();
        }

        mc.player.setDeltaMovement(motion.x, mc.player.getDeltaMovement().y, motion.z);

        double dX = target.getX() - mc.player.getX();
        double dZ = target.getZ() - mc.player.getZ();
        float yaw = (float) (Math.atan2(dZ, dX) * 180.0D / Math.PI) - 90.0F;
        mc.player.setYRot(yaw);
    }
}
