package com.coding_dui.cestclient.modules.movement;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Abilities;

public class Speed extends Module {
    private static final float DEFAULT_WALK_SPEED = 0.1F;
    private static final float FAST_WALK_SPEED = 0.26F;

    private float previousSpeed = DEFAULT_WALK_SPEED;

    public Speed() {
        super("Speed", "Increases your walking speed", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        Abilities abilities = mc.player.getAbilities();
        previousSpeed = abilities.getWalkingSpeed();
        abilities.setWalkingSpeed(FAST_WALK_SPEED);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        mc.player.getAbilities().setWalkingSpeed(FAST_WALK_SPEED);
    }

    @Override
    public void onDisable() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        mc.player.getAbilities().setWalkingSpeed(previousSpeed);
    }
}
