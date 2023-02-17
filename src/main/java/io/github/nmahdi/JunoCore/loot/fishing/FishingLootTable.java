package io.github.nmahdi.JunoCore.loot.fishing;

import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.loot.items.LootTable;

import java.util.ArrayList;

public class FishingLootTable implements LootTable {

	private int totalWeight = 0;
	private ArrayList<FishingDrop> loot = new ArrayList<>();

	public FishingLootTable(){
	}

	public FishingLootTable addItem(GameItem item, int amount, int weight){
		totalWeight+=weight;
		loot.add(new FishingItemDrop(item, amount, amount, weight, totalWeight));
		return this;
	}

	public FishingLootTable addItem(GameItem item, int minAmount, int maxAmount, int weight){
		totalWeight+=weight;
		loot.add(new FishingItemDrop(item, minAmount, maxAmount, weight, totalWeight));
		return this;
	}

	public FishingLootTable addEntity(GameEntity entity, int weight){
		totalWeight+=weight;
		loot.add(new FishingEntityDrop(entity, weight, totalWeight));
		return this;
	}

	public FishingLootTable merge(FishingLootTable... tables){

		for(FishingLootTable table : tables){
			for(FishingDrop fishingLoot : table.getLoot()){
				FishingDrop newLoot = null;

				totalWeight+= fishingLoot.getWeight();
				if(fishingLoot instanceof FishingItemDrop){
					FishingItemDrop oldLoot = (FishingItemDrop) fishingLoot;
					newLoot = new FishingItemDrop(oldLoot.getGameItem(), oldLoot.getMinAmount(), oldLoot.getMaxAmount(), oldLoot.getWeight(), totalWeight);
				}

				if(fishingLoot instanceof FishingEntityDrop){
					FishingEntityDrop oldLoot = (FishingEntityDrop) fishingLoot;
					newLoot = new FishingEntityDrop(oldLoot.getGameEntity(), oldLoot.getWeight(), totalWeight);
				}

				if(newLoot != null) loot.add(newLoot);
			}
		}

		return this;
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public ArrayList<FishingDrop> getLoot() {
		return loot;
	}

	@Override
	public boolean isWeighted() {
		return true;
	}
}
