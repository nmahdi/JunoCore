package io.github.nmahdi.JunoCore.loot;

import io.github.nmahdi.JunoCore.item.GameItem;

public enum LootTable {

	Zombie(new ChanceLootTable("zombie").addItem(GameItem.RottenFlesh, 1, 2, 50f)),
	Spider(new ChanceLootTable("spider").addItem(GameItem.String, 2, 4, 100f)
                .addItem(GameItem.SpiderEye, 1, 2, 50f)),
	Endermite(new ChanceLootTable("endermite").addItem(GameItem.EnderPearl, 1, 100f)),
	MagmaCube(new ChanceLootTable("magma_cube").addItem(GameItem.MagmaCream, 2, 4, 100f)),
	Slime(new ChanceLootTable("slime").addItem(GameItem.SlimeBall, 2, 4, 100f)),
	Skeleton(new ChanceLootTable("skeleton").addItem(GameItem.Bone, 1, 2, 100f)),

	Enderman(new ChanceLootTable("enderman").addItem(GameItem.EnderPearl, 1, 2, 100f)),

	BasicGeode(new WeightLootTable("basic_geode")
                .addItem(GameItem.CompactedCobblestone, 1, 5, 400)
                .addItem(GameItem.CompactedCoal, 1, 4, 300)
                .addItem(GameItem.CompactedIron, 1, 3, 200)
                .addItem(GameItem.CompactedGold, 1, 2, 100)
                .addItem(GameItem.CompactedDiamond, 1, 50)
                .addItem(GameItem.CompactedEmerald, 1, 25)),

	CommonEquipment(new ChanceLootTable("common_equipment").addItem(GameItem.MagicWand, 1, 2, 100f)),
	RareEquipment(new ChanceLootTable("rare_equipment").addItem(GameItem.MagicStone, 3, 4, 100f)),
	EpicEquipment(new ChanceLootTable("epic_equipment").addItem(GameItem.MagicStone, 5, 6, 100f)),

	;

	private ILootTable container;

	LootTable(ILootTable container){
		this.container = container;
	}

	public ILootTable getTable(){
		return container;
	}

	public boolean isWeighted(){
		return container.isWeighted();
	}
}
