package io.github.nmahdi.JunoCore.item.listeners;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.events.JEquipmentEquipEvent;
import io.github.nmahdi.JunoCore.item.JItemManager;
import io.github.nmahdi.JunoCore.item.stats.EquipmentSlotID;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class EquipmentEquipListener implements Listener {


    private JItemManager itemManager;

    public EquipmentEquipListener(JCore main, JItemManager itemManager){
        main.getServer().getPluginManager().registerEvents(this, main);
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onEquip(JEquipmentEquipEvent e){
        NBTCompound juno = new NBTEntity(e.getPlayer()).getPersistentDataContainer().getCompound("juno");
        NBTCompound stats = juno.getCompound("stats");
        for(ItemStatID id : ItemStatID.values()){
            if(e.getJuno().getCompound("stats").hasKey(id.getID())){
                if(itemManager.isPlayerStat(id)) {
                    stats.setInteger(id.getID(), stats.getInteger(id.getID())+Integer.parseInt(e.getJuno().getCompound("stats").getString(id.getID())));
                }
            }
        }
        e.getPlayer().sendMessage(ChatColor.GREEN + "Equipped " + e.getItem().getItem().getItemMeta().getDisplayName());
    }

    @EventHandler
    public void onEquipmentClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;
        //Normal Clicking an Item
        if (e.getCursor().getType() != Material.AIR) {
            if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
                //Juno Check
                NBTItem cursor = new NBTItem(e.getCursor());
                if (!cursor.hasKey("juno")) return;
                if (!cursor.getCompound("juno").hasKey("hidden-stats")) return;
                NBTCompound chStats = cursor.getCompound("juno").getCompound("hidden-stats");
                if (!chStats.hasKey(ItemStatID.EquipmentSlot.getID())) return;

                EquipmentSlotID cSlot = itemManager.getSlotByID(chStats.getString(ItemStatID.EquipmentSlot.getID()));

                if(cSlot == EquipmentSlotID.Helmet){

                    if(hasArmor(e.getWhoClicked(), cSlot)) return;

                    e.setCurrentItem(e.getCursor());
                    Bukkit.getServer().getPluginManager().callEvent(new JEquipmentEquipEvent((Player) e.getWhoClicked(), new NBTItem(e.getCursor()), cSlot));
                    e.getWhoClicked().setItemOnCursor(null);
                }
            }
            return;
        }

        //Shift Clicking an Item
        if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
            //Juno Check
            if (!e.getCurrentItem().hasItemMeta()) return;
            NBTItem item = new NBTItem(e.getCurrentItem());
            if (!item.hasKey("juno")) return;
            if (!item.getCompound("juno").hasKey("hidden-stats")) return;
            NBTCompound hStats = item.getCompound("juno").getCompound("hidden-stats");
            if (!hStats.hasKey(ItemStatID.EquipmentSlot.getID())) return;

            EquipmentSlotID slot = itemManager.getSlotByID(hStats.getString(ItemStatID.EquipmentSlot.getID()));

            if(hasArmor(e.getWhoClicked(), slot)) return;

            if (slot == EquipmentSlotID.Helmet) {
                e.getWhoClicked().getEquipment().setHelmet(e.getCurrentItem());
                e.setCurrentItem(null);
            }
            Bukkit.getServer().getPluginManager().callEvent(new JEquipmentEquipEvent((Player) e.getWhoClicked(), item, slot));
        }
    }

    private boolean hasArmor(HumanEntity player, EquipmentSlotID slot){
        if(slot == EquipmentSlotID.Helmet) {
            if(player.getEquipment().getHelmet() != null) return true;
        }
        if(slot == EquipmentSlotID.Chestplate) {
            if(player.getEquipment().getChestplate() != null) return true;
        }
        if(slot == EquipmentSlotID.Leggings) {
            if(player.getEquipment().getLeggings() != null) return true;
        }
        if(slot == EquipmentSlotID.Boots) {
            if(player.getEquipment().getBoots() != null) return true;
        }
        return false;
    }

}
