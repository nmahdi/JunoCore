package io.github.nmahdi.JunoCore.world;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.generation.ResourceType;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import io.github.nmahdi.JunoCore.utils.LocationHelper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WorldManager implements JunoManager {

    private boolean debugMode;
    private final World primaryWorld = Bukkit.getWorld("world");

    public WorldManager(JCore main){
        this.debugMode = main.getConfig().getBoolean("debug-mode.world");

        JLogger.log("World manager has been loaded.");
    }

    public World getPrimaryWorld() {
        return primaryWorld;
    }

    @Override
    public void setDebugMode(boolean mode) {
        this.debugMode = mode;
    }

    @Override
    public boolean isDebugging() {
        return debugMode;
    }

    @Override
    public void onDisable() {

    }

}
