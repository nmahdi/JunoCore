package io.github.nmahdi.JunoCore.item.builder;

import io.github.nmahdi.JunoCore.item.JItem;
import io.github.nmahdi.JunoCore.item.NBTJItem;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import io.github.nmahdi.JunoCore.item.stats.Stat;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class NBTSkullBuilder{

    private SkullItemBuilder builder;
    private NBTJItem nbtItem;
    private JItem item;

    public NBTSkullBuilder(JItem item){
        this.item = item;
        builder = new SkullItemBuilder(item.getTexture()).setName(getItemDisplayName());
        init();
    }

    public NBTSkullBuilder(Player owner, String displayName, String... lore){
        builder = new SkullItemBuilder(owner).setName(displayName).addLore(lore);
    }

    private void init(){
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
        this.nbtItem = new NBTJItem(builder.build());
        this.nbtItem.getJuno().setString("id", item.getId());
        //Adds the stats to the Juno~Stats NBT Tag
        if(!item.getStats().isEmpty()) {
            for (Stat stat : item.getStats()) {
                this.nbtItem.setStat(stat.getID(), stat.getValue());
            }
        }
    }

    public ItemStack build(){
        return nbtItem.getItem();
    }

    /*
     * Helper Methods
     *
     */

    public ItemStack getStack(){
        return nbtItem.getItem();
    }

    private String getItemDisplayName(){
        return "&" + item.getRarity().getColor() + "&l" + item.getDisplayName();
    }

    private String getRarityLore(){
        return "&" + item.getRarity().getColor() + "&l" + item.getRarity();
    }

    private String getStatLore(Stat stat){
        return getStatLore(stat.getID().getDisplayName(), stat.getValue());
    }

    private String getStatLore(String displayName, String value){
        String color = "a+";
        if(Integer.parseInt(value) < 0) color = "c";
        return "&7" + displayName + "&f: &" + color + value;
    }

    private void addLore(SkullItemBuilder builder){
        if(!item.getStats().isEmpty()) builder.skipLore();
        builder.addLore(item.getLore());
        if(!item.getLore().isEmpty()) builder.skipLore();
        builder.addLore(getRarityLore());
    }

}
