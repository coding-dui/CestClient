package com.coding_dui.cestclient.modules.player;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;

public class AutoRespawn extends Module {
    private boolean requested;

    public AutoRespawn() {
        super("AutoRespawn", "Automatically respawns you after death", Category.PLAYER);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            requested = false;
            return;
        }
        if (mc.player.isDeadOrDying()) {
            if (!requested) {
                mc.player.respawn();
                requested = true;
            }
        } else {
            requested = false;
        }
    }

    @Override
    public void onDisable() {
        requested = false;
    }
}
