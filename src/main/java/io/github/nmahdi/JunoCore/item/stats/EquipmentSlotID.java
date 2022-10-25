package io.github.nmahdi.JunoCore.item.stats;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public enum EquipmentSlotID {
    Helmet("helmet", 5),
    Chestplate("chestplate", 6),
    Leggings("leggings", 7),
    Boots("boots", 8),
    Ring("ring"),
    Necklace("necklace"),
    Bracelet("bracelet"),
    Cape("cape"),
    Headband("headband")
    ;

    private int slot;
    private String id;

    EquipmentSlotID(String id, int slot){
        this.id = id;
        this.slot = slot;
    }

    EquipmentSlotID(String id){
        this.id = id;
        this.slot = 9999;
    }

    public static EquipmentSlotID matchType(final ItemStack itemStack){
        if(itemStack == null && itemStack.getType().isAir()) return null;
        if(!itemStack.hasItemMeta()) return null;
        NBTItem item = new NBTItem(itemStack);
        if(!item.hasKey("juno")) return null;
        if(!item.getCompound("juno").hasKey("hidden-stats")) return null;
        if(!item.getCompound("juno").getCompound("hidden-stats").hasKey(ItemStatID.EquipmentSlot.getID())) return null;
        for(EquipmentSlotID slot : EquipmentSlotID.values()){
            if(slot.getId().equals(item.getCompound("juno").getCompound("hidden-stats").getString(ItemStatID.EquipmentSlot.getID()))) return slot;
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public int getSlot() {
        return slot;
    }
}
