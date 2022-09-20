package com.junocore.item.builder;

import com.junocore.item.JItem;
import com.junocore.item.stats.Stat;
import com.junocore.item.stats.ItemStatID;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class NBTItemStackBuilder {

    private ItemStack itemStack;
    private NBTItem nbtItem;
    private JItem jItem;

    /**
     *  Used on pre-existing Juno Core NBT items.
     *
     * @param item
     */

    public NBTItemStackBuilder(NBTItem item, JItem jItem){
        this.nbtItem = item;
        this.itemStack = item.getItem();
        this.jItem = jItem;
    }

    public NBTItemStackBuilder setStat(ItemStatID id, String stat) {
        this.nbtItem.getCompound("juno").getCompound("stats").setString(id.getID(), stat);
        return this;
    }

    /**
     * Edit's an item stack based on the constructor's JItem
     *
     * @param amount
     * @return
     */

    public ItemStack buildStack(int amount){
        ItemStackBuilder builder = new ItemStackBuilder(this.itemStack).setName(getItemDisplayName()).setAmount(amount);
        builder.clearLore();
        //Loops through all stats and checks if the item has an NBT value matching it, if there is one it adds it to the lore
        for(ItemStatID stat : ItemStatID.values()){
            if(nbtItem.getCompound("juno").getCompound("stats").hasKey(stat.getID()))
                builder.addLore(ChatColor.translateAlternateColorCodes('&', getStatLore(stat.getDisplayName(), nbtItem.getCompound("juno").getCompound("stats").getString(stat.getID()))));
        }
        //Adds the JItem template lore
        addLore(builder);
        NBTItem temp = new NBTItem(builder.build());
        temp.getOrCreateCompound("juno").mergeCompound(nbtItem.getCompound("juno"));
        return temp.getItem();
    }

    public ItemStack buildStack(){
        return this.buildStack(1);
    }

    /**
     *
     * Used for newly created Itemstacks
     *
     * @param item
     */

    public NBTItemStackBuilder(JItem item) {
        this.jItem = item;
        newBuilder(item, 1);
    }

    public NBTItemStackBuilder(JItem item, int amount){
        this.jItem = item;
        newBuilder(item, amount);
    }

    private void newBuilder(JItem item, int amount){
        ItemStackBuilder builder = new ItemStackBuilder(item.getMaterialType()).setName(getItemDisplayName()).setAmount(amount);
        //Loops through the stats of the JItem, and updates the lore according to the template
        if(!item.getStats().isEmpty()){
            for(ItemStatID s : ItemStatID.values()) {
                for (Stat stat : item.getStats()) {
                    if (stat.getID().equals(s)) {
                        if (!stat.getID().isHidden())
                            builder.addLore(ChatColor.translateAlternateColorCodes('&', getStatLore(stat)));
                    }
                }
            }
        }
        //Adds the JItem template lore
        addLore(builder);
        this.itemStack = builder.build();
        this.nbtItem = new NBTItem(itemStack);
        this.nbtItem.addCompound("juno").setString("id", item.getId());
        //Adds the stats to the Juno~Stats NBT Tag
        if(!item.getStats().isEmpty()) {
            for (Stat stat : item.getStats()) {
                if (stat.getID().isHidden()) {
                    this.nbtItem.getCompound("juno").addCompound("hidden-stats").setString(stat.getID().getID(), stat.getValue());
                } else {
                    this.nbtItem.getCompound("juno").addCompound("stats").setString(stat.getID().getID(), stat.getValue());
                }
            }
        }
    }

    /*
     * Helper Methods
     *
     */

    public ItemStack getStack(){
        return nbtItem.getItem();
    }

    private String getItemDisplayName(){
        return "&" + jItem.getRarity().getColor() + "&l" + jItem.getDisplayName();
    }

    private String getRarityLore(){
        return "&" + jItem.getRarity().getColor() + "&l" + jItem.getRarity();
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
        if(!jItem.getLore().isEmpty()) builder.skipLore();
        builder.addLore(jItem.getLore());
        if(!jItem.getStats().isEmpty()) builder.skipLore();
        builder.addLore(getRarityLore());
    }

}
