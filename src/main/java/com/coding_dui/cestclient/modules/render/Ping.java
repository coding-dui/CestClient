package com.coding_dui.cestclient.modules.render;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.PlayerInfo;

public class Ping extends Module {
    public Ping() {
        super("Ping", "Shows your server latency on the HUD", Category.RENDER);
    }

    @Override
    public int onRenderHud(GuiGraphicsExtractor context, Font font, int y) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.getConnection() == null) {
            return y;
        }
        PlayerInfo info = mc.getConnection().getPlayerInfo(mc.player.getUUID());
        int latency = info == null ? 0 : info.getLatency();
        context.text(font, "Ping: " + latency + "ms", 4, y, 0xFFFF55FF);
        return y + 10;
    }
}
