package com.coding_dui.cestclient.modules.movement;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Step extends Module {
    private static final double STEP_HEIGHT = 1.5D;

    private double previousHeight = 0.6D;

    public Step() {
        super("Step", "Lets you step up full blocks", Category.MOVEMENT);
    }

    private static AttributeInstance stepAttribute(Minecraft mc) {
        if (mc.player == null) {
            return null;
        }
        return mc.player.getAttribute(Attributes.STEP_HEIGHT);
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getInstance();
        AttributeInstance attribute = stepAttribute(mc);
        if (attribute == null) {
            return;
        }
        previousHeight = attribute.getBaseValue();
        attribute.setBaseValue(STEP_HEIGHT);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        AttributeInstance attribute = stepAttribute(mc);
        if (attribute != null && attribute.getBaseValue() != STEP_HEIGHT) {
            attribute.setBaseValue(STEP_HEIGHT);
        }
    }

    @Override
    public void onDisable() {
        Minecraft mc = Minecraft.getInstance();
        AttributeInstance attribute = stepAttribute(mc);
        if (attribute != null) {
            attribute.setBaseValue(previousHeight);
        }
    }
}
