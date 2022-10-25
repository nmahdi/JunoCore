package io.github.nmahdi.JunoCore.player.listeners;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.events.JEquipmentEquipEvent;
import io.github.nmahdi.JunoCore.item.JItemManager;
import io.github.nmahdi.JunoCore.item.stats.EquipmentSlotID;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import io.github.nmahdi.JunoCore.player.PlayerStatID;
import net.citizensnpcs.api.trait.trait.Equipment;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EquipmentEquipListener implements Listener {

    private JCore main;
    private JItemManager itemManager;

    public EquipmentEquipListener(JCore main, JItemManager itemManager){
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
        this.itemManager = itemManager;
    }

    @EventHandler
    public void onEquip(JEquipmentEquipEvent e) {
        NBTCompound playerStats = new NBTEntity(e.getPlayer()).getPersistentDataContainer().getCompound("juno").getCompound("stats");
        if(e.getOldItem() != null){
            NBTCompound stats = e.getOldItem().getCompound("juno").getCompound("stats");
            for(ItemStatID id : ItemStatID.values()){
                if(stats.hasKey(id.getID())){
                    if(itemManager.isPlayerStat(id)) {
                        playerStats.setInteger(id.getID(), playerStats.getInteger(id.getID())-Integer.parseInt(stats.getString(id.getID())));
                    }
                }
            }
            e.getPlayer().sendMessage(ChatColor.RED + "Unequipped: " + e.getOldItem().getItem().getItemMeta().getDisplayName());
        }
        if (e.getNewItem() != null) {
            NBTCompound stats = e.getNewItem().getCompound("juno").getCompound("stats");
            for(ItemStatID id : ItemStatID.values()){
                if(stats.hasKey(id.getID())){
                    if(itemManager.isPlayerStat(id)) {
                        playerStats.setInteger(id.getID(), playerStats.getInteger(id.getID())+Integer.parseInt(stats.getString(id.getID())));
                    }
                }
            }
            e.getPlayer().sendMessage(ChatColor.GREEN + "Equipped " + e.getNewItem().getItem().getItemMeta().getDisplayName());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClick(InventoryClickEvent e) {
        boolean shift = false, numKey = false;
        if(e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) shift = true;
        if(e.getClick().equals(ClickType.NUMBER_KEY)) numKey = true;
        if(e.getSlotType() != InventoryType.SlotType.ARMOR && e.getSlotType() != InventoryType.SlotType.QUICKBAR && e.getSlotType() != InventoryType.SlotType.CONTAINER) return;
        if(e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
        if(!(e.getWhoClicked() instanceof Player)) return;
        EquipmentSlotID newEquipment = EquipmentSlotID.matchType(shift ? e.getCurrentItem() : e.getCursor());
        if(!shift && newEquipment != null && isArmor(newEquipment) && e.getRawSlot() != newEquipment.getSlot()) return;
        if(shift){
            if(newEquipment != null){
                boolean equipping = e.getRawSlot() != newEquipment.getSlot();
                if(newEquipment.equals(EquipmentSlotID.Helmet) &&
                        (equipping ? isAir(e.getWhoClicked().getInventory().getHelmet()) : !isAir(e.getWhoClicked().getInventory().getHelmet())) ||
                    newEquipment.equals(EquipmentSlotID.Chestplate) &&
                        (equipping ? isAir(e.getWhoClicked().getInventory().getChestplate()) : !isAir(e.getWhoClicked().getInventory().getChestplate())) ||
                    newEquipment.equals(EquipmentSlotID.Leggings) &&
                        (equipping ? isAir(e.getWhoClicked().getInventory().getLeggings()) : !isAir(e.getWhoClicked().getInventory().getLeggings())) ||
                    newEquipment.equals(EquipmentSlotID.Boots) &&
                        (equipping ? isAir(e.getWhoClicked().getInventory().getBoots()) : !isAir(e.getWhoClicked().getInventory().getBoots()))){
                    JEquipmentEquipEvent equipEvent = new JEquipmentEquipEvent((Player)e.getWhoClicked(), JEquipmentEquipEvent.EquipMethod.SHIFT_CLICK, newEquipment,  equipping ? null : new NBTItem(e.getCurrentItem()), equipping ? new NBTItem(e.getCurrentItem()) : null);
                    Bukkit.getServer().getPluginManager().callEvent(equipEvent);
                    if(equipEvent.isCancelled()){
                        e.setCancelled(true);
                    }
                    if(newEquipment.equals(EquipmentSlotID.Helmet) && equipping) {
                        e.getWhoClicked().getInventory().setHelmet(e.getCurrentItem());
                        e.setCurrentItem(null);
                    }
                }
            }
        }else{
            if(e.getSlotType() != InventoryType.SlotType.ARMOR) return;
            ItemStack newArmorPiece = e.getCursor();
            ItemStack oldArmorPiece = e.getCurrentItem();
            if(numKey){
                if(e.getClickedInventory().getType().equals(InventoryType.PLAYER)){
                    ItemStack hotbarItem = e.getClickedInventory().getItem(e.getHotbarButton());
                    if(!isAir(hotbarItem)){
                        newEquipment = EquipmentSlotID.matchType(hotbarItem);
                        newArmorPiece = hotbarItem;
                        oldArmorPiece = e.getClickedInventory().getItem(e.getSlot());
                    }else{
                        newEquipment = EquipmentSlotID.matchType(!isAir(oldArmorPiece) ? oldArmorPiece : newArmorPiece);
                    }
                }
            }else{
                if(isAir(newArmorPiece) && !isAir(oldArmorPiece)){ // Unequip with no new item going into the slot.
                    newEquipment = EquipmentSlotID.matchType(oldArmorPiece);
                }
                if(newEquipment.equals(EquipmentSlotID.Helmet)){
                    e.setCurrentItem(newArmorPiece);
                    e.getWhoClicked().setItemOnCursor(oldArmorPiece);
                    e.setCancelled(true);
                }
            }
            if(newEquipment != null && isArmor(newEquipment) && e.getRawSlot() == newEquipment.getSlot()){
                JEquipmentEquipEvent.EquipMethod method = JEquipmentEquipEvent.EquipMethod.PICK_DROP;
                if(e.getAction().equals(InventoryAction.HOTBAR_SWAP) || numKey) method = JEquipmentEquipEvent.EquipMethod.HOTBAR_SWAP;
                JEquipmentEquipEvent equipEvent = new JEquipmentEquipEvent((Player)e.getWhoClicked(), method, newEquipment, oldArmorPiece, newArmorPiece);
                Bukkit.getServer().getPluginManager().callEvent(equipEvent);
                if(equipEvent.isCancelled()){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e){
        if(isAir(e.getItem())) return;
        if(e.getItem().hasItemMeta() && !new NBTItem(e.getItem()).hasKey("juno")) return;
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            EquipmentSlotID newEquipment = EquipmentSlotID.matchType(e.getItem());
            if(newEquipment == null) return;
            if(newEquipment.equals(EquipmentSlotID.Helmet) && isAir(e.getPlayer().getInventory().getHelmet()) ||
                newEquipment.equals(EquipmentSlotID.Chestplate) && isAir(e.getPlayer().getInventory().getChestplate()) ||
                newEquipment.equals(EquipmentSlotID.Leggings) && isAir(e.getPlayer().getInventory().getLeggings()) ||
                newEquipment.equals(EquipmentSlotID.Boots) && isAir(e.getPlayer().getInventory().getBoots())){
                JEquipmentEquipEvent equipEvent = new JEquipmentEquipEvent(e.getPlayer(), JEquipmentEquipEvent.EquipMethod.HOTBAR, newEquipment, null, new NBTItem(e.getItem()));
                Bukkit.getServer().getPluginManager().callEvent(equipEvent);
                if(newEquipment.equals(EquipmentSlotID.Helmet)){
                    e.getPlayer().getInventory().setHelmet(e.getItem());
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                        @Override
                        public void run() {
                            e.getPlayer().getInventory().setItemInMainHand(null);
                        }
                    },1);
                }
                if(equipEvent.isCancelled()){
                    e.setCancelled(true);
                    e.getPlayer().updateInventory();
                }
            }
        }
    }

    @EventHandler
    public void onInvDrag(InventoryDragEvent e){
        EquipmentSlotID type = EquipmentSlotID.matchType(e.getOldCursor());
        if(type == null) return;
        if(e.getRawSlots().isEmpty()) return;
        if(type.getSlot() == e.getRawSlots().stream().findFirst().orElse(0)){
            JEquipmentEquipEvent equipEvent = new JEquipmentEquipEvent((Player)e.getWhoClicked(), JEquipmentEquipEvent.EquipMethod.DRAG, type, null, new NBTItem(e.getOldCursor()));
            Bukkit.getServer().getPluginManager().callEvent(equipEvent);
            if(equipEvent.isCancelled()){
                e.setResult(Event.Result.DENY);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHelmetPlace(BlockPlaceEvent e){
        if(!e.getItemInHand().hasItemMeta()) return;
        NBTItem item = new NBTItem(e.getItemInHand());
        if(!item.hasKey("juno") && item.getCompound("juno").hasKey("hidden-stats")) return;
        if(item.getCompound("juno").getCompound("hidden-stats").hasKey(ItemStatID.EquipmentSlot.getID())) e.setCancelled(true);
    }

    @EventHandler
    public void dispenseArmorEvent(BlockDispenseArmorEvent e){
        e.setCancelled(true);
    }

    private boolean isArmor(EquipmentSlotID slot){
        return slot.getSlot() != 9999;
    }

    private boolean isAir(ItemStack stack){
        return stack == null || stack.getType().isAir();
    }

}
