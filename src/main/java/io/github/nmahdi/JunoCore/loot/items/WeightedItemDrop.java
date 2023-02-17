package io.github.nmahdi.JunoCore.loot.items;

import io.github.nmahdi.JunoCore.item.GameItem;

public class WeightedItemDrop extends ItemDrop{

	private int weight;
	private int weightIndex;

	public WeightedItemDrop(GameItem item, int minAmount, int maxAmount, int weight, int weightIndex) {
		super(item, minAmount, maxAmount);
	}

	public WeightedItemDrop(GameItem item, int amount, int weight, int weightIndex){
		super(item, amount);
	}

	public int getWeight() {
		return weight;
	}

	public int getWeightIndex() {
		return weightIndex;
	}

}
