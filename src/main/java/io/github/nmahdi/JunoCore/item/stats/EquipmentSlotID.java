package io.github.nmahdi.JunoCore.item.stats;

public enum EquipmentSlotID {
    Helmet("helmet"),
    Chestplate("chestplate"),
    Leggings("leggings"),
    Boots("boots"),
    Ring("ring"),
    Necklace("necklace"),
    Bracelet("bracelet"),
    Cape("cape"),
    Headband("headband")
    ;

    private String id;

    EquipmentSlotID(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
