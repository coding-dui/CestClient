package com.coding_dui.cestclient.module;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.lwjgl.glfw.GLFW;

public abstract class Module {
    /** Sentinel key code meaning "no key bound". */
    public static final int NO_KEY = GLFW.GLFW_KEY_UNKNOWN;

    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled;
    private int keybind = NO_KEY;

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
    }

    /** Flips the enabled state, firing the appropriate lifecycle hook. */
    public void toggle() {
        setEnabled(!this.enabled);
    }

    /** Sets the enabled state. Does nothing if the state is unchanged. */
    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) {
            return;
        }
        this.enabled = enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable() {}

    public void onDisable() {}

    /** Called every client tick while the module is enabled. */
    public void onTick() {}

    /**
     * Called while drawing the HUD to append this module's own lines.
     *
     * @param y the current vertical cursor
     * @return the updated vertical cursor
     */
    public int onRenderHud(GuiGraphicsExtractor context, Font font, int y) {
        return y;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }
}
