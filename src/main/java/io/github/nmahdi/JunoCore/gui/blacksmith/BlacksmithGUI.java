package io.github.nmahdi.JunoCore.gui.blacksmith;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.gui.NPCGUI;
import io.github.nmahdi.JunoCore.item.JItem;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.item.builder.SkullItemBuilder;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlacksmithGUI extends NPCGUI {

    ItemStack dismantle = new ItemStackBuilder(Material.BLAZE_POWDER).setName("&cDismantle").skipLore().addLore("&7Dismantle old equipment", "&7break open geodes.").build();
    ItemStack upgrade = new ItemStackBuilder(Material.ANVIL).setName("&aUpgrade").skipLore().addLore("&7Upgrade equipment and weapons!").build();

    DismantleGUI dismantleGUI;
    UpgradeGUI upgradeGUI;

    public BlacksmithGUI(JCore main) {
        super(main, "&7Blacksmith", 54, null, "&7Blacksmith", "&7Dismantle and upgrade items at the blacksmith!");
        dismantleGUI = new DismantleGUI(main, this);
        upgradeGUI = new UpgradeGUI(main, this);
    }

    @EventHandler
    public void onNPCClick(NPCRightClickEvent e){
        if(e.getNPC().getName().equalsIgnoreCase(npcName)) openInventory(e.getClicker());
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getView().getTitle().equals(getName())){
            e.setCancelled(true);
            if(e.getCurrentItem().isSimilar(dismantle)) dismantleGUI.openInventory((Player)e.getWhoClicked());
            if(e.getCurrentItem().isSimilar(upgrade)) upgradeGUI.openInventory((Player)e.getWhoClicked());
        }
    }

    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getName());

        inventory.setItem(20, dismantle);
        inventory.setItem(21, upgrade);

        addNpcSkull(inventory);
        insertFiller(inventory);

        player.openInventory(inventory);
    }

}
