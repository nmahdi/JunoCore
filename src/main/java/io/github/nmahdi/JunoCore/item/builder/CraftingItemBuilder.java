package io.github.nmahdi.JunoCore.item.builder;

import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.item.JItem;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import io.github.nmahdi.JunoCore.item.stats.Stat;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CraftingItemBuilder {

    private ItemStack itemStack;
    private NBTItem nbtItem;
    private Recipe recipe;

    public CraftingItemBuilder(Recipe recipe){
        this.recipe = recipe;
        newBuilder(recipe);
    }

    private void newBuilder(Recipe recipe){
        ItemStackBuilder builder = new ItemStackBuilder(recipe.getResult().getMaterialType()).setName(getItemDisplayName());
        //Loops through the stats of the JItem, and updates the lore according to the template
        if(!recipe.getResult().getStats().isEmpty()){
            for(ItemStatID s : ItemStatID.values()) {
                for (Stat stat : recipe.getResult().getStats()) {
                    if (stat.getID().equals(s)) {
                        if (!stat.getID().isHidden())
                            builder.addLore(ChatColor.translateAlternateColorCodes('&', getStatLore(stat)));
                    }
                }
            }
        }
        //Adds the JItem template lore
        addLore(builder);
        builder.skipLore();
        for(Map.Entry<JItem, Integer> map : recipe.getItems().entrySet()){
            builder.addLore("&f- " + map.getValue() + " " + map.getKey().getDisplayName());
        }
        this.itemStack = builder.build();
        this.nbtItem = new NBTItem(itemStack);
        this.nbtItem.addCompound("juno").setString("recipe", recipe.getId());
    }

    /*
     * Helper Methods
     *
     */

    public ItemStack getStack(){
        return nbtItem.getItem();
    }

    private String getItemDisplayName(){
        return "&" + recipe.getResult().getRarity().getColor() + "&l" + recipe.getResult().getDisplayName();
    }

    private String getRarityLore(){
        return "&" + recipe.getResult().getRarity().getColor() + "&l" + recipe.getResult().getRarity();
    }

    private String getStatLore(Stat stat){
        return getStatLore(stat.getID().getDisplayName(), stat.getValue());
    }

    private String getStatLore(String displayName, String value){
        String color = "a+";
        if(Integer.parseInt(value) < 0) color = "c";
        return "&7" + displayName + "&f: &" + color + value;
    }

    private void addLore(ItemStackBuilder builder){
        if(!recipe.getResult().getStats().isEmpty()) builder.skipLore();
        builder.addLore(recipe.getResult().getLore());
        if(!recipe.getResult().getLore().isEmpty()) builder.skipLore();
        builder.addLore(getRarityLore());
    }
}
