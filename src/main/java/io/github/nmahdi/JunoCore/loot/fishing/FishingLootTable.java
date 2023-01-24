package io.github.nmahdi.JunoCore.loot.fishing;

import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.loot.ILootTable;
import io.github.nmahdi.JunoCore.loot.Loot;
import io.github.nmahdi.JunoCore.loot.WeightLoot;
import io.github.nmahdi.JunoCore.loot.WeightLootTable;

import java.util.ArrayList;

public class FishingLootTable implements ILootTable {

	private int totalWeight = 0;
	private ArrayList<FishingLoot> loot = new ArrayList<>();

	public FishingLootTable(){
	}

	public FishingLootTable addItem(GameItem item, int amount, int weight){
		totalWeight+=weight;
		loot.add(new FishingLootItem(item, amount, amount, weight, totalWeight));
		return this;
	}

	public FishingLootTable addItem(GameItem item, int minAmount, int maxAmount, int weight){
		totalWeight+=weight;
		loot.add(new FishingLootItem(item, minAmount, maxAmount, weight, totalWeight));
		return this;
	}

	public FishingLootTable addEntity(GameEntity entity, int weight){
		totalWeight+=weight;
		loot.add(new FishingLootEntity(entity, weight, totalWeight));
		return this;
	}

	public FishingLootTable merge(FishingLootTable... tables){

		for(FishingLootTable table : tables){
			for(FishingLoot fishingLoot : table.getLoot()){
				FishingLoot newLoot = null;

				totalWeight+= fishingLoot.getWeight();
				if(fishingLoot instanceof FishingLootItem){
					FishingLootItem oldLoot = (FishingLootItem) fishingLoot;
					newLoot = new FishingLootItem(oldLoot.getGameItem(), oldLoot.getMinAmount(), oldLoot.getMaxAmount(), oldLoot.getWeight(), totalWeight);
				}

				if(fishingLoot instanceof FishingLootEntity){
					FishingLootEntity oldLoot = (FishingLootEntity) fishingLoot;
					newLoot = new FishingLootEntity(oldLoot.getGameEntity(), oldLoot.getWeight(), totalWeight);
				}

				if(newLoot != null) loot.add(newLoot);
			}
		}

		return this;
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public ArrayList<FishingLoot> getLoot() {
		return loot;
	}

	@Override
	public boolean isWeighted() {
		return true;
	}
}
