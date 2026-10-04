package com.coding_dui.cestclient.modules.combat;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Reach extends Module {
    private static final double RANGE = 6.0D;

    private double previousBlockRange = 4.5D;
    private double previousEntityRange = 3.0D;

    public Reach() {
        super("Reach", "Extends how far you can interact", Category.COMBAT);
    }

    private static AttributeInstance attribute(Minecraft mc, boolean block) {
        if (mc.player == null) {
            return null;
        }
        return mc.player.getAttribute(block ? Attributes.BLOCK_INTERACTION_RANGE : Attributes.ENTITY_INTERACTION_RANGE);
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getInstance();
        AttributeInstance block = attribute(mc, true);
        AttributeInstance entity = attribute(mc, false);
        if (block != null) {
            previousBlockRange = block.getBaseValue();
            block.setBaseValue(RANGE);
        }
        if (entity != null) {
            previousEntityRange = entity.getBaseValue();
            entity.setBaseValue(RANGE);
        }
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        AttributeInstance block = attribute(mc, true);
        AttributeInstance entity = attribute(mc, false);
        apply(block, RANGE);
        apply(entity, RANGE);
    }

    private static void apply(AttributeInstance instance, double value) {
        if (instance != null && instance.getBaseValue() != value) {
            instance.setBaseValue(value);
        }
    }

    @Override
    public void onDisable() {
        Minecraft mc = Minecraft.getInstance();
        AttributeInstance block = attribute(mc, true);
        AttributeInstance entity = attribute(mc, false);
        if (block != null) {
            block.setBaseValue(previousBlockRange);
        }
        if (entity != null) {
            entity.setBaseValue(previousEntityRange);
        }
    }
}
