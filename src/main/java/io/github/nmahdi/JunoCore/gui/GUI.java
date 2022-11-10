package io.github.nmahdi.JunoCore.gui;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import scala.Int;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class GUI implements Listener {

    public final ItemStack EMPTY = new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build();
    public final ItemStack BACK = new ItemStackBuilder(Material.ARROW).setName("&fBack").build();

    protected String name;
    protected int size;
    protected GUI previousMenu;

    public GUI(JCore main, String name, int size, GUI previousMenu){
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.size = size;
        this.previousMenu = previousMenu;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public abstract void onInvClick(InventoryClickEvent e);

    public abstract void openInventory(Player player);

    public boolean openPrevious(HumanEntity player, ItemStack currentItem){
        if(currentItem.isSimilar(BACK)){
            previousMenu.openInventory((Player)player);
            return true;
        }
        return false;
    }

    protected void insertBack(Inventory inventory){
        inventory.setItem(45, BACK);
    }

    protected void insertFiller(Inventory inventory, int... skippedSlots){
        ArrayList<Integer> skipped = new ArrayList<>();
        for(int s : skippedSlots){
            skipped.add(s);
        }
        for (int i = 0; i < getSize(); i++) {
            if(!skipped.contains(i)) {
                if (inventory.getItem(i) == null) inventory.setItem(i, EMPTY);
            }
        }
    }

    protected void insertFiller(Inventory inventory){
        for (int i = 0; i < getSize(); i++) {
            if (inventory.getItem(i) == null) inventory.setItem(i, EMPTY);
        }
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public GUI getPreviousMenu() {
        return previousMenu;
    }

}
