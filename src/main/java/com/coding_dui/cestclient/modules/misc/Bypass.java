package com.coding_dui.cestclient.modules.misc;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Abilities;

/**
 * "Legit mode" coordinator. It does not magically defeat a server-side
 * anti-cheat; it only keeps client values within ranges a vanilla client can
 * produce, so movement modules look less obvious to server-side checks.
 *
 * <p>Because it runs after the other modules in the tick loop, it overrides
 * more aggressive values set by modules such as {@code Speed}.
 */
public class Bypass extends Module {
    private static final float LEGIT_WALK_SPEED = 0.13F;
    private static final float DEFAULT_WALK_SPEED = 0.1F;

    public Bypass() {
        super("Bypass", "Legit mode: clamps suspicious values to reduce detection", Category.MISC);
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
            abilities.setWalkingSpeed(LEGIT_WALK_SPEED);
        }

        // Do not carry an accumulated fall distance while moving downward.
        if (!abilities.flying && !mc.player.onGround() && mc.player.getDeltaMovement().y < 0.0D) {
            mc.player.fallDistance = 0.0F;
        }
    }

    @Override
    public void onDisable() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        Abilities abilities = mc.player.getAbilities();
        if (!abilities.flying && abilities.getWalkingSpeed() > LEGIT_WALK_SPEED) {
            abilities.setWalkingSpeed(DEFAULT_WALK_SPEED);
        }
    }
}
