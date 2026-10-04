package com.coding_dui.cestclient.gui;

import com.coding_dui.cestclient.CestClient;
import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import com.coding_dui.cestclient.util.ChatUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
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
        super(Text.literal("CestClient"));
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
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        context.drawTextWithShadow(this.textRenderer, "CestClient", 6, 6, 0xFF00E0FF);
        context.drawTextWithShadow(this.textRenderer,
                "Left-click: toggle   Right-click: bind key   Drag headers   ESC: close",
                6, this.height - 12, 0xFFAAAAAA);

        for (CategoryPanel panel : panels) {
            panel.render(context, this.textRenderer, mouseX, mouseY, bindingModule);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
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
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (draggingPanel != null) {
            draggingPanel = null;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (draggingPanel != null) {
            draggingPanel.x = (int) mouseX - dragOffsetX;
            draggingPanel.y = (int) mouseY - dragOffsetY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (bindingModule != null) {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_DELETE) {
                bindingModule.setKeybind(Module.NO_KEY);
                ChatUtils.info("Cleared keybind for " + bindingModule.getName() + ".");
            } else {
                bindingModule.setKeybind(keyCode);
                String keyName = InputUtil.Type.KEYSYM.createFromCode(keyCode).getLocalizedText().getString();
                ChatUtils.info("Bound " + bindingModule.getName() + " to " + keyName + ".");
            }
            bindingModule = null;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
