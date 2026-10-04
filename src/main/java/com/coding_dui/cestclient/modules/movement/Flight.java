package com.coding_dui.cestclient.modules.movement;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;

public class Flight extends Module {
    public Flight() {
        super("Flight", "Allows you to fly in survival", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        mc.player.getAbilities().mayfly = true;
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        mc.player.getAbilities().mayfly = true;
    }

    @Override
    public void onDisable() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }
        if (!mc.player.getAbilities().instabuild) {
            mc.player.getAbilities().mayfly = false;
            mc.player.getAbilities().flying = false;
        }
    }
}
