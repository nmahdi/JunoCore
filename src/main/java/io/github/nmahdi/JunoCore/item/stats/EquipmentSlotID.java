package io.github.nmahdi.JunoCore.item.stats;

import io.github.nmahdi.JunoCore.item.NBTJItem;
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
        NBTJItem item = new NBTJItem(itemStack);
        if(!item.hasJuno()) return null;
        if(!item.isEquipment()) return null;
        for(EquipmentSlotID slot : EquipmentSlotID.values()){
            if(slot.getId().equals(item.getEquipmentSlot().getId())) return slot;
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public int getSlot() {
        return slot;
    }

    public static EquipmentSlotID getSlot(String id) {
        for (EquipmentSlotID slot : EquipmentSlotID.values()) {
            if (slot.getId().equals(id)) return slot;
        }
        return null;
    }

}
