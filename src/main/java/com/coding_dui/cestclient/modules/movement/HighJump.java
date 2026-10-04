package com.coding_dui.cestclient.modules.movement;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HighJump extends Module {
    private static final double JUMP_STRENGTH = 0.62D;

    private double previousStrength = 0.42D;

    public HighJump() {
        super("HighJump", "Makes you jump much higher", Category.MOVEMENT);
    }

    private static AttributeInstance jumpAttribute(Minecraft mc) {
        if (mc.player == null) {
            return null;
        }
        return mc.player.getAttribute(Attributes.JUMP_STRENGTH);
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getInstance();
        AttributeInstance attribute = jumpAttribute(mc);
        if (attribute == null) {
            return;
        }
        previousStrength = attribute.getBaseValue();
        attribute.setBaseValue(JUMP_STRENGTH);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        AttributeInstance attribute = jumpAttribute(mc);
        if (attribute != null && attribute.getBaseValue() != JUMP_STRENGTH) {
            attribute.setBaseValue(JUMP_STRENGTH);
        }
    }

    @Override
    public void onDisable() {
        Minecraft mc = Minecraft.getInstance();
        AttributeInstance attribute = jumpAttribute(mc);
        if (attribute != null) {
            attribute.setBaseValue(previousStrength);
        }
    }
}
