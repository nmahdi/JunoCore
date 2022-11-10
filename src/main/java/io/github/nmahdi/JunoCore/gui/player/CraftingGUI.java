package io.github.nmahdi.JunoCore.gui.player;

import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.gui.GUI;
import io.github.nmahdi.JunoCore.item.JItem;
import io.github.nmahdi.JunoCore.item.NBTJItem;
import io.github.nmahdi.JunoCore.item.builder.ItemBuilder;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CraftingGUI extends GUI {

    public CraftingGUI(JCore main, PlayerMenuGUI playerMenuGUI) {
        super(main, "&7Crafting", 54, playerMenuGUI);
    }


    //Needs fixing
    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if(e.getCurrentItem() == null) return;
        if(e.getView().getTitle().equals(getName())){
            e.setCancelled(true);
            if(openPrevious(e.getWhoClicked(), e.getCurrentItem())) return;
            if(!e.getCurrentItem().hasItemMeta()) return;
            NBTJItem item = new NBTJItem(e.getCurrentItem());
            if(!item.hasJuno() && !item.isRecipeItem()) return;
            Recipe recipe = Recipe.getRecipe(item.getRecipe());
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

    @Override
    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, getSize(), getName());

        int index = 10;
        for(Recipe recipe : Recipe.values()){
            inventory.setItem(index, ItemBuilder.buildCraftingMenuItem(recipe));
            index++;
        }

        insertBack(inventory);
        insertFiller(inventory);

        player.openInventory(inventory);
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


}
