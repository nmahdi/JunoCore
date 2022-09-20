package com.junocore.item.listeners;

import com.junocore.JCore;
import com.junocore.events.JEquipmentEquipEvent;
import com.junocore.item.JItemManager;
import com.junocore.item.stats.EquipmentSlotID;
import com.junocore.item.stats.ItemStatID;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class EquipmentEquipListener implements Listener {


    public EquipmentEquipListener(JCore main){
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onEquip(JEquipmentEquipEvent e){
        //Add equiping code
    }

    @EventHandler
    public void onEquipmentClick(InventoryClickEvent e){
        if(!e.getCurrentItem().hasItemMeta()) return;
        NBTItem item = new NBTItem(e.getCurrentItem());
        if(!item.hasKey("juno")) return;
        if(!item.getCompound("juno").hasKey("hidden-stats")) return;
        NBTCompound hStats = item.getCompound("juno").getCompound("hidden-stats");
        if(!hStats.hasKey(ItemStatID.EquipmentSlot.getID())) return;
        if(e.getSlotType() == InventoryType.SlotType.ARMOR) return;
        e.setCancelled(true);
        for(EquipmentSlotID id : EquipmentSlotID.values()){
            if(id == EquipmentSlotID.Helmet) {
                e.getWhoClicked().getEquipment().setHelmet(e.getCurrentItem());
                Bukkit.getServer().getPluginManager().callEvent(new JEquipmentEquipEvent(item, id));
            }
        }
        e.setCurrentItem(null);

    }

}
