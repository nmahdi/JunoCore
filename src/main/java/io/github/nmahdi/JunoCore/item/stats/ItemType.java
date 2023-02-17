package io.github.nmahdi.JunoCore.item.stats;

import io.github.nmahdi.JunoCore.item.GameItem;

public enum ItemType {
    Helmet(Catagory.Armor),
    Chestplate(Catagory.Armor),
    Leggings(Catagory.Armor),
    Boots(Catagory.Armor),

    Ring(Catagory.Equipment),
    Necklace(Catagory.Equipment),
    Bracelet(Catagory.Equipment),
    Headband(Catagory.Equipment),
    Cape(Catagory.Equipment),

    Sword(Catagory.Weapon),
    Bow(Catagory.Weapon),
    Wand(Catagory.Weapon),

    Pickaxe(Catagory.Tool),
    Axe(Catagory.Tool),
    Shovel(Catagory.Tool),
    Hoe(Catagory.Tool),
    Shears(Catagory.Tool),
    FishingRod(Catagory.Tool),

    Rune(Catagory.Rune),

    CombatResource(Catagory.Resource),
    MiningResource(Catagory.Resource),
    ForagingResource(Catagory.Resource),
    FishingResource(Catagory.Resource),
    WoodcuttingResource(Catagory.Resource),
    FarmingResource(Catagory.Resource),

    Resource(Catagory.Resource),
    CompactedResource(Catagory.Resource),
    Ore(Catagory.Resource),
    MobDrop(Catagory.Resource),

    Misc(Catagory.Misc),
    Block(Catagory.Misc);

    private Catagory catagory;

    ItemType(Catagory catagory){
        this.catagory = catagory;
    }

    public Catagory getCatagory() {
        return catagory;
    }

    public static enum Catagory{
        Armor,
        Equipment,
        Weapon,
        Tool,
        Rune,
        Resource,
        Misc
        ;

    }

    public static boolean isArmor(GameItem item){
        return item.getItemType().getCatagory() == Catagory.Armor;
    }

    public static boolean isEquipment(GameItem item){
        return item.getItemType().getCatagory() == Catagory.Equipment;
    }

    public static boolean isWeapon(GameItem item){
        return item.getItemType().getCatagory() == Catagory.Weapon;
    }

    public static boolean isTool(GameItem item){
        return item.getItemType().getCatagory() == Catagory.Tool;
    }

    public static boolean isHandEquipable(GameItem item){
        return isTool(item) || isWeapon(item);
    }

}
