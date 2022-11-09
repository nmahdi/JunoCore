package io.github.nmahdi.JunoCore.world;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class PlayerWorld {

    private World world;
    private Player player;

    public PlayerWorld(World world, Player player){
        this.world = world;
        this.player = player;
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

}
