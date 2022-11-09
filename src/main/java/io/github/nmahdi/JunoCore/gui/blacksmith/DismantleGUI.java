package io.github.nmahdi.JunoCore.gui.blacksmith;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.item.JItem;
import io.github.nmahdi.JunoCore.item.NBTJItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DismantleGUI extends GUI {

    private int insertSlot = 22;
    ItemStack accept = new ItemStackBuilder(Material.BLAZE_POWDER).setName("&cConfirm").build();

    public DismantleGUI(JCore main, BlacksmithGUI blacksmithGUI) {
        super(main,"&cDismantle", 54, blacksmithGUI);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getView().getTitle().equals(getName())){
            openPrevious(e.getWhoClicked(), e.getCurrentItem());
            if(e.getCurrentItem().isSimilar(accept)){
                if(e.getInventory().getItem(insertSlot) != null && !e.getInventory().getItem(insertSlot).getType().isAir()){
                    NBTJItem item = new NBTJItem(e.getInventory().getItem(insertSlot));
                    if(item.canDismantle()){
                        e.getInventory().setItem(insertSlot, ItemBuilder.buildItem(JItem.CompactedDiamond));
                        e.getWhoClicked().getWorld().playSound(e.getWhoClicked(), Sound.BLOCK_ANVIL_USE, 1f, 1f);
                    }
                }
            }
            if(e.getClickedInventory().getType() != InventoryType.PLAYER){
                if(e.getSlot() != insertSlot) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getName());

        inventory.setItem(31, accept);

        insertBack(inventory);
        insertFiller(inventory, insertSlot);

        player.openInventory(inventory);
    }

}
