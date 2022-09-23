package io.github.nmahdi.JunoCore.events;

import io.github.nmahdi.JunoCore.entity.JEntity;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JEntityDeathEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private World world;
    private Location deathLocation;
    private Player player;
    private JEntity jEntity;

    public JEntityDeathEvent(World world, Location deathLocation, Player player, JEntity jEntity){
        this.world = world;
        this.player = player;
        this.deathLocation = deathLocation;
        this.jEntity = jEntity;
    }

    public World getWorld() {
        return world;
    }

    public Location getDeathLocation() {
        return deathLocation;
    }

    public Player getPlayer() {
        return player;
    }

    public JEntity getJEntity() {
        return jEntity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
