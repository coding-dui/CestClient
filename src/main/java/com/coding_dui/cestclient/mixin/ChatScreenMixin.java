package com.coding_dui.cestclient.mixin;

import com.coding_dui.cestclient.CestClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Catches messages sent from the chat box. When the message is a CestClient
 * command, it is executed locally and cancelled, so it is never sent to the
 * server and other players never see it.
 */
@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    @Inject(method = "sendMessage(Ljava/lang/String;Z)V", at = @At("HEAD"), cancellable = true)
    private void cestclient$handleCommand(String chatText, boolean addToHistory, CallbackInfo ci) {
        if (CestClient.INSTANCE == null || CestClient.INSTANCE.getCommandManager() == null) {
            return;
        }
        if (CestClient.INSTANCE.getCommandManager().handle(chatText)) {
            ci.cancel();
        }
    }
}
