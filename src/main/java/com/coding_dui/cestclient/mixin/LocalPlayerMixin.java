package com.coding_dui.cestclient.mixin;

import com.coding_dui.cestclient.CestClient;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(method = "tick", at = @At("RETURN"))
    private void cestclient$onTick(CallbackInfo ci) {
        if (CestClient.INSTANCE != null) {
            CestClient.INSTANCE.onClientTick();
        }
    }
}
