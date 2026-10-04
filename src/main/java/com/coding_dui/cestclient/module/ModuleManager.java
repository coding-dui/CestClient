package com.coding_dui.cestclient.module;

import com.coding_dui.cestclient.modules.movement.AutoSprint;
import com.coding_dui.cestclient.modules.movement.Flight;
import com.coding_dui.cestclient.modules.render.Fullbright;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void init() {
        // Register modules here
        register(new Flight());
        register(new AutoSprint());
        register(new Fullbright());
    }

    private void register(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    /** Finds a module by its name, ignoring case. Returns null when not found. */
    public Module getModule(String name) {
        if (name == null) {
            return null;
        }
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public List<Module> getModulesByCategory(Category category) {
        List<Module> result = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory() == category) {
                result.add(module);
            }
        }
        return result;
    }

    public List<Module> getEnabledModules() {
        List<Module> result = new ArrayList<>();
        for (Module module : modules) {
            if (module.isEnabled()) {
                result.add(module);
            }
        }
        return result;
    }

    /** Disables every enabled module and returns how many were disabled. */
    public int disableAll() {
        int disabled = 0;
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.setEnabled(false);
                disabled++;
            }
        }
        return disabled;
    }

    public void onTick() {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onTick();
            }
        }
    }
}
