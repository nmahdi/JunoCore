package com.junocore;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class JHologramsManager {

    private JCore jcore;

    public JHologramsManager(JCore jcore){
        this.jcore = jcore;
    }


    public void createDamageHologram(Location location, int damage){
        Hologram hologram = createHologram(location, "&f" + damage);
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(jcore, new Runnable() {
            @Override
            public void run() {
                hologram.delete();
            }
        },60);
    }


    private Hologram createHologram(Location location, String... strings){
        Hologram hologram = HologramsAPI.createHologram(jcore, location.add(
                randomWithRange(0d, 0.1d),
                randomWithRange(0.3d, 0.6d),
                randomWithRange(0d, 0.1d)));
        for(String s : strings){
            hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', s));
        }
        hologram.getVisibilityManager().setVisibleByDefault(true);
        for(int i = 0; i < location.getWorld().getPlayers().size(); i ++){
            hologram.getVisibilityManager().showTo(location.getWorld().getPlayers().get(i));
        }
        return hologram;
    }

    private double randomWithRange(double min, double max) {
        double range = max - min + 1.0D;
        return Math.random() * range + min;
    }

}
