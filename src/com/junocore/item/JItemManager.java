package com.junocore.item;

import com.junocore.item.stats.ItemStatID;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class JItemManager {

    public ItemStatID getIdByName(String id){
        for(int i = 0; i < ItemStatID.values().length; i++){
            if(ItemStatID.values()[i].toString().equalsIgnoreCase(id)){
                return ItemStatID.values()[i];
            }
        }
        return null;
    }

    public JItem getItemByID(String id){
        for(int i = 0; i < JItem.values().length; i++){
            if(JItem.values()[i].getId().equalsIgnoreCase(id)){
                return JItem.values()[i];
            }
        }
        return null;
    }

    /**
     * Check's if an itemstack has the NBT Key "juno"
     *
     * @param item Itemstack
     * @return
     */

    public boolean isJunoItem(ItemStack item){
        NBTItem nItem = new NBTItem(item);
        if(nItem.hasCustomNbtData() && nItem.hasKey("juno")){
            return true;
        }
        return false;
    }

    public String getItemID(ItemStack item){
        NBTItem nItem = new NBTItem(item);
        return nItem.getCompound("juno").getString("id");
    }
    public String getItemID(NBTItem item){
        return item.getCompound("juno").getString("id");
    }


}
