package com.coding_dui.cestclient.gui;

import com.coding_dui.cestclient.CestClient;
import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import com.coding_dui.cestclient.util.ChatUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends Screen {
    private final List<CategoryPanel> panels = new ArrayList<>();

    private CategoryPanel draggingPanel;
    private int dragOffsetX;
    private int dragOffsetY;
    private Module bindingModule;

    public ClickGuiScreen() {
        super(Component.literal("CestClient"));
    }

    @Override
    protected void init() {
        super.init();
        if (panels.isEmpty()) {
            int startX = 20;
            for (Category category : Category.values()) {
                List<Module> modules = CestClient.INSTANCE.getModuleManager().getModulesByCategory(category);
                if (modules.isEmpty()) {
                    continue;
                }
                panels.add(new CategoryPanel(category, startX, 30, modules));
                startX += CategoryPanel.WIDTH + 12;
            }
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        // The screen framework already drew the dimmed/blurred background in its own stratum
        // before calling this method. Drawing it again here would put the full-screen menu
        // background into our layer and hide the panels, so only our own content is drawn.
        context.text(this.font, "CestClient", 6, 6, 0xFF00E0FF);
        context.text(this.font,
                "Left-click: toggle   Right-click: bind key   Drag headers   ESC: close",
                6, this.height - 12, 0xFFAAAAAA);

        for (CategoryPanel panel : panels) {
            panel.extract(context, this.font, mouseX, mouseY, bindingModule);
        }

        super.extractRenderState(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();
        int button = event.button();

        // While binding, the next click cancels the bind.
        if (bindingModule != null) {
            bindingModule = null;
            return true;
        }

        for (int i = panels.size() - 1; i >= 0; i--) {
            CategoryPanel panel = panels.get(i);
            Module module = panel.getModuleAt(mouseX, mouseY);
            if (module != null) {
                if (button == 0) {
                    module.toggle();
                    ChatUtils.info(module.getName() + (module.isEnabled() ? " enabled." : " disabled."));
                } else if (button == 1) {
                    bindingModule = module;
                    ChatUtils.info("Press a key to bind " + module.getName() + " (ESC to clear).");
                }
                return true;
            }
            if (panel.isHeaderAt(mouseX, mouseY)) {
                // Bring the panel to the front and start dragging it.
                panels.remove(panel);
                panels.add(panel);
                draggingPanel = panel;
                dragOffsetX = (int) mouseX - panel.x;
                dragOffsetY = (int) mouseY - panel.y;
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (draggingPanel != null) {
            draggingPanel = null;
            return true;
        }
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double deltaX, double deltaY) {
        if (draggingPanel != null) {
            draggingPanel.x = (int) event.x() - dragOffsetX;
            draggingPanel.y = (int) event.y() - dragOffsetY;
            return true;
        }
        return super.mouseDragged(event, deltaX, deltaY);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (bindingModule != null) {
            int keyCode = event.key();
            if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_DELETE) {
                bindingModule.setKeybind(Module.NO_KEY);
                ChatUtils.info("Cleared keybind for " + bindingModule.getName() + ".");
            } else {
                bindingModule.setKeybind(keyCode);
                String keyName = InputConstants.Type.KEYSYM.getOrCreate(keyCode).getDisplayName().getString();
                ChatUtils.info("Bound " + bindingModule.getName() + " to " + keyName + ".");
            }
            bindingModule = null;
            return true;
        }
        return super.keyPressed(event);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
