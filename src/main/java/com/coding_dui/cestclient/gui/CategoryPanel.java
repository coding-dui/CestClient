package com.coding_dui.cestclient.gui;

import com.coding_dui.cestclient.module.Category;
import com.coding_dui.cestclient.module.Module;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.List;

public class CategoryPanel {
    public static final int WIDTH = 110;
    public static final int HEADER_HEIGHT = 15;
    public static final int ROW_HEIGHT = 13;

    private final Category category;
    private final List<Module> modules;

    public int x;
    public int y;

    public CategoryPanel(Category category, int x, int y, List<Module> modules) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.modules = modules;
    }

    public int getHeight() {
        return HEADER_HEIGHT + modules.size() * ROW_HEIGHT + 2;
    }

    public boolean isHeaderAt(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + WIDTH && mouseY >= y && mouseY <= y + HEADER_HEIGHT;
    }

    /** Returns the module row under the cursor, or null. */
    public Module getModuleAt(double mouseX, double mouseY) {
        if (mouseX < x || mouseX > x + WIDTH) {
            return null;
        }
        int relativeY = (int) mouseY - (y + HEADER_HEIGHT);
        if (relativeY < 0) {
            return null;
        }
        int index = relativeY / ROW_HEIGHT;
        if (index >= modules.size()) {
            return null;
        }
        return modules.get(index);
    }

    public void extract(GuiGraphicsExtractor context, Font font, int mouseX, int mouseY, Module bindingModule) {
        int height = getHeight();

        // Panel body and border.
        context.fill(x - 1, y - 1, x + WIDTH + 1, y + height + 1, 0xFF000000);
        context.fill(x, y, x + WIDTH, y + height, 0xFF141418);

        // Header.
        context.fill(x, y, x + WIDTH, y + HEADER_HEIGHT - 1, category.getColor());
        context.centeredText(font, category.getDisplayName(), x + WIDTH / 2, y + 3, 0xFF141418);

        // Module rows.
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            int rowY = y + HEADER_HEIGHT + i * ROW_HEIGHT;
            boolean hovered = mouseX >= x && mouseX <= x + WIDTH && mouseY >= rowY && mouseY < rowY + ROW_HEIGHT;

            if (module == bindingModule) {
                context.fill(x, rowY, x + WIDTH, rowY + ROW_HEIGHT, 0xFF3A2E00);
            } else if (module.isEnabled()) {
                context.fill(x, rowY, x + WIDTH, rowY + ROW_HEIGHT, 0xFF1E3A1E);
            } else if (hovered) {
                context.fill(x, rowY, x + WIDTH, rowY + ROW_HEIGHT, 0x30FFFFFF);
            }

            int color = module.isEnabled() ? 0xFF55FF55 : 0xFFDDDDDD;
            String label = module.getName();
            if (module == bindingModule) {
                label = module.getName() + " ...";
                color = 0xFFFFAA00;
            }
            context.text(font, label, x + 3, rowY + 3, color);

            if (module.getKeybind() != Module.NO_KEY && module != bindingModule) {
                String key = InputConstants.Type.KEYSYM.getOrCreate(module.getKeybind()).getDisplayName().getString();
                int keyWidth = font.width(key);
                context.text(font, key, x + WIDTH - keyWidth - 3, rowY + 3, 0xFF8888FF);
            }
        }
    }
}
