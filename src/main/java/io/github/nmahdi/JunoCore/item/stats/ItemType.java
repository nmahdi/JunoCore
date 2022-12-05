package io.github.nmahdi.JunoCore.item.stats;

public enum ItemType {
    Helmet(Catagory.ARMOR),
    Chestplate(Catagory.ARMOR),
    Leggings(Catagory.ARMOR),
    Boots(Catagory.ARMOR),

    Ring(Catagory.EQUIPMENT),
    Necklace(Catagory.EQUIPMENT),
    Bracelet(Catagory.EQUIPMENT),
    Headband(Catagory.EQUIPMENT),
    Cape(Catagory.EQUIPMENT),

    Sword(Catagory.WEAPON),
    Bow(Catagory.WEAPON),
    Wand(Catagory.WEAPON),

    Pickaxe(Catagory.TOOL),
    Axe(Catagory.TOOL),
    Shovel(Catagory.TOOL),
    Hoe(Catagory.TOOL),
    Shears(Catagory.TOOL),

    Rune(Catagory.RUNE),

    Misc(Catagory.MISC),
    Block(Catagory.MISC);

    private int catagory;

    ItemType(int catagory){
        this.catagory = catagory;
    }

    public int getCatagory() {
        return catagory;
    }

    public static class Catagory{

        public static final int ARMOR = 0;
        public static final int EQUIPMENT = 1;
        public static final int WEAPON = 2;
        public static final int TOOL = 3;
        public static final int RUNE = 4;
        public static final int MISC = 5;
    }

}
