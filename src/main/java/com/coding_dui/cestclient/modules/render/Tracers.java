package com.coding_dui.cestclient.modules.render;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import com.coding_dui.cestclient.util.Projector;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

/**
 * Draws a line from the bottom-centre of the screen to every nearby living entity.
 *
 * <p>Like {@link Esp}, this is a screen-space projection drawn on the HUD because
 * 26.2 does not expose a world-space drawing hook.
 */
public class Tracers extends Module {
    private static final int PLAYER_COLOR = 0xFFFF5555;
    private static final int MOB_COLOR = 0xFFFFAA00;

    public Tracers() {
        super("Tracers", "Draws lines from the crosshair to nearby entities", Category.RENDER);
    }

    @Override
    public int onRenderHud(GuiGraphicsExtractor context, Font font, int y) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            return y;
        }
        Camera camera = mc.gameRenderer.mainCamera();
        if (camera == null) {
            return y;
        }
        int width = context.guiWidth();
        int height = context.guiHeight();
        int originX = width / 2;
        int originY = height - 1;

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == mc.player || !(entity instanceof LivingEntity) || !entity.isAlive()) {
                continue;
            }
            Vec3 centre = entity.getPosition(1.0F).add(0.0D, entity.getEyeHeight() * 0.5D, 0.0D);
            Vector4f screen = Projector.toScreen(camera, centre.x, centre.y, centre.z, width, height);
            if (screen == null) {
                continue;
            }
            int color = entity instanceof Player ? PLAYER_COLOR : MOB_COLOR;
            drawLine(context, originX, originY, (int) screen.x, (int) screen.y, color);
        }
        return y;
    }

    /** Rasterises a 1px line using the HUD's rectangle fill. */
    private static void drawLine(GuiGraphicsExtractor context, int x1, int y1, int x2, int y2, int color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int steps = Math.max(dx, dy);
        if (steps > 2000) {
            return;
        }
        for (int i = 0; i <= steps; i++) {
            float t = steps == 0 ? 0.0F : (float) i / steps;
            int x = Math.round(x1 + (x2 - x1) * t);
            int yy = Math.round(y1 + (y2 - y1) * t);
            context.fill(x, yy, x + 1, yy + 1, color);
        }
    }
}
