package io.github.nmahdi.JunoCore.loot;


import io.github.nmahdi.JunoCore.item.JItem;

public enum LootTable {
    Zombie("zombie", new LootContainer().addItem(JItem.RottenFlesh, 1, 2, 100f)
            .addItem(JItem.CompactedRottenFlesh, 1, 1, 10f)),
    Zealot("zealot", new LootContainer().addItem(JItem.EnderPearl, 1, 2, 100f)),
    Geode("basic_geode", new WeightedLootContainer()
            .addItem(JItem.CompactedCobble, 1, 5, 400)
            .addItem(JItem.CompactedCoal, 1, 4, 300)
            .addItem(JItem.CompactedIron, 1, 3, 200)
            .addItem(JItem.CompactedGold, 1, 2, 100)
            .addItem(JItem.CompactedDiamond, 1, 50)
            .addItem(JItem.CompactedEmerald, 1, 25)),
    CommonEquipment("common_equipment", new LootContainer().addItem(JItem.MagicStone, 1, 2, 100f)),
    RareEquipment("rare_equipment", new LootContainer().addItem(JItem.MagicStone, 3, 4, 100f)),
    EpicEquipment("epic_equipment", new LootContainer().addItem(JItem.MagicStone, 5, 6, 100f))

    ;

    String id;
    ILootContainer lootContainer;

    LootTable(String id, ILootContainer lootContainer){
        this.id = id;
        this.lootContainer = lootContainer;
    }

    public ILootContainer getLootContainer() {
        return lootContainer;
    }

    public boolean isWeighted(){
        return lootContainer.isWeighted();
    }

}
