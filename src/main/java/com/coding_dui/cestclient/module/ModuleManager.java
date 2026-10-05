package com.coding_dui.cestclient.module;

import com.coding_dui.cestclient.modules.combat.AutoClicker;
import com.coding_dui.cestclient.modules.combat.Criticals;
import com.coding_dui.cestclient.modules.combat.KillAura;
import com.coding_dui.cestclient.modules.combat.Orbit;
import com.coding_dui.cestclient.modules.combat.Reach;
import com.coding_dui.cestclient.modules.misc.Bypass;
import com.coding_dui.cestclient.modules.movement.AutoSprint;
import com.coding_dui.cestclient.modules.movement.BunnyHop;
import com.coding_dui.cestclient.modules.movement.Flight;
import com.coding_dui.cestclient.modules.movement.HighJump;
import com.coding_dui.cestclient.modules.movement.NoFall;
import com.coding_dui.cestclient.modules.movement.Speed;
import com.coding_dui.cestclient.modules.movement.Step;
import com.coding_dui.cestclient.modules.movement.Velocity;
import com.coding_dui.cestclient.modules.player.AutoRespawn;
import com.coding_dui.cestclient.modules.player.AutoTool;
import com.coding_dui.cestclient.modules.player.FastPlace;
import com.coding_dui.cestclient.modules.player.Nuker;
import com.coding_dui.cestclient.modules.render.Clock;
import com.coding_dui.cestclient.modules.render.Coordinates;
import com.coding_dui.cestclient.modules.render.Esp;
import com.coding_dui.cestclient.modules.render.FpsDisplay;
import com.coding_dui.cestclient.modules.render.Fullbright;
import com.coding_dui.cestclient.modules.render.Ping;
import com.coding_dui.cestclient.modules.render.Tracers;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void init() {
        // Movement
        register(new Flight());
        register(new AutoSprint());
        register(new Speed());
        register(new Step());
        register(new HighJump());
        register(new NoFall());
        register(new Velocity());
        register(new BunnyHop());

        // Player
        register(new AutoRespawn());
        register(new FastPlace());
        register(new AutoTool());
        register(new Nuker());

        // Combat
        register(new AutoClicker());
        register(new Criticals());
        register(new KillAura());
        register(new Reach());
        register(new Orbit());

        // Render
        register(new Fullbright());
        register(new Coordinates());
        register(new FpsDisplay());
        register(new Ping());
        register(new Clock());
        register(new Esp());
        register(new Tracers());

        // Misc. Kept last so its "legit mode" clamping runs after everything else.
        register(new Bypass());
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
