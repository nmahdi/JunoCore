package io.github.nmahdi.JunoCore.world;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WorldManager {

    private ArrayList<PlayerWorld> worlds = new ArrayList<>();


    public void createWorld(Player player){

    }

    public void loadWorld(Player player){
        PlayerWorld world = new PlayerWorld(Bukkit.getServer().createWorld(new WorldCreator(player.getUniqueId().toString())), player);
        worlds.add(world);
    }

}
