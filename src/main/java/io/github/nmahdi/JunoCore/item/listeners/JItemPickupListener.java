package io.github.nmahdi.JunoCore.item.listeners;

import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.item.NBTJItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class JItemPickupListener implements Listener {


    public JItemPickupListener(){
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e){
        if(!(e.getEntity() instanceof Player)) return;

        NBTJItem item = new NBTJItem(e.getItem().getItemStack());
        if(!item.hasJuno()) return;


    }

}
