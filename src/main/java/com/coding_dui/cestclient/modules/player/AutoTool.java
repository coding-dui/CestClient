package com.coding_dui.cestclient.modules.player;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool", "Switches to the best tool for the block you mine", Category.PLAYER);
    }

    @Override
    public void onTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || !mc.options.keyAttack.isDown()) {
            return;
        }
        if (mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = ((BlockHitResult) mc.hitResult).getBlockPos();
        BlockState state = mc.level.getBlockState(pos);
        Inventory inventory = mc.player.getInventory();

        int bestSlot = -1;
        float bestSpeed = 1.0F;
        for (int slot = 0; slot < 9; slot++) {
            ItemStack stack = inventory.getItem(slot);
            float speed = stack.getDestroySpeed(state);
            if (speed > bestSpeed) {
                bestSpeed = speed;
                bestSlot = slot;
            }
        }

        if (bestSlot != -1 && inventory.getSelectedSlot() != bestSlot) {
            inventory.setSelectedSlot(bestSlot);
        }
    }
}
