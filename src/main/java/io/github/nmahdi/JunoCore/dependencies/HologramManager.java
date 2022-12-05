package io.github.nmahdi.JunoCore.dependencies;


import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class HologramManager implements JunoManager {

    private JCore jcore;
    private int counter = 0;

    private ArrayList<Hologram> holograms = new ArrayList<>();

    public HologramManager(JCore jcore){
        this.jcore = jcore;
        JLogger.log("Holograms Manager has been enabled.");
    }


    public void createDamageHologram(Location location, int damage){
        createHologram(location.add(randomWithRange(0.1, 0.5), randomWithRange(1, 1.5), randomWithRange(0.1, 0.5)), "&c" + damage);
    }

    public void createXPHologram(Location location, int xp, Skill skill, Player player){
        createHologram(location.add(0, 1.4, 0),"&b+" + xp + " " + skill.getDisplayName() + " XP");
    }


    private Hologram createHologram(Location location, String... strings){
        counter++;
        Hologram hologram = DHAPI.createHologram(String.valueOf(counter), location, Arrays.asList(strings));
        holograms.add(hologram);
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(jcore, new Runnable() {
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

    private double randomWithRange(double min, double max) {
        double range = max - min + 1.0D;
        return Math.random() * range + min;
    }

    @Override
    public boolean isDebugging() {
        return false;
    }

    @Override
    public void debug(String s) {
        JLogger.debug(this, s);
    }

    @Override
    public void onDisable() {
        clearHolograms();
    }


}
