package com.coding_dui.cestclient.modules.misc;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.phys.Vec3;

/**
 * "Legit mode" coordinator. It does not magically defeat a server-side
 * anti-cheat; it only keeps client values within ranges a vanilla client can
 * produce, so movement modules look less obvious to server-side checks.
 *
 * <p>Because it runs after the other modules in the tick loop, it overrides
 * more aggressive values set by modules such as {@code Speed} and {@code Reach}.
 * Everything it changes is restored when the module is disabled.
 */
public class Bypass extends Module {
    private static final float LEGIT_WALK_SPEED = 0.13F;
    private static final float DEFAULT_WALK_SPEED = 0.1F;
    private static final double LEGIT_BLOCK_RANGE = 4.5D;
    private static final double LEGIT_ENTITY_RANGE = 3.0D;
    /** Sprint-jumping peaks around 0.3 blocks/tick; anything above this is a boost. */
    private static final double MAX_HORIZONTAL_SPEED = 0.35D;
    /** A vanilla jump reaches 0.42 blocks/tick, so this leaves one tick of headroom. */
    private static final double MAX_UPWARD_SPEED = 0.45D;

    private float previousWalkSpeed = DEFAULT_WALK_SPEED;
    private boolean capturedWalkSpeed;

    public Bypass() {
        super("Bypass", "Legit mode: clamps suspicious values to reduce detection", Category.MISC);
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        previousWalkSpeed = mc.player.getAbilities().getWalkingSpeed();
        capturedWalkSpeed = true;
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        Abilities abilities = mc.player.getAbilities();

        // Cap a boosted walking speed to something that still looks plausible.
        if (!abilities.flying && abilities.getWalkingSpeed() > LEGIT_WALK_SPEED) {
            if (!capturedWalkSpeed) {
                previousWalkSpeed = abilities.getWalkingSpeed();
                capturedWalkSpeed = true;
            }
            abilities.setWalkingSpeed(LEGIT_WALK_SPEED);
        }

        // Neutralise extended reach set by the Reach module.
        clampRange(mc, true, LEGIT_BLOCK_RANGE);
        clampRange(mc, false, LEGIT_ENTITY_RANGE);

        // Cap blatant speed/high-jump velocity while keeping normal movement intact.
        Vec3 motion = mc.player.getDeltaMovement();
        double horizontal = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
        if (horizontal > MAX_HORIZONTAL_SPEED) {
            double scale = MAX_HORIZONTAL_SPEED / horizontal;
            motion = new Vec3(motion.x * scale, motion.y, motion.z * scale);
        }
        if (motion.y > MAX_UPWARD_SPEED) {
            motion = new Vec3(motion.x, MAX_UPWARD_SPEED, motion.z);
        }
        mc.player.setDeltaMovement(motion);

        // Do not carry an accumulated fall distance while moving downward.
        if (!abilities.flying && !mc.player.onGround() && motion.y < 0.0D) {
            mc.player.fallDistance = 0.0F;
        }
    }

    private static void clampRange(Minecraft mc, boolean block, double value) {
        if (mc.player == null) {
            return;
        }
        AttributeInstance instance = mc.player.getAttribute(
                block ? Attributes.BLOCK_INTERACTION_RANGE : Attributes.ENTITY_INTERACTION_RANGE);
        if (instance != null && instance.getBaseValue() > value) {
            instance.setBaseValue(value);
        }
    }

    @Override
    public void onDisable() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && capturedWalkSpeed && !mc.player.getAbilities().flying) {
            mc.player.getAbilities().setWalkingSpeed(previousWalkSpeed);
        }
        capturedWalkSpeed = false;
    }
}
