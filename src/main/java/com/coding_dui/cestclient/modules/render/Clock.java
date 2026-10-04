package com.coding_dui.cestclient.modules.render;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Clock extends Module {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Clock() {
        super("Clock", "Shows the current real time on the HUD", Category.RENDER);
    }

    @Override
    public int onRenderHud(GuiGraphicsExtractor context, Font font, int y) {
        context.text(font, "Time: " + LocalTime.now().format(FORMAT), 4, y, 0xFFAAAAAA);
        return y + 10;
    }
}
