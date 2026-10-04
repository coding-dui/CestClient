package com.coding_dui.cestclient.modules.player;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class Nuker extends Module {
    private static final int RADIUS = 4;

    public Nuker() {
        super("Nuker", "Breaks the blocks around you", Category.PLAYER);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.gameMode == null) {
            return;
        }

        BlockPos origin = mc.player.blockPosition();
        BlockPos from = origin.offset(-RADIUS, -RADIUS, -RADIUS);
        BlockPos to = origin.offset(RADIUS, RADIUS, RADIUS);

        for (BlockPos pos : BlockPos.betweenClosed(from, to)) {
            BlockState state = mc.level.getBlockState(pos);
            if (state.isAir()) {
                continue;
            }
            // One block per tick keeps the mining rate believable.
            mc.gameMode.destroyBlock(pos);
            return;
        }
    }
}
