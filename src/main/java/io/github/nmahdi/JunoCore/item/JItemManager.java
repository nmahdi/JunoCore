package io.github.nmahdi.JunoCore.item;

import de.tr7zw.nbtapi.NBTItem;
import io.github.nmahdi.JunoCore.item.stats.EquipmentSlotID;
import io.github.nmahdi.JunoCore.item.stats.ItemStatID;
import io.github.nmahdi.JunoCore.player.PlayerStatID;
import org.bukkit.inventory.ItemStack;

public class JItemManager {

    public boolean isPlayerStat(ItemStatID stat){
        for(PlayerStatID id : PlayerStatID.values()){
            if(id.getId().equals(stat.getID())) return true;
        }
        return false;
    }

    public EquipmentSlotID getSlotByID(String id){
        for(EquipmentSlotID slot : EquipmentSlotID.values()){
            if(slot.getId().equals(id)) return slot;
        }
        return null;
    }

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
