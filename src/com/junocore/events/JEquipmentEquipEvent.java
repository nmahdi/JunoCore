package com.junocore.events;

import com.junocore.item.stats.EquipmentSlotID;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JEquipmentEquipEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private NBTItem item;
    private EquipmentSlotID slot;

    public JEquipmentEquipEvent(NBTItem item, EquipmentSlotID slot){
        this.item = item;
        this.slot = slot;
    }

    public NBTItem getItem() {
        return item;
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
