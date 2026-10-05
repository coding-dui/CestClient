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
import net.minecraft.world.phys.AABB;
import org.joml.Vector4f;

/**
 * Draws a 2D box around every nearby living entity.
 *
 * <p>26.2's extract-render model has no world-space drawing hook, so the boxes are
 * projected to screen space and drawn as part of the HUD. They track the world
 * correctly as long as the entity is in front of the camera.
 */
public class Esp extends Module {
    private static final int PLAYER_COLOR = 0xFFFF5555;
    private static final int MOB_COLOR = 0xFFFFAA00;

    public Esp() {
        super("ESP", "Draws a box around nearby entities", Category.RENDER);
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

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == mc.player || !(entity instanceof LivingEntity) || !entity.isAlive()) {
                continue;
            }

            AABB box = entity.getBoundingBox();
            float minX = Float.MAX_VALUE;
            float minY = Float.MAX_VALUE;
            float maxX = -Float.MAX_VALUE;
            float maxY = -Float.MAX_VALUE;
            boolean visible = true;

            // Project all eight corners and take the enclosing 2D rectangle.
            for (int corner = 0; corner < 8; corner++) {
                double cx = (corner & 1) == 0 ? box.minX : box.maxX;
                double cy = (corner & 2) == 0 ? box.minY : box.maxY;
                double cz = (corner & 4) == 0 ? box.minZ : box.maxZ;
                Vector4f screen = Projector.toScreen(camera, cx, cy, cz, width, height);
                if (screen == null) {
                    visible = false;
                    break;
                }
                minX = Math.min(minX, screen.x);
                minY = Math.min(minY, screen.y);
                maxX = Math.max(maxX, screen.x);
                maxY = Math.max(maxY, screen.y);
            }

            if (!visible) {
                continue;
            }
            int color = entity instanceof Player ? PLAYER_COLOR : MOB_COLOR;
            context.outline((int) minX, (int) minY, (int) maxX, (int) maxY, color);
        }
        return y;
    }
}
