package com.coding_dui.cestclient.modules.render;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.MinecraftClient;

public class Fullbright extends Module {
    private double previousGamma = 1.0;

    public Fullbright() {
        super("Fullbright", "Lights up the world without night vision", Category.RENDER);
    }

    @Override
    public void onEnable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        previousGamma = mc.options.getGamma().getValue();
        mc.options.getGamma().setValue(1.0);
    }

    @Override
    public void onTick() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options.getGamma().getValue() < 1.0) {
            mc.options.getGamma().setValue(1.0);
        }
    }

    @Override
    public void onDisable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.options.getGamma().setValue(previousGamma);
    }
}
