package com.coding_dui.cestclient;

import net.fabricmc.api.ClientModInitializer;
import com.coding_dui.cestclient.module.ModuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CestClient implements ClientModInitializer {
    public static final String MOD_ID = "cestclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static CestClient INSTANCE;

    private ModuleManager moduleManager;

    @Override
    public void onInitializeClient() {
        INSTANCE = this;
        LOGGER.info("Initializing CestClient...");
        
        moduleManager = new ModuleManager();
        moduleManager.init();
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
