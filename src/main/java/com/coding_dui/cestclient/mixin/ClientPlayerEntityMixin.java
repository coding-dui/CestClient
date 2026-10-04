package com.coding_dui.cestclient.mixin;

import com.coding_dui.cestclient.CestClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Inject(method = "tick", at = @At("RETURN"))
    private void cestclient$onTick(CallbackInfo ci) {
        if (CestClient.INSTANCE != null) {
            CestClient.INSTANCE.onClientTick();
        }
    }
}
