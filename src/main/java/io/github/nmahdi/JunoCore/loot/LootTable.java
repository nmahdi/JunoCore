package io.github.nmahdi.JunoCore.loot;

import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.loot.fishing.FishingLootTable;

public enum LootTable {

	Zombie(new ChanceLootTable().addItem(GameItem.RottenFlesh, 1, 2, 100f)),
	Skeleton(new ChanceLootTable().addItem(GameItem.Bone, 1, 2, 100f)),

	LowQualityGeode(new WeightLootTable()
                .addItem(GameItem.CompactedCobblestone, 1, 5, 400)
                .addItem(GameItem.CompactedCoal, 1, 4, 300)
                .addItem(GameItem.CompactedIronBar, 1, 3, 200)
                .addItem(GameItem.CompactedGoldBar, 1, 2, 100)
                .addItem(GameItem.CompactedDiamond, 1, 50)
                .addItem(GameItem.CompactedEmerald, 1, 25)),

	FairQualityGeode(new WeightLootTable()
			.addItem(GameItem.CompactedCobblestone, 3, 8, 400)
			.addItem(GameItem.CompactedCoal, 4, 7, 300)
			.addItem(GameItem.CompactedIronBar, 3, 6, 200)
			.addItem(GameItem.CompactedGoldBar, 2, 5, 100)
			.addItem(GameItem.CompactedDiamond, 1, 2, 50)
			.addItem(GameItem.CompactedEmerald, 1, 2, 25)
			.addItem(GameItem.MagicStone, 1, 5)),

	HighQualityGeode(new WeightLootTable()
			.addItem(GameItem.CompactedCobblestone, 5, 10, 400)
			.addItem(GameItem.CompactedCoal, 6, 8, 300)
			.addItem(GameItem.CompactedIronBar, 5, 7, 200)
			.addItem(GameItem.CompactedGoldBar, 3, 6, 100)
			.addItem(GameItem.CompactedDiamond, 2, 4, 50)
			.addItem(GameItem.CompactedEmerald, 2, 4, 25)
			.addItem(GameItem.MagicStone, 2, 5)),


	CommonEquipment(new ChanceLootTable().addItem(GameItem.MagicStone, 1, 2, 100f)),
	RareEquipment(new ChanceLootTable().addItem(GameItem.MagicStone, 3, 4, 100f)),
	EpicEquipment(new ChanceLootTable().addItem(GameItem.MagicStone, 5, 6, 100f)),

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
