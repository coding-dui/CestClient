package com.coding_dui.cestclient;

import com.coding_dui.cestclient.command.CommandManager;
import com.coding_dui.cestclient.gui.ClickGuiScreen;
import com.coding_dui.cestclient.module.Module;
import com.coding_dui.cestclient.module.ModuleManager;
import com.coding_dui.cestclient.util.ChatUtils;
import com.coding_dui.cestclient.util.InputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CestClient implements ClientModInitializer {
    public static final String MOD_ID = "cestclient";
    public static final String PREFIX = "@";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static CestClient INSTANCE;

    /** Default keys (GLFW codes) used by the client. */
    public static final int GUI_KEY = GLFW.GLFW_KEY_RIGHT_SHIFT;
    public static final int PANIC_KEY = GLFW.GLFW_KEY_DELETE;

    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private InputHandler inputHandler;
    private boolean debug;
    private Screen pendingScreen;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        LOGGER.info("Initializing CestClient...");

        inputHandler = new InputHandler();
        moduleManager = new ModuleManager();
        moduleManager.init();
        commandManager = new CommandManager();
        commandManager.init();

        LOGGER.info("CestClient loaded with {} module(s) and {} command(s).",
                moduleManager.getModules().size(), commandManager.getCommands().size());
    }

    /** Called from the local player tick mixin. */
    public void onClientTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) {
            return;
        }
        if (pendingScreen != null) {
            mc.gui.setScreen(pendingScreen);
            pendingScreen = null;
        }
        if (mc.player == null) {
            return;
        }
        moduleManager.onTick();
        if (mc.gui.screen() == null) {
            handleKeybinds(mc);
        }
    }

    private void handleKeybinds(Minecraft mc) {
        if (inputHandler.consumePress(GUI_KEY)) {
            mc.gui.setScreen(new ClickGuiScreen());
            return;
        }
        if (inputHandler.consumePress(PANIC_KEY)) {
            int disabled = panic();
            if (disabled > 0) {
                ChatUtils.success("Panic! Disabled " + disabled + " module(s).");
            }
            return;
        }
        for (Module module : moduleManager.getModules()) {
            int key = module.getKeybind();
            if (key != Module.NO_KEY && inputHandler.consumePress(key)) {
                module.toggle();
                ChatUtils.info(module.getName() + (module.isEnabled() ? " enabled." : " disabled."));
            }
        }
    }

    /**
     * Queues the ClickGUI to open on the next tick. Opening it immediately would be
     * overwritten by the chat screen closing itself right after a command runs.
     */
    public void openClickGui() {
        pendingScreen = new ClickGuiScreen();
    }

    /** Disables every enabled module. Returns how many were disabled. */
    public int panic() {
        int disabled = moduleManager.disableAll();
        LOGGER.info("Panic activated, disabled {} module(s).", disabled);
        return disabled;
    }

    /** Toggles debug mode. Returns the new state. */
    public boolean toggleDebug() {
        debug = !debug;
        LOGGER.info("Debug mode {}.", debug ? "enabled" : "disabled");
        return debug;
    }

    /** Draws the watermark, the enabled-module list and (when enabled) debug info. */
    public void renderHud(GuiGraphicsExtractor context) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null || mc.player == null) {
            return;
        }
        Font font = mc.font;
        int y = 4;

        context.text(font, "CestClient", 4, y, 0xFF00E0FF);
        y += 11;

        List<Module> enabled = new ArrayList<>(moduleManager.getEnabledModules());
        enabled.sort(Comparator.comparing(Module::getName));
        for (Module module : enabled) {
            context.text(font, module.getName(), 4, y, 0xFF55FF55);
            y += 10;
        }

        // Let modules append their own HUD lines (coordinates, FPS, ...).
        for (Module module : enabled) {
            y = module.onRenderHud(context, font, y);
        }

        if (debug) {
            y += 2;
            context.text(font, "Debug mode", 4, y, 0xFFFFFF55);
            y += 10;
            context.text(font, "FPS: " + mc.getFps(), 4, y, 0xFFBBBBBB);
            y += 10;
            context.text(font,
                    String.format("XYZ: %.1f / %.1f / %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ()),
                    4, y, 0xFFBBBBBB);
            y += 10;
            context.text(font, "Module count: " + moduleManager.getModules().size(), 4, y, 0xFFBBBBBB);
        }
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public boolean isDebug() {
        return debug;
    }
}
