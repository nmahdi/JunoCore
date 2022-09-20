package com.junocore.item.listeners;

import com.junocore.hunter.HunterManager;
import com.junocore.item.JItemManager;
import com.junocore.item.builder.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HunterPhoneListener implements Listener {

    private JItemManager itemManager;
    private HunterManager hunterManager;
    private String hmName = ChatColor.GRAY + "Hunter Menu";
    private ItemStack ACCEPT = new ItemStackBuilder(Material.LIME_WOOL).setName("&aAccept").build();
    private ItemStack INPROGRESS = new ItemStackBuilder(Material.PAPER).setName("&7In Progress").build();

    public HunterPhoneListener(JItemManager itemManager, HunterManager hunterManager){
        this.itemManager = itemManager;
        this.hunterManager = hunterManager;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()){
                if(itemManager.isJunoItem(e.getPlayer().getInventory().getItemInMainHand()) && itemManager.getItemID(e.getPlayer().getInventory().getItemInMainHand()).equals("hunter_phone")){
                    openHunterMenu(e.getPlayer());
                }
            }
        }
    }

/*    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(e.getInventory() != null && e.getInventory().){
            if(e.getCurrentItem().equals(ACCEPT)){
                hunterManager.startQuest(userManager.getUser(e.getWhoClicked().getUniqueId().toString()), HunterManager.HunterQuestID.Zombie);
                e.setCancelled(true);
            }
        }
    } */

    public void openHunterMenu(Player player){
        Inventory inv = Bukkit.createInventory(null, 54, hmName);

        if(hunterManager.hasQuest(player)){
            inv.setItem(15, INPROGRESS);
        }else {
            inv.setItem(15, ACCEPT);
        }

        player.openInventory(inv);
    }

}
