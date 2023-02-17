package io.github.nmahdi.JunoCore.loot.items;

import io.github.nmahdi.JunoCore.item.GameItem;

import java.util.ArrayList;
import java.util.Random;

public class WeightedItemLootTable implements LootTable{

	private int totalWeight = 0;
	private ArrayList<WeightedItemDrop> drops = new ArrayList<>();

	public WeightedItemLootTable addDrop(GameItem item, int minAmount, int maxAmount, int weight){
		totalWeight+=weight;
		drops.add(new WeightedItemDrop(item, minAmount, maxAmount, weight, totalWeight));
		return this;
	}

	public WeightedItemLootTable addDrop(GameItem item, int amount, int weight){
		totalWeight+=weight;
		drops.add(new WeightedItemDrop(item, amount, weight, totalWeight));
		return this;
	}

	public ItemDrop roll(Random random){
		int roll = random.nextInt(totalWeight)+1;

		for(int i = 0; i < drops.size(); i++){

			if(i > 0){
				if(roll < drops.get(i).getWeightIndex() && roll >= drops.get(i - 1).getWeightIndex()){
					return drops.get(i);
				}
			}else{
				if(roll < drops.get(i).getWeightIndex()){
					return drops.get(i);
				}
			}

		}
		return null;
	}

	public int getTotalWeight() {
		return totalWeight;
	}

	public ArrayList<WeightedItemDrop> getDrops() {
		return drops;
	}

	@Override
	public boolean isWeighted() {
		return true;
	}
}
