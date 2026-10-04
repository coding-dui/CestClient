package com.coding_dui.cestclient.mixin;

import com.coding_dui.cestclient.CestClient;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow
    private GuiRenderState guiRenderState;

    @Inject(
            method = "extractRenderState(Lnet/minecraft/client/DeltaTracker;ZZ)V",
            at = @At("TAIL")
    )
    private void cestclient$renderHud(DeltaTracker deltaTracker, boolean renderedLevel, boolean renderedScreen,
                                      CallbackInfo ci) {
        if (CestClient.INSTANCE == null) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null || this.guiRenderState == null) {
            return;
        }
        GuiGraphicsExtractor extractor = new GuiGraphicsExtractor(mc, this.guiRenderState,
                mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
        CestClient.INSTANCE.renderHud(extractor);
    }
}
