package io.github.nmahdi.JunoCore.player.listeners;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.utils.JLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlayerInventoryListener implements Listener {

    private JCore main;
    private PlayerManager playerManager;
    private ItemManager itemManager;


    private final int OFFHAND_SLOT = 40;
    private final int OFFHAND_DRAG_SLOT = 45;

    public static final int HELMET_SLOT = 39;
    public static final int HELMET_DRAG_SLOT = 5;

    public static final int CHESTPLATE_SLOT = 38;
    public static final int CHESTPLATE_DRAG_SLOT = 6;

    public static final int LEGGINGS_SLOT = 37;
    public static final int LEGGINGS_DRAG_SLOT = 7;

    public static final int BOOTS_SLOT = 36;
    public static final int BOOTS_DRAG_SLOT = 8;

    public static final int CAPE_SLOT = 0;
    public static final int BRACELET_SLOT = 1;
    public static final int RING_SLOT = 2;
    public static final int HEADBAND_SLOT = 3;
    public static final int NECKLACE_SLOT = 4;

    public PlayerInventoryListener(JCore main) {
        this.main = main;
        this.playerManager = main.getPlayerManager();
        this.itemManager = main.getItemManager();
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e){
        if(e.getInventory().getType() != InventoryType.CRAFTING) return;
        if(!(e.getPlayer() instanceof Player)) return;
        CraftingInventory inventory = (CraftingInventory)e.getInventory();
        inventory.clear();

        GamePlayer player = playerManager.getPlayer((Player)e.getPlayer());

        if(player != null) {
            new BukkitRunnable(){

                @Override
                public void run() {
                    updateEquipment(player, inventory);
                }

            }.runTaskLater(main, 1);
        }
    }

    //TODO: Fix normal clicking an armor piece when the player already has one equipped.
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getWhoClicked().getGameMode() == GameMode.CREATIVE) return;

        //Disabling offhand
        if(e.getSlot() == OFFHAND_SLOT){
            e.setCancelled(true);
            return;
        }

        if (e.getClickedInventory() == null) return;
        if (!(e.getWhoClicked() instanceof Player)) return;

        boolean shift = false, numKey = false, drop = false;
        if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) shift = true;
        if (e.getClick().equals(ClickType.NUMBER_KEY)) numKey = true;
        if (e.getClick().equals(ClickType.DROP) || e.getClick().equals(ClickType.CONTROL_DROP)) drop = true;

        Player player = (Player) e.getWhoClicked();
        PlayerInventory playerInventory = player.getInventory();
        GamePlayer gamePlayer = playerManager.getPlayer(player);

        //For Shift Clicking
        if(shift) {
            if(InventoryHelper.isAirOrNull(e.getCurrentItem())) return;

            if (e.getSlotType() == InventoryType.SlotType.ARMOR) {

                if (!InventoryHelper.isFull(playerInventory)) {
                    equipArmor(gamePlayer, new NBTGameItem(e.getCurrentItem()), null);
                }

            } else {

                //Check if Item In Hand changed a tick after shift clicking
                if(InventoryHelper.isAirOrNull(playerInventory.getItemInMainHand())){

                    new BukkitRunnable(){

                        @Override
                        public void run() {
                            if(!InventoryHelper.isAirOrNull(playerInventory.getItemInMainHand())){
                                equipHand(gamePlayer, null, new NBTGameItem(playerInventory.getItemInMainHand()));
                            }

                        }

                    }.runTaskLater(main, 1);

                }

                //For equipment slots
                if (e.getClickedInventory().getType() == InventoryType.CRAFTING) {

                    if (!InventoryHelper.isFull(playerInventory)) {
                        equipEquipment(gamePlayer, new NBTGameItem(e.getCurrentItem()), null);
                        updateEquipment(gamePlayer, e.getClickedInventory());
                    }

                } else if (e.getClickedInventory().getType() == InventoryType.PLAYER) {

                    if(e.getSlot() == playerInventory.getHeldItemSlot()){

                        ItemStack hand = playerInventory.getItemInMainHand().clone();

                        new BukkitRunnable(){

                            @Override
                            public void run() {
                                if(!hand.equals(playerInventory.getItemInMainHand())){
                                    equipHand(gamePlayer, new NBTGameItem(hand), InventoryHelper.isAirOrNull(playerInventory.getItemInMainHand()) ? null : new NBTGameItem(playerInventory.getItemInMainHand()));
                                }

                            }

                        }.runTaskLater(main, 1);


                    }else {

                        NBTGameItem nbtItem = new NBTGameItem(e.getCurrentItem());
                        if (!nbtItem.hasID()) return;

                        GameItem item = itemManager.getItem(nbtItem.getID());
                        if (item == null) return;

                        int slot = getEquipmentSlot(item.getItemType());

                        //Checks if a player has his own inventory open
                        if (e.getView().getTopInventory().getType() == InventoryType.CRAFTING) {

                            ItemStack current = e.getCurrentItem();

                            if (ItemType.isEquipment(item)) {

                                CraftingInventory craftingInventory = (CraftingInventory) e.getView().getTopInventory();

                                if(InventoryHelper.isAirOrNull(craftingInventory.getItem(slot))){
                                    e.setCancelled(true);

                                    equipEquipment(gamePlayer, null, new NBTGameItem(e.getCurrentItem()));

                                    e.setCurrentItem(null);
                                    updateEquipment(gamePlayer, craftingInventory);
                                }

                            }

                            if(ItemType.isArmor(item)){

                                if(InventoryHelper.isAirOrNull(playerInventory.getItem(slot))){
                                    e.setCancelled(true);

                                    equipArmor(gamePlayer, null, new NBTGameItem(e.getCurrentItem()));

                                    e.setCurrentItem(null);
                                    playerInventory.setItem(slot, current);
                                }

                            }

                        }
                    }

                }
            }

        }else if(numKey){
            boolean move = false;

            ItemStack current = e.getCurrentItem();
            ItemStack hotbar = playerInventory.getItem(e.getHotbarButton());

            if(e.getSlotType() == InventoryType.SlotType.ARMOR){

                if(!InventoryHelper.isAirOrNull(hotbar)) {
                    NBTGameItem nbtHotbar = new NBTGameItem(hotbar);
                    if(!nbtHotbar.hasID()) return;

                    GameItem item = itemManager.getItem(nbtHotbar.getID());
                    if(item == null || !ItemType.isArmor(item) || e.getSlot() != getEquipmentSlot(item.getItemType())){
                        e.setCancelled(true);
                        return;
                    }

                }

                move = true;
                equipArmor(gamePlayer, InventoryHelper.isAirOrNull(current) ? null : new NBTGameItem(current), InventoryHelper.isAirOrNull(hotbar) ? null : new NBTGameItem(hotbar));

            }else {
                if (e.getClickedInventory().getType() == InventoryType.CRAFTING) {

                    if(!InventoryHelper.isAirOrNull(hotbar)) {
                        NBTGameItem nbtHotbar = new NBTGameItem(hotbar);
                        if(!nbtHotbar.hasID()) return;

                        GameItem item = itemManager.getItem(nbtHotbar.getID());
                        if(item == null) return;
                        if(!ItemType.isEquipment(item) || e.getSlot() != getEquipmentSlot(item.getItemType())){
                            e.setCancelled(true);
                            return;
                        }

                    }

                    move = true;
                    equipEquipment(gamePlayer, InventoryHelper.isAirOrNull(current) ? null : new NBTGameItem(current), InventoryHelper.isAirOrNull(hotbar) ? null : new NBTGameItem(hotbar));

                    updateEquipment(gamePlayer, e.getClickedInventory());
                }else if(e.getClickedInventory().getType() == InventoryType.PLAYER){

                    if(e.getSlot() == playerInventory.getHeldItemSlot()){

                        move = true;
                        equipHand(gamePlayer, InventoryHelper.isAirOrNull(current) ? null : new NBTGameItem(current), InventoryHelper.isAirOrNull(hotbar) ? null : new NBTGameItem(hotbar));

                    }else if(e.getHotbarButton() == playerInventory.getHeldItemSlot()){

                        move = true;
                        equipHand(gamePlayer, InventoryHelper.isAirOrNull(hotbar) ? null : new NBTGameItem(hotbar), InventoryHelper.isAirOrNull(current) ? null : new NBTGameItem(current));

                    }

                }
            }

            if(move){
                e.setCancelled(true);
                e.setCurrentItem(hotbar);
                playerInventory.setItem(e.getHotbarButton(), current);
            }

        }else if(drop){
            if(e.getCurrentItem() == null) return;

            if(e.getSlotType() == InventoryType.SlotType.ARMOR){

                equipArmor(gamePlayer, new NBTGameItem(e.getCurrentItem()),null);

            }else{
                if(e.getClickedInventory().getType() == InventoryType.CRAFTING){

                    equipEquipment(gamePlayer, new NBTGameItem(e.getCurrentItem()),null);
                    updateEquipment(gamePlayer, e.getClickedInventory());

                }else if(e.getClickedInventory().getType() == InventoryType.PLAYER){

                    if(e.getSlot() == playerInventory.getHeldItemSlot()){

                        equipHand(gamePlayer, new NBTGameItem(e.getCurrentItem()), null);

                    }

                }
            }

        }else{
            ItemStack current = e.getCurrentItem();
            ItemStack cursor = e.getCursor();

            if(e.getSlotType() == InventoryType.SlotType.ARMOR){

                if(!InventoryHelper.isAirOrNull(cursor)){

                    NBTGameItem nbtItem = new NBTGameItem(cursor);
                    if(!nbtItem.hasID()) return;

                    GameItem gameItem = itemManager.getItem(nbtItem.getID());
                    if(gameItem == null || !ItemType.isArmor(gameItem)) return;

                    if(getEquipmentSlot(gameItem.getItemType()) != e.getSlot()) return;

                    //Overriding helmet clicking (to allow block type helemts)
                    if(gameItem.getItemType() == ItemType.Helmet){
                        e.setCancelled(true);
                        e.setCurrentItem(cursor);
                        e.getWhoClicked().setItemOnCursor(current);
                    }

                }

                equipArmor(gamePlayer, InventoryHelper.isAirOrNull(current) ? null : new NBTGameItem(current), InventoryHelper.isAirOrNull(cursor) ? null : new NBTGameItem(cursor));
            }else{

                if(e.getClickedInventory().getType() == InventoryType.CRAFTING){
                    if(!InventoryHelper.isAirOrNull(cursor)){

                        NBTGameItem nbtItem = new NBTGameItem(cursor);
                        if(!nbtItem.hasID()) return;

                        GameItem gameItem = itemManager.getItem(nbtItem.getID());
                        if(gameItem == null || !ItemType.isEquipment(gameItem) || getEquipmentSlot(gameItem.getItemType()) != e.getSlot()){
                            e.setCancelled(true);
                            return;
                        }

                    }

                    //Overrides clicking on crafting result
                    if(e.getSlot() == CAPE_SLOT){
                        e.setCancelled(true);
                        e.setCurrentItem(cursor);
                        e.getWhoClicked().setItemOnCursor(current);
                    }

                    equipEquipment(gamePlayer, InventoryHelper.isAirOrNull(current) ? null : new NBTGameItem(current), InventoryHelper.isAirOrNull(cursor) ? null : new NBTGameItem(cursor));

                    updateEquipment(gamePlayer, e.getClickedInventory());
                }else if(e.getClickedInventory().getType() == InventoryType.PLAYER){

                    if(e.getSlot() == playerInventory.getHeldItemSlot()){
                        ItemStack itemStack = playerInventory.getItemInMainHand().clone();

                        equipHand(gamePlayer, InventoryHelper.isAirOrNull(current) ? null : new NBTGameItem(current), InventoryHelper.isAirOrNull(cursor) ? null : new NBTGameItem(cursor));
                    }

                }

            }
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent e) {
        if (InventoryHelper.isAirOrNull(e.getItem())) return;

        NBTGameItem nbtItem = new NBTGameItem(e.getItem());
        if (!nbtItem.hasID()) return;

        GameItem item = itemManager.getItem(nbtItem.getID());
        if (item == null) return;

        GamePlayer gamePlayer = playerManager.getPlayer(e.getPlayer());
        int equipmentSlot = getEquipmentSlot(item.getItemType());

        if (ItemType.isArmor(item)) {

            if (InventoryHelper.isAirOrNull(e.getPlayer().getInventory().getItem(equipmentSlot))) {

                e.setCancelled(true);
                e.getPlayer().getInventory().setItem(equipmentSlot, e.getItem());

                equipArmor(gamePlayer, null, new NBTGameItem(e.getItem()));
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                    @Override
                    public void run() {
                        e.getPlayer().getInventory().setItemInMainHand(null);
                    }
                }, 1);
            }
        }

        if (ItemType.isEquipment(item)) {

            if (e.getPlayer().getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING) {
                CraftingInventory craftingInventory = (CraftingInventory) e.getPlayer().getOpenInventory().getTopInventory();

                if (InventoryHelper.isAirOrNull(craftingInventory.getItem(equipmentSlot))) {
                    craftingInventory.setItem(equipmentSlot, e.getItem());

                    equipEquipment(gamePlayer, null, new NBTGameItem(e.getItem()));

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
                        @Override
                        public void run() {
                            e.getPlayer().getInventory().setItemInMainHand(null);
                        }
                    }, 1);
                }

            }

        }
    }

    //TODO: FIX THIS SHIT
    @EventHandler
    public void onInvDrag(InventoryDragEvent e){
        if(InventoryHelper.isAirOrNull(e.getOldCursor())) return;
        if(e.getRawSlots().isEmpty()) return;
        Set<Integer> slots = e.getRawSlots();

        //Shield equipmentslot check
        if(slots.contains(OFFHAND_DRAG_SLOT)){
            e.setCancelled(true);
            return;
        }

        GameItem item = itemManager.getItem(new NBTGameItem(e.getOldCursor()).getID());
        if(item == null){
            e.setCancelled(true);
            return;
        }

        GamePlayer player = playerManager.getPlayer((Player)e.getWhoClicked());

        int equipmentSlot = getDragEquipmentSlot(item.getItemType());

        int firstSlot = slots.stream().findFirst().orElse(0);
        int dragDiff = 36;

        //For armor & equipment
        if(e.getView().getTopInventory().getType() == InventoryType.CRAFTING) {


                if (slots.contains(HELMET_DRAG_SLOT) || slots.contains(CHESTPLATE_DRAG_SLOT) || slots.contains(LEGGINGS_DRAG_SLOT) || slots.contains(BOOTS_DRAG_SLOT)) {
                    if(ItemType.isArmor(item) && slots.contains(equipmentSlot)) {

                        //Overriding to allow block type helmets
                        if(item.getItemType() == ItemType.Helmet){
                            e.setCancelled(true);
                            e.getInventory().setItem(HELMET_DRAG_SLOT, e.getOldCursor());
                            e.getWhoClicked().setItemOnCursor(null);
                        }

                        equipArmor(player, null, new NBTGameItem(e.getOldCursor()));
                        return;
                    }
                    e.setCancelled(true);
                    return;
                }

                if (slots.contains(CAPE_SLOT) || slots.contains(RING_SLOT) || slots.contains(BRACELET_SLOT) || slots.contains(HEADBAND_SLOT) || slots.contains(NECKLACE_SLOT)) {
                    if (ItemType.isEquipment(item) && slots.contains(equipmentSlot)) {

                        //Setting cape slot (crafting result slot) to cursor
                        if(item.getItemType() == ItemType.Cape){
                            e.setCancelled(true);
                            e.getInventory().setItem(CAPE_SLOT, e.getOldCursor());
                            e.getWhoClicked().setItemOnCursor(null);
                        }

                        equipEquipment(player, null, new NBTGameItem(e.getOldCursor()));
                        updateEquipment(player, e.getView().getTopInventory());
                        return;
                    }
                    e.setCancelled(true);
                    return;
                }
        }

        if(firstSlot == player.getInventory().getHeldItemSlot()+dragDiff){
            equipHand(player, null, new NBTGameItem(e.getOldCursor()));
        }
    }

    @EventHandler
    public void onItemPlace(BlockPlaceEvent e){
        if(InventoryHelper.isAirOrNull(e.getItemInHand())) return;
        GameItem item = itemManager.getItem(new NBTGameItem(e.getItemInHand()).getID());
        if(item == null) return;
        if(item.getItemType().getCatagory() != ItemType.Catagory.Misc) e.setCancelled(true);
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
            GameItem item = itemManager.getItem(oldItem.getID());
            if(item != null && ItemType.isEquipment(item)) {
                player.unequip((StatItem) item, oldItem);
                player.equipment.put(getEquipmentSlot(item.getItemType()), null);
                player.getPlayerObject().sendMessage(ChatColor.RED + "Unequipped: " + oldItem.getItem().getItemMeta().getDisplayName());
            }
        }
        if (newItem != null) {
            GameItem item = itemManager.getItem(newItem.getID());
            if(item != null && ItemType.isEquipment(item)) {
                player.equip((StatItem) item, newItem);
                player.equipment.put(getEquipmentSlot(item.getItemType()), newItem.getItem());
                player.getPlayerObject().sendMessage(ChatColor.GREEN + "Equipped " + newItem.getItem().getItemMeta().getDisplayName());
            }
        }
    }

    private void equipArmor(GamePlayer player, NBTGameItem oldItem, NBTGameItem newItem){
        if(oldItem != null){
            GameItem item = itemManager.getItem(oldItem.getID());
            if(item != null && ItemType.isArmor(item)) {
                player.unequip((StatItem) item, oldItem);
                player.getPlayerObject().sendMessage(ChatColor.RED + "Unequipped: " + oldItem.getItem().getItemMeta().getDisplayName());
            }
        }
        if (newItem != null) {
            GameItem item = itemManager.getItem(newItem.getID());
            if(item != null && ItemType.isArmor(item)) {
                player.equip((StatItem) item, newItem);
                player.getPlayerObject().sendMessage(ChatColor.GREEN + "Equipped " + newItem.getItem().getItemMeta().getDisplayName());
            }
        }
    }

    private void equipHand(GamePlayer player, NBTGameItem oldItem, NBTGameItem newItem){
        if(oldItem != null){
            GameItem item = itemManager.getItem(oldItem.getID());
            if(item != null && ItemType.isHandEquipable(item)) {
                player.unequip((StatItem) item, oldItem);
                player.getPlayerObject().sendMessage(ChatColor.RED + "Unequipped: " + oldItem.getItem().getItemMeta().getDisplayName());
            }
        }
        if(newItem != null){
            GameItem item = itemManager.getItem(newItem.getID());
            if(item != null && ItemType.isHandEquipable(item)) {
                player.equip((StatItem) item, newItem);
                player.getPlayerObject().sendMessage(ChatColor.GREEN + "Equipped " + newItem.getItem().getItemMeta().getDisplayName());
            }
        }
    }

    private void updateEquipment(GamePlayer gamePlayer, Inventory craftingInventory){
        new BukkitRunnable(){

            @Override
            public void run() {
                for(Map.Entry<Integer, ItemStack> items : gamePlayer.equipment.entrySet()){
                    if(items.getKey() != CAPE_SLOT) {
                        craftingInventory.setItem(items.getKey(), items.getValue());
                    }
                }
                craftingInventory.setItem(CAPE_SLOT, gamePlayer.equipment.get(CAPE_SLOT));
            }
        }.runTaskLater(main, 1);
    }

    private int getEquipmentSlot(ItemType type){
        //Armor
        if(type == ItemType.Helmet) return HELMET_SLOT;
        if(type == ItemType.Chestplate) return CHESTPLATE_SLOT;
        if(type == ItemType.Leggings) return LEGGINGS_SLOT;
        if(type == ItemType.Boots) return BOOTS_SLOT;
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
