package com.junocore.item.listeners;

import com.junocore.item.JItemManager;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class JItemPickupListener implements Listener {

    private JItemManager itemManager;

    public JItemPickupListener(JItemManager itemManager){
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e){
        if(!(e.getEntity() instanceof Player)) return;

        NBTItem item = new NBTItem(e.getItem().getItemStack());
        if(!item.hasKey("juno")) return;


    }

}
