package io.github.nmahdi.JunoCore.item.builder;

import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.item.JItem;
import io.github.nmahdi.JunoCore.item.NBTJItem;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import io.github.nmahdi.JunoCore.item.stats.ConsumableID;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import io.github.nmahdi.JunoCore.item.stats.StatContainer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import scala.collection.immutable.Stream;

import java.util.Map;
import java.util.UUID;

public class ItemBuilder{

    public static ItemStack buildItem(JItem item){
        return buildItem(item, 1);
    }

    public static ItemStack buildItem(JItem item, int amount){
        IItemBuilder builder;
        if(item.isSkull()){
            builder = new SkullItemBuilder(item.getTexture());
        }else {
            builder = new ItemStackBuilder(item.getMaterialType());
        }

        builder.setName(getItemDisplayName(item));
        builder.setAmount(amount);
        if(item.hasStats()) {
            setLore(builder, item.getStatContainer());
        }

        //Adds the JItem template lore
        addItemLore(builder, item);

        //Adds NBT tags based on the item template
        NBTJItem nbtItem = new NBTJItem(builder.build());
        nbtItem.addCompound("juno").setString("id", item.getId());
        if(item.hasStats()) {
            StatContainer container = item.getStatContainer();

            if(container.isDismantlable()) nbtItem.setDismantlable();
            if(container.isWeapon()) nbtItem.setWeaponType(container.getWeaponType());
            if(container.isEquipment()) nbtItem.setEquipmentSlot(container.getEquipmentSlot());
            if(container.hasUUID()) nbtItem.setUUID(UUID.randomUUID().toString());

            //Adds the stats to the Juno~Stats NBT Tag
            if (!container.getStats().isEmpty()) {
                for (Map.Entry<ItemStatID, String> stats : container.getStats().entrySet()) {
                    nbtItem.setStat(stats.getKey(), String.valueOf(stats.getValue()));
                }
            }
            //Adds the consumable stats to the Juno~Consumable NBT Tag
            if (container.isConsumable()) {
                for (Map.Entry<ConsumableID, Integer> con : container.getConsumableStats().entrySet()) {
                    nbtItem.setConsumableStat(con.getKey(), con.getValue());
                }
            }
        }
        return nbtItem.getItem();
    }

    public static ItemStack editItem(ItemStack stack, ItemStatID id, String value){
        NBTJItem nbtItem = new NBTJItem(stack);
        nbtItem.setStat(id, value);
        JItem template = JItem.getItemByID(nbtItem.getID());
        if(template == null) return new ItemStack(Material.AIR);
        IItemBuilder builder;
        if(stack.getType() == Material.PLAYER_HEAD){
            builder = new SkullItemBuilder(template.getTexture());
        }else{
            builder = new ItemStackBuilder(stack).clearLore();
        }
        //Loops through all stats and checks if the item has an NBT value matching it, if there is one it adds it to the lore
        for(ItemStatID stat : ItemStatID.values()){
            if(nbtItem.getStats().hasKey(stat.getID()))
                builder.addLore(ChatColor.translateAlternateColorCodes('&', getStatLore(stat.getDisplayName(), nbtItem.getStat(stat.getID()))));
        }
        //Adds the JItem template lore
        addItemLore(builder, template);
        NBTJItem temp = new NBTJItem(builder.build());
        temp.getJuno().mergeCompound(nbtItem.getJuno());
        return temp.getItem();
    }

    public static ItemStack buildCraftingMenuItem(Recipe recipe){
        IItemBuilder builder;
        if(recipe.getResult().getMaterialType() == Material.PLAYER_HEAD){
            builder = new SkullItemBuilder(recipe.getResult().getTexture());
        }else {
            builder = new ItemStackBuilder(recipe.getResult().getMaterialType());
        }
        builder.setName(getItemDisplayName(recipe.getResult()));

        if(recipe.getResult().hasStats()) {
            setLore(builder, recipe.getResult().getStatContainer());
        }
        //Adds the JItem template lore
        addItemLore(builder, recipe.getResult());
        builder.skipLore();
        for(Map.Entry<JItem, Integer> map : recipe.getItems().entrySet()){
            builder.addLore("&f- " + map.getValue() + " " + map.getKey().getDisplayName());
        }
        NBTItem nbtItem = new NBTItem(builder.build());
        nbtItem.addCompound("juno").setString("recipe", recipe.getId());
        return nbtItem.getItem();
    }

    private static String getItemDisplayName(JItem item){
        return "&" + item.getRarity().getColor() + "&l" + item.getDisplayName();
    }

    private static String getStatLore(String displayName, String value){
        String color = "a+";
        if(Integer.parseInt(value) < 0) color = "c";
        return "&7" + displayName + "&f: &" + color + value;
    }

    private static String getRarityLore(JItem item){
        return "&" + item.getRarity().getColor() + "&l" + item.getRarity();
    }

    private static void addItemLore(IItemBuilder builder, JItem item){
        if(item.hasStats()) builder.skipLore();
        builder.addLore(item.getLore());
        if(!item.getLore().isEmpty()) builder.skipLore();
        builder.addLore(getRarityLore(item));
    }

    private static void setLore(IItemBuilder builder, StatContainer container){
        //Loops through the stats of the JItem, and updates the lore according to the template
        if (!container.getStats().isEmpty()) {
            for(ItemStatID id : ItemStatID.values()){
                if(container.hasStat(id))
                    builder.addLore(ChatColor.translateAlternateColorCodes('&', getStatLore(id.getDisplayName(), container.getStat(id))));
            }
        }

        //Loops through the consumable stats of the JIte, and updates the lore according to the template
        if (container.isConsumable()) {
            for (ConsumableID id : ConsumableID.values()) {
                if(container.hasConsumableStat(id))
                    builder.addLore(ChatColor.translateAlternateColorCodes('&', getStatLore(id.getPlayerID().getDisplayName(), String.valueOf(container.getConsumableStat(id)))));
            }
        }
    }



}
