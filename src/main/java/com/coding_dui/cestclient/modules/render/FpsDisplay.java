package com.coding_dui.cestclient.modules.render;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class FpsDisplay extends Module {
    public FpsDisplay() {
        super("FPS", "Shows your frames per second on the HUD", Category.RENDER);
    }

    @Override
    public int onRenderHud(GuiGraphicsExtractor context, Font font, int y) {
        Minecraft mc = Minecraft.getInstance();
        context.text(font, "FPS: " + mc.getFps(), 4, y, 0xFFFFAA00);
        return y + 10;
    }
}
