package com.coding_dui.cestclient.modules.render;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class Coordinates extends Module {
    public Coordinates() {
        super("Coordinates", "Shows your position on the HUD", Category.RENDER);
    }

    @Override
    public int onRenderHud(GuiGraphicsExtractor context, Font font, int y) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return y;
        }
        context.text(font,
                String.format("XYZ: %.1f / %.1f / %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ()),
                4, y, 0xFF55FFFF);
        return y + 10;
    }
}
