package io.github.nmahdi.JunoCore.events;


import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.item.stats.EquipmentSlotID;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JEquipmentEquipEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private NBTItem item;
    private EquipmentSlotID slot;

    public JEquipmentEquipEvent(Player player, NBTItem item, EquipmentSlotID slot){
        this.player = player;
        this.item = item;
        this.slot = slot;
    }

    public Player getPlayer() {
        return player;
    }

    public NBTItem getItem() {
        return item;
    }

    public NBTCompound getJuno(){
        return item.getCompound("juno");
    }

    public EquipmentSlotID getSlot() {
        return slot;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

}
