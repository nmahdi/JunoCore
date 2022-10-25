package io.github.nmahdi.JunoCore.item;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import org.bukkit.inventory.ItemStack;

public class NBTJItem extends NBTItem {

    public NBTJItem(ItemStack item) {
        super(item);
    }

    public NBTCompound getJuno(){
        return getOrCreateCompound("juno");
    }

    public boolean hasJuno(){
        return hasKey("juno");
    }

    public NBTCompound getStats(){
        return getJuno().getOrCreateCompound("stats");
    }

    public boolean hasStats(){ return getJuno().hasKey("stats"); }

    public void setStat(ItemStatID stat, String value){
        if(stat.isHidden()) {
            getHiddenStats().setString(stat.getID(), value);
            return;
        }
        getStats().setString(stat.getID(), value);
    }

    public String getStat(ItemStatID id){
        return getStat(id.getID());
    }

    public String getStat(String id){
        return getStats().getString(id);
    }

    public NBTCompound getHiddenStats(){
        return getJuno().getOrCreateCompound("hidden-stats");
    }

    public boolean hasHiddenStats(){ return getJuno().hasKey("hidden-stats"); }

    public String getHiddenStat(ItemStatID id){
        return getHiddenStat(id.getID());
    }

    public String getHiddenStat(String id){
        return getHiddenStats().getString(id);
    }


}
