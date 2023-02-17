package io.github.nmahdi.JunoCore.loot.items;

import java.util.HashMap;

public class ChanceItemLootTable implements LootTable{

	private HashMap<ItemDrop, Float> drops = new HashMap<>();

	public ChanceItemLootTable addDrop(ItemDrop drop, float chance){
		this.drops.put(drop, chance);
		return this;
	}

	public HashMap<ItemDrop, Float> getDrops() {
		return drops;
	}

	@Override
	public boolean isWeighted() {
		return false;
	}

}
