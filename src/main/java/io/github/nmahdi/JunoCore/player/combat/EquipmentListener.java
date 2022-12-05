package io.github.nmahdi.JunoCore.player.combat;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.utils.JLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;

public class EquipmentListener implements Listener {

    private JCore main;
    private PlayerManager playerManager;

    private final int OFFHAND_SLOT = 40;

    public static final int CAPE_SLOT = 0;
    public static final int BRACELET_SLOT = 1;
    public static final int RING_SLOT = 2;
    public static final int HEADBAND_SLOT = 3;
    public static final int NECKLACE_SLOT = 4;

    public EquipmentListener(JCore main) {
        this.main = main;
        this.playerManager = main.getPlayerManager();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e){
        if(e.getInventory().getType() != InventoryType.CRAFTING) return;
        if(!(e.getPlayer() instanceof Player)) return;
        CraftingInventory inventory = (CraftingInventory)e.getInventory();
        GamePlayer player = playerManager.getPlayer((Player)e.getPlayer());

        if(player == null){
            inventory.clear();
            return;
        }

        ItemStack[] itemStacks = inventory.getContents();
        for(int i = 0; i < itemStacks.length; i++){
            player.equipment.put(i, itemStacks[i]);
        }
        inventory.clear();
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
            inventory.setContents(itemStacks);
            inventory.setResult(itemStacks[CAPE_SLOT]);
        }, 1);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (!(e.getWhoClicked() instanceof Player)) return;

        boolean shift = false, numKey = false, drop = false;
        if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) shift = true;
        if (e.getClick().equals(ClickType.NUMBER_KEY)) numKey = true;
        if (e.getClick().equals(ClickType.DROP) || e.getClick().equals(ClickType.CONTROL_DROP)) drop = true;

        Player player = (Player) e.getWhoClicked();
        PlayerInventory playerInventory = player.getInventory();
        GamePlayer gamePlayer = playerManager.getPlayer(player);

        if(e.getClickedInventory().getType() == InventoryType.PLAYER){
            if(e.getSlot() == OFFHAND_SLOT){
                e.setCancelled(true);
                return;
            }
        }

        if(e.getClickedInventory().getType().equals(InventoryType.CRAFTING)){
            ItemStack result = e.getClickedInventory().getItem(CAPE_SLOT);
            e.setCancelled(true);

            if(shift){
                if(!InventoryHelper.isAirOrNull(e.getCurrentItem())) {
                    NBTGameItem item = new NBTGameItem(e.getCurrentItem());
                    HashMap<Integer, ItemStack> leftOver = playerInventory.addItem(item.getItem());
                    if(leftOver.isEmpty()) {
                        e.setCurrentItem(null);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                            if (InventoryHelper.isAirOrNull(e.getCurrentItem())) {
                                equipEquipment(gamePlayer, item, null);
                            }
                        }, 1);
                    }
                }
            }else {
                ItemStack current = e.getCurrentItem();
                ItemStack cursor = e.getCursor();

                if (!InventoryHelper.isAirOrNull(cursor)) {

                    NBTGameItem item = new NBTGameItem(cursor);
                    if (!item.hasID()) return;

                    GameItem gameItem = GameItem.getItem(item.getID());
                    if (gameItem == null) return;

                    if (getEquipmentSlot(gameItem.getItemType()) != e.getSlot()) return;

                }


                e.setCurrentItem(cursor);
                e.getWhoClicked().setItemOnCursor(current);

                equipEquipment(gamePlayer, InventoryHelper.isAirOrNull(current) ? null : new NBTGameItem(current), InventoryHelper.isAirOrNull(cursor) ? null : new NBTGameItem(cursor));
            }

            if (e.getSlot() != CAPE_SLOT) {
                e.getClickedInventory().setItem(CAPE_SLOT, result);
            }
            return;
        }

        if (!e.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;


        if (shift) {
            if (!InventoryHelper.isAirOrNull(e.getCurrentItem())) {

                if (InventoryHelper.isAirOrNull(playerInventory.getItemInMainHand())) {

                    Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                        if (!InventoryHelper.isAirOrNull(playerInventory.getItemInMainHand())) {
                            equipHand(gamePlayer, null, new NBTGameItem(playerInventory.getItemInMainHand()));
                        }
                    }, 1);

                } else {

                    if (e.getSlot() == playerInventory.getHeldItemSlot()) {

                        ItemStack itemStack = playerInventory.getItemInMainHand().clone();

                        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                            if (!playerInventory.getItemInMainHand().isSimilar(itemStack)) {
                                equipHand(gamePlayer, new NBTGameItem(itemStack),
                                        InventoryHelper.isAirOrNull(playerInventory.getItemInMainHand()) ? null : new NBTGameItem(playerInventory.getItemInMainHand()));
                            }
                        }, 1);

                    }

                }
            }
        } else {
            if (numKey) {
                if (e.getHotbarButton() == playerInventory.getHeldItemSlot()) {

                    equipHand(gamePlayer, InventoryHelper.isAirOrNull(playerInventory.getItemInMainHand()) ? null : new NBTGameItem(playerInventory.getItemInMainHand()),
                            InventoryHelper.isAirOrNull(e.getCurrentItem()) ? null : new NBTGameItem(e.getCurrentItem()));
                    return;
                }
                if (e.getSlot() == playerInventory.getHeldItemSlot()) {
                    ItemStack hotbar = playerInventory.getItem(e.getHotbarButton());
                    equipHand(gamePlayer, InventoryHelper.isAirOrNull(playerInventory.getItemInMainHand()) ? null : new NBTGameItem(playerInventory.getItemInMainHand()),
                            InventoryHelper.isAirOrNull(hotbar) ? null : new NBTGameItem(hotbar));
                    return;
                }

            } else if (e.getSlot() == playerInventory.getHeldItemSlot()) {
                ItemStack current = e.getCurrentItem();
                ItemStack cursor = e.getCursor();
                e.setCancelled(true);
                e.setCurrentItem(cursor);
                e.getWhoClicked().setItemOnCursor(current);
                equipHand(gamePlayer, InventoryHelper.isAirOrNull(current) ? null : new NBTGameItem(current),
                        InventoryHelper.isAirOrNull(cursor) ? null : new NBTGameItem(cursor));
                return;
            }
        }

        if (!e.getInventory().getType().equals(InventoryType.CRAFTING)) return;

        if(drop){
            if(InventoryHelper.isAirOrNull(e.getCurrentItem())) return;
            if(e.getSlotType() == InventoryType.SlotType.ARMOR) {
                GameItem item = GameItem.getItem(e.getCurrentItem());
                if (item == null || !item.isArmor()) return;
                equipArmor(gamePlayer,
                        new NBTGameItem(e.getCurrentItem()), null);
            }else{
                if(e.getRawSlot() == playerInventory.getHeldItemSlot()){
                    equipHand(gamePlayer, new NBTGameItem(e.getCurrentItem()), null);
                    JLogger.log("Dropped");
                }
            }
            return;
        }

        ItemStack newArmor = null;
        ItemStack oldArmor = null;

        if (shift) {
            if (InventoryHelper.isAirOrNull(e.getCurrentItem())) return;

            GameItem item = GameItem.getItem(new NBTGameItem(e.getCurrentItem()).getID());
            if (item == null) return;

            int equipmentSlot = getEquipmentSlot(item.getItemType());
            if(equipmentSlot == 999) return;

            if(item.isEquipment()){

                if(InventoryHelper.isAirOrNull(e.getInventory().getItem(equipmentSlot))){
                    ItemStack cape = e.getInventory().getItem(CAPE_SLOT);

                    ItemStack current = e.getCurrentItem();
                    e.getInventory().setItem(equipmentSlot, current);
                    e.setCurrentItem(null);
                    equipEquipment(gamePlayer, null, new NBTGameItem(current));

                    if(equipmentSlot != CAPE_SLOT) {
                        e.getInventory().setItem(CAPE_SLOT, cape);
                    }
                }

            }

            if(!item.isArmor()) return;


            boolean isArmorSlot = e.getSlot() == equipmentSlot;
            boolean equipping = InventoryHelper.isAirOrNull(playerInventory.getItem(equipmentSlot));

            if (equipping) {
                newArmor = e.getCurrentItem();
                playerInventory.setItem(equipmentSlot, e.getCurrentItem());
            } else {
                if(!isArmorSlot) return;
                if(InventoryHelper.isFull(playerInventory)) return;
                oldArmor = e.getCurrentItem();
                playerInventory.addItem(e.getCurrentItem());
            }
            e.setCancelled(true);
            e.setCurrentItem(null);
        }else {
            if (e.getSlotType() != InventoryType.SlotType.ARMOR) return;
            e.setCancelled(true);

            newArmor = e.getCursor();
            oldArmor = e.getCurrentItem();


            if (numKey) {
                ItemStack hotbar = playerInventory.getItem(e.getHotbarButton());
                if (!InventoryHelper.isAirOrNull(hotbar)) {
                    newArmor = hotbar;
                }
                oldArmor = playerInventory.getItem(e.getSlot());

                if (!InventoryHelper.isAirOrNull(newArmor)) {
                    GameItem item = GameItem.getItem(newArmor);
                    if (item == null || !item.isArmor()) return;
                    int equipmentSlot = getEquipmentSlot(item.getItemType());
                    if (e.getSlot() != equipmentSlot) return;
                    playerInventory.setItem(equipmentSlot, newArmor);
                } else {
                    playerInventory.setItem(e.getSlot(), null);
                }
                playerInventory.setItem(e.getHotbarButton(), oldArmor);
            }else{
                if (!InventoryHelper.isAirOrNull(newArmor)) {
                    GameItem item = GameItem.getItem(newArmor);
                    if (item == null || !item.isArmor()) return;
                    int equipmentSlot = getEquipmentSlot(item.getItemType());
                    playerInventory.setItem(equipmentSlot, newArmor);
                } else {
                    playerInventory.setItem(e.getSlot(), null);
                }
                player.setItemOnCursor(oldArmor);
            }
        }

        equipArmor(gamePlayer, InventoryHelper.isAirOrNull(oldArmor) ? null : new NBTGameItem(oldArmor),
                InventoryHelper.isAirOrNull(newArmor) ? null : new NBTGameItem(newArmor));

    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e){
        if(InventoryHelper.isAirOrNull(e.getItem())) return;
        if(!e.getItem().hasItemMeta()) return;
        GameItem item = GameItem.getItem(new NBTGameItem(e.getItem()).getID());
        if(item == null || !item.isArmor()) return;
        int equipmentSlot = getEquipmentSlot(item.getItemType());
        if(InventoryHelper.isAirOrNull(e.getPlayer().getInventory().getItem(equipmentSlot))){
            e.setCancelled(true);
            e.getPlayer().getInventory().setItem(equipmentSlot, e.getItem());
            equipArmor(playerManager.getPlayer(e.getPlayer()), null, new NBTGameItem(e.getItem()));
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                @Override
                public void run() {
                    e.getPlayer().getInventory().setItemInMainHand(null);
                }
            }, 1);
        }
    }

    @EventHandler
    public void onInvDrag(InventoryDragEvent e){
        if(e.getRawSlots().isEmpty()) return;
        GameItem item = GameItem.getItem(new NBTGameItem(e.getOldCursor()).getID());
        if(item == null) return;
        if(item.isArmor() || item.isEquipment()) {
            for(int i = 0; i < 9; i++){
                if(e.getRawSlots().contains(i)){
                    e.setCancelled(true);
                    return;
                }
            }
            if(e.getRawSlots().contains(OFFHAND_SLOT+5)){
                e.setCancelled(true);
                return;
            }
            /*e.setCancelled(true);
            int equipmentSlot = getDragEquipmentSlot(item.getItemType());
            if(equipmentSlot == 999) return;

            int slot = e.getRawSlots().stream().findFirst().orElse(0);
            if (equipmentSlot == slot) {

                GamePlayer player = playerManager.getPlayer((Player) e.getWhoClicked());

                e.getInventory().setItem(slot, e.getOldCursor());
                e.setCursor(null);
                if(item.isArmor()) {
                    equipArmor(player, null, new NBTGameItem(e.getOldCursor()));
                }else if(item.isEquipment()){
                    equipEquipment(player, null, new NBTGameItem(e.getOldCursor()));
                }
            }*/
        }
    }

    @EventHandler
    public void onItemPlace(BlockPlaceEvent e){
        if(InventoryHelper.isAirOrNull(e.getItemInHand())) return;
        GameItem item = GameItem.getItem(new NBTGameItem(e.getItemInHand()).getID());
        if(item == null) return;
        if(item.getItemType().getCatagory() != ItemType.Catagory.MISC) e.setCancelled(true);
    }

    @EventHandler
    public void dispenseArmorEvent(BlockDispenseArmorEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onHotbarDrop(PlayerDropItemEvent e){
        GamePlayer player = playerManager.getPlayer(e.getPlayer());
        if(player.hasHeldItem() && InventoryHelper.isAirOrNull(e.getPlayer().getInventory().getItemInMainHand())){
            equipHand(player, new NBTGameItem(e.getItemDrop().getItemStack()), null);
        }
    }

    @EventHandler
    public void onHeldSlotPickup(EntityPickupItemEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(InventoryHelper.isAirOrNull(((Player) e.getEntity()).getInventory().getItemInMainHand())){
            Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> {
                if(!InventoryHelper.isAirOrNull(((Player) e.getEntity()).getInventory().getItemInMainHand())){
                    equipHand(playerManager.getPlayer((Player)e.getEntity()), null, new NBTGameItem(((Player) e.getEntity()).getInventory().getItemInMainHand()));
                }
            },1);
        }
    }

    @EventHandler
    public void onSwap(PlayerItemHeldEvent e){
        GamePlayer player = playerManager.getPlayer(e.getPlayer());
        ItemStack oItem = player.getInventory().getItem(e.getPreviousSlot());
        ItemStack nItem = player.getInventory().getItem(e.getNewSlot());
        equipHand(player, InventoryHelper.isAirOrNull(oItem) ? null : new NBTGameItem(oItem), InventoryHelper.isAirOrNull(nItem) ? null : new NBTGameItem(nItem));
    }


    private void equipEquipment(GamePlayer player, NBTGameItem oldItem, NBTGameItem newItem){
        if(oldItem != null){
            GameItem item = GameItem.getItem(oldItem.getID());
            if(item != null && item.isEquipment()) {
                player.unequip(item, oldItem);
                player.getPlayerObject().sendMessage(ChatColor.RED + "Unequipped: " + oldItem.getItem().getItemMeta().getDisplayName());
            }
        }
        if (newItem != null) {
            GameItem item = GameItem.getItem(newItem.getID());
            if(item != null && item.isEquipment()) {
                player.equip(item, newItem);
                player.getPlayerObject().sendMessage(ChatColor.GREEN + "Equipped " + newItem.getItem().getItemMeta().getDisplayName());
            }
        }
    }

    private void equipArmor(GamePlayer player, NBTGameItem oldItem, NBTGameItem newItem){
        if(oldItem != null){
            GameItem item = GameItem.getItem(oldItem.getID());
            if(item != null && item.isArmor()) {
                player.unequip(item, oldItem);
                player.getPlayerObject().sendMessage(ChatColor.RED + "Unequipped: " + oldItem.getItem().getItemMeta().getDisplayName());
            }
        }
        if (newItem != null) {
            GameItem item = GameItem.getItem(newItem.getID());
            if(item != null && item.isArmor()) {
                player.equip(item, newItem);
                player.getPlayerObject().sendMessage(ChatColor.GREEN + "Equipped " + newItem.getItem().getItemMeta().getDisplayName());
            }
        }
    }

    private void equipHand(GamePlayer player, NBTGameItem oldItem, NBTGameItem newItem){
        if(oldItem != null){
            GameItem item = GameItem.getItem(oldItem.getID());
            if(item != null && item.isHandEquipable()) {
                player.unequip(item, oldItem);
                player.getPlayerObject().sendMessage(ChatColor.RED + "Unequipped: " + oldItem.getItem().getItemMeta().getDisplayName());
            }
        }
        if(newItem != null){
            GameItem item = GameItem.getItem(newItem.getID());
            if(item != null && item.isHandEquipable()) {
                player.equip(item, newItem);
                player.getPlayerObject().sendMessage(ChatColor.GREEN + "Equipped " + newItem.getItem().getItemMeta().getDisplayName());
            }
        }
    }

    private int getEquipmentSlot(ItemType type){
        //Armor
        if(type == ItemType.Helmet) return 39;
        if(type == ItemType.Chestplate) return 38;
        if(type == ItemType.Leggings) return 37;
        if(type == ItemType.Boots) return 36;
        //Equipment
        if(type == ItemType.Cape) return CAPE_SLOT;
        if(type == ItemType.Bracelet) return BRACELET_SLOT;
        if(type == ItemType.Ring) return RING_SLOT;
        if(type == ItemType.Headband) return HEADBAND_SLOT;
        if(type == ItemType.Necklace) return NECKLACE_SLOT;
        return 999;
    }

    private int getDragEquipmentSlot(ItemType type){
        if(type == ItemType.Helmet) return 5;
        if(type == ItemType.Chestplate) return 6;
        if(type == ItemType.Leggings) return 7;
        if(type == ItemType.Boots) return 8;
        //Equipment
        if(type == ItemType.Cape) return CAPE_SLOT;
        if(type == ItemType.Bracelet) return BRACELET_SLOT;
        if(type == ItemType.Ring) return RING_SLOT;
        if(type == ItemType.Headband) return HEADBAND_SLOT;
        if(type == ItemType.Necklace) return NECKLACE_SLOT;
        return 999;
    }

}
