package com.coding_dui.cestclient.mixin;

import com.coding_dui.cestclient.CestClient;
import com.coding_dui.cestclient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityKnockbackMixin {
    @Inject(
            method = "knockback(DDDLnet/minecraft/world/damagesource/DamageSource;FZ)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cestclient$cancelKnockback(double strength, double x, double z, DamageSource source,
                                            float knockbackResistance, boolean flag, CallbackInfo ci) {
        CestClient client = CestClient.INSTANCE;
        if (client == null || client.getModuleManager() == null) {
            return;
        }
        Module velocity = client.getModuleManager().getModule("Velocity");
        if (velocity != null && velocity.isEnabled() && (Object) this == Minecraft.getInstance().player) {
            ci.cancel();
        }
    }
}
