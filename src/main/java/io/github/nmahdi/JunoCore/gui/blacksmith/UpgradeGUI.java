package io.github.nmahdi.JunoCore.gui.blacksmith;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.gui.NPCGUI;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class UpgradeGUI extends NPCGUI {

    private int insertSlot = 22;
    ItemStack upgrade = new ItemStackBuilder(Material.ANVIL).setName("&aUpgrade").build();

    public UpgradeGUI(JCore main, BlacksmithGUI blacksmithGUI) {
        super(main, "&aUpgrade", 54, blacksmithGUI, blacksmithGUI.getName(), blacksmithGUI.getSkullLore());
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;
        if(e.getView().getTitle().equals(getName())){
            e.setCancelled(true);
            if(openPrevious(e.getWhoClicked(), e.getCurrentItem())){
                return;
            }
        }
    }

    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getName());

        addNpcSkull(inventory);
        insertBack(inventory);
        insertFiller(inventory);

        player.openInventory(inventory);
    }

}
