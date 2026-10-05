package com.coding_dui.cestclient.util;

import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;

/**
 * Projects world-space points into the HUD's pixel space.
 *
 * <p>Minecraft 26.2 renders the world and the HUD from separate render states, so
 * there is no shared world-to-screen helper. The camera's view-rotation-projection
 * matrix is rotation-only, so the point is first translated relative to the camera
 * before being projected into normalised device coordinates and mapped onto the
 * GUI-scaled screen.
 */
public final class Projector {
    private Projector() {}

    /**
     * Projects a world position onto the screen.
     *
     * @return {@code (x, y)} in HUD pixels, or {@code null} when the point is
     *         behind the camera and therefore not visible.
     */
    public static Vector4f toScreen(Camera camera, double x, double y, double z,
                                    int guiWidth, int guiHeight) {
        Matrix4f matrix = camera.getViewRotationProjectionMatrix(new Matrix4f());
        Vec3 cam = camera.position();

        Vector4f point = new Vector4f(
                (float) (x - cam.x),
                (float) (y - cam.y),
                (float) (z - cam.z),
                1.0f);
        matrix.transform(point);

        if (point.w <= 0.0f) {
            return null;
        }

        float ndcX = point.x / point.w;
        float ndcY = point.y / point.w;
        return new Vector4f(
                (ndcX * 0.5f + 0.5f) * guiWidth,
                (1.0f - (ndcY * 0.5f + 0.5f)) * guiHeight,
                0.0f,
                0.0f);
    }
}
