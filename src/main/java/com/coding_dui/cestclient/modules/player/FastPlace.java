package com.coding_dui.cestclient.modules.player;

import com.coding_dui.cestclient.mixin.MinecraftAccessor;
import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", "Removes the right-click place delay", Category.PLAYER);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.keyUse.isDown()) {
            ((MinecraftAccessor) mc).setRightClickDelay(0);
        }
    }
}
