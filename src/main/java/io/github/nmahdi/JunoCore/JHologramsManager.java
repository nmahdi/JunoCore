package io.github.nmahdi.JunoCore;


import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import io.github.nmahdi.JunoCore.player.skills.SkillID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class JHologramsManager {

    private JCore jcore;
    private int counter = 0;

    private ArrayList<Hologram> holograms = new ArrayList<>();

    public JHologramsManager(JCore jcore){
        this.jcore = jcore;
    }


    public void createDamageHologram(Location location, int damage){
        Hologram hologram = createHologram(location, "&f" + damage);
        holograms.add(hologram);
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(jcore, new Runnable() {
            @Override
            public void run() {
                holograms.remove(hologram);
                hologram.delete();
            }
        },60);
        counter++;
    }

    public void createXPHologram(Location location, int xp, SkillID skill, Player player){
        Hologram hologram = createHologram(location, "&f+" + xp + " " + skill.getDisplayName() + " XP", "[" + player.getName() + "]");
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(jcore, new Runnable() {
            @Override
            public void run() {
                hologram.delete();
            }
        },60);
        counter++;
    }


    private Hologram createHologram(Location location, String... strings){
        return DHAPI.createHologram(String.valueOf(counter), location.add(
                randomWithRange(0d, 0.1d),
                randomWithRange(0.3d, 0.6d),
                randomWithRange(0d, 0.1d)), Arrays.asList(strings));
    }

    public void clearHolograms(){
        for(Hologram h : holograms){
            h.delete();
        }
    }

    private double randomWithRange(double min, double max) {
        double range = max - min + 1.0D;
        return Math.random() * range + min;
    }

}
