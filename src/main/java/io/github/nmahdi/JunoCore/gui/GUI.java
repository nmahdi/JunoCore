package io.github.nmahdi.JunoCore.gui;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class GUI implements Listener {

    public final ItemStack EMPTY = new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE).blankName().build();
    public final ItemStack BACK = new ItemStackBuilder(Material.ARROW).setName("Back", TextColors.WHITE, false).build();
    public final ItemStack OUTPUT = new ItemStackBuilder(Material.BARRIER).blankName().build();

    protected final Component CLICK_TO_VIEW = Component.text("Click to view!").color(TextColors.YELLOW);

    protected JCore main;
    protected String name;
    protected int size;
    protected GUI previousMenu;

    public GUI(JCore main, String name, int size, GUI previousMenu){
        this.main = main;
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.size = size;
        this.previousMenu = previousMenu;
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    public abstract void onInvClick(InventoryClickEvent e);

    public abstract void openInventory(Player player);
    public abstract void onInvClose(Inventory inventory, Player player);

    @EventHandler
    public void invClick(InventoryClickEvent e){
        if(InventoryHelper.isAirOrNull(e.getCurrentItem())) return;
        if(!e.getView().getTitle().equals(getName())) return;
        if(e.getCurrentItem().isSimilar(BACK)){
            previousMenu.openInventory((Player)e.getWhoClicked());
            e.setCancelled(true);
            return;
        }
        onInvClick(e);
    }

    @EventHandler
    public void invClose(InventoryCloseEvent e){
        if(!e.getView().getTitle().equalsIgnoreCase(getName())) return;
        onInvClose(e.getInventory(), (Player)e.getPlayer());
    }

    protected Inventory createInventory(){
        return Bukkit.createInventory(null, getSize(), getName());
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
