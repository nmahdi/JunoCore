package io.github.nmahdi.JunoCore.utils;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventoryHelper {

    public static boolean isAirOrNull(ItemStack stack){
        return stack == null || stack.getType().isAir();
    }

    public static boolean isFull(Inventory inventory){
        return inventory.firstEmpty() == -1;
    }

    public static void addItem(Player player, ItemStack itemStack){
        HashMap<Integer, ItemStack> itemsLeft = player.getInventory().addItem(itemStack);

    }

    public static boolean hasItems(Player player, Recipe recipe) {
        for (Map.Entry<GameItem, Integer> map : recipe.getRecipe().entrySet()) {
            boolean hasItem = false;
            int amount = 0;

            for (ItemStack stack : player.getInventory().getStorageContents()) {
                if (!isAirOrNull(stack)) {
                    NBTGameItem item = new NBTGameItem(stack);
                    if (item.hasJuno() && item.hasID()) {
                        if (item.getID().equals(map.getKey().getId())) {

                            amount += stack.getAmount();
                            if (amount >= map.getValue()) {
                                hasItem = true;
                            }
                        }
                    }
                }
            }

            if (!hasItem) {
                return false;
            }
        }
        return true;
    }

    public static void removeItem(Player player, GameItem gameItem, int amount){
        for(ItemStack stack : player.getInventory().getStorageContents()){
            if(!isAirOrNull(stack)){
                NBTGameItem item = new NBTGameItem(stack);
                if(item.hasJuno() && item.hasID()){
                    if(item.getID().equals(gameItem.getId())){
                        int preAmount = stack.getAmount();
                        int newAmount = Math.max(0, preAmount - amount);
                        amount = Math.max(0, amount - preAmount);
                        stack.setAmount(newAmount);
                        if(amount == 0){
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void removeItems(Player player, Recipe recipe){
        for(Map.Entry<GameItem, Integer> map : recipe.getRecipe().entrySet()) {
            removeItem(player, map.getKey(), map.getValue());
        }
    }

}
