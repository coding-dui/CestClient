package com.coding_dui.cestclient.module;

public enum Category {
    COMBAT("Combat", 0xFFFF5555),
    MOVEMENT("Movement", 0xFF55FFFF),
    RENDER("Render", 0xFF55FF55),
    PLAYER("Player", 0xFFFFAA00),
    MISC("Misc", 0xFFAA55FF);

    private final String displayName;
    private final int color;

    Category(String displayName, int color) {
        this.displayName = displayName;
        this.color = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getColor() {
        return color;
    }
}
