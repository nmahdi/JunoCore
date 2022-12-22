package io.github.nmahdi.JunoCore.dependencies;


import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import io.github.nmahdi.JunoCore.utils.LocationHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class HologramManager implements JunoManager {

    private boolean debugMode;
    private JCore main;
    private int counter = 0;

    private ArrayList<Hologram> holograms = new ArrayList<>();

    public HologramManager(JCore main){
        debugMode = main.getConfig().getBoolean("debug-mode.hologram");
        this.main = main;
        JLogger.log("Holograms Manager has been enabled.");
    }


    public void createDamageHologram(Location location, int damage){
        createHologram(location.add(LocationHelper.randomWithRange(0.1, 0.5), LocationHelper.randomWithRange(1, 1.5), LocationHelper.randomWithRange(0.1, 0.5)), "&c" + damage);
    }


    private Hologram createHologram(Location location, String... strings){
        counter++;
        Hologram hologram = DHAPI.createHologram(String.valueOf(counter), location, Arrays.asList(strings));
        holograms.add(hologram);
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                holograms.remove(hologram);
                hologram.delete();
            }
        },30);
        return hologram;
    }

    private void clearHolograms() {
        if (!holograms.isEmpty()){
            for (Hologram h : holograms) {
                if(h != null) h.delete();
            }
            holograms.clear();
        }
    }


    @Override
    public void debug(String s) {
        JLogger.debug(this, s);
    }

    @Override
    public boolean isDebugging() {
        return debugMode;
    }

    @Override
    public void setDebugMode(boolean mode) {
        debugMode = mode;
    }

    @Override
    public void onDisable() {
        clearHolograms();
    }


}
