package io.github.nmahdi.JunoCore.item.crafting;

import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.PlayerMenuGUI;
import io.github.nmahdi.JunoCore.item.JItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CraftingManager implements Listener {

    private final String MENU_NAME = ChatColor.translateAlternateColorCodes('&', "&bCrafting");

    private PlayerMenuGUI playerGUI;

    public CraftingManager(JCore main, PlayerMenuGUI playerGUI){
        main.getServer().getPluginManager().registerEvents(this, main);
        this.playerGUI = playerGUI;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null || e.getCurrentItem().getType().isAir()) return;
        if(e.getClickedInventory() == null) return;
        if(e.getView().getTitle().equals(MENU_NAME)){
            e.setCancelled(true);
            if(!e.getCurrentItem().hasItemMeta()) return;
            NBTItem item = new NBTItem(e.getCurrentItem());
            if(!item.hasKey("juno") && !item.getCompound("juno").hasKey("recipe")) return;
            Recipe recipe = getRecipe(item.getCompound("juno").getString("recipe"));
            if(recipe == null) return;
            if(hasItems((Player)e.getWhoClicked(), recipe)){
                removeItems((Player)e.getWhoClicked(), recipe);
                e.getWhoClicked().getInventory().addItem(ItemBuilder.buildItem(recipe.getResult()));
                e.getWhoClicked().sendMessage(ChatColor.GREEN + "Successfully crafted " + recipe.getResult().getDisplayName() + ".");
                return;
            }
            e.getWhoClicked().sendMessage(ChatColor.RED + "You don't have the required items!");
        }
    }

    public void openCraftingMenu(Player player){
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', MENU_NAME));

        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, playerGUI.EMPTY);
        }
        int index = 10;
        for(Recipe recipe : Recipe.values()){
            inv.setItem(index, ItemBuilder.buildCraftingMenuItem(recipe));
            index++;
        }
        player.openInventory(inv);
    }

    private boolean hasItems(Player player, Recipe recipe){
        for(Map.Entry<JItem, Integer> map : recipe.getItems().entrySet()) {
            boolean hasItem = false;
            int amount = map.getValue();
            for (ItemStack stack : player.getInventory().getStorageContents()) {
                if (stack != null && !stack.getType().isAir()) {
                    if (stack.hasItemMeta()) {
                        NBTItem item = new NBTItem(stack);
                        if (item.hasKey("juno") && item.getCompound("juno").hasKey("id")) {
                            if (item.getCompound("juno").getString("id").equals(map.getKey().getId())) {
                                amount-=stack.getAmount();
                                if (stack.getAmount() >= map.getValue() || amount <= 0) {
                                    hasItem = true;
                                }
                            }
                        }
                    }
                }
            }
            if(!hasItem){
                return false;
            }
        }
        return true;
    }

    private void removeItems(Player player, Recipe recipe){
        for(Map.Entry<JItem, Integer> map : recipe.getItems().entrySet()) {
            for (ItemStack stack : player.getInventory().getStorageContents()) {
                if (stack != null && !stack.getType().isAir()) {
                    if (stack.hasItemMeta()) {
                        NBTItem item = new NBTItem(stack);
                        if (item.hasKey("juno") && item.getCompound("juno").hasKey("id")) {
                            if (item.getCompound("juno").getString("id").equals(map.getKey().getId())) {
                                if (stack.getAmount() > map.getValue()) {
                                    stack.setAmount(stack.getAmount() - map.getValue());
                                } else {
                                    player.getInventory().remove(stack);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Recipe getRecipe(String id){
        for(Recipe recipe : Recipe.values()){
            if(recipe.getId().equals(id)) return recipe;
        }
        return null;
    }

}
