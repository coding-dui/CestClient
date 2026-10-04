package com.coding_dui.cestclient.modules.render;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;

public class Fullbright extends Module {
    private double previousGamma = 1.0;

    public Fullbright() {
        super("Fullbright", "Lights up the world without night vision", Category.RENDER);
    }

    @Override
    public void onEnable() {
        Minecraft mc = Minecraft.getInstance();
        previousGamma = mc.options.gamma().get();
        mc.options.gamma().set(1.0);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.gamma().get() < 1.0) {
            mc.options.gamma().set(1.0);
        }
    }

    @Override
    public void onDisable() {
        Minecraft mc = Minecraft.getInstance();
        mc.options.gamma().set(previousGamma);
    }
}
