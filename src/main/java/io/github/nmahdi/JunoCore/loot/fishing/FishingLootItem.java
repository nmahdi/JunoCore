package io.github.nmahdi.JunoCore.loot.fishing;

import io.github.nmahdi.JunoCore.item.GameItem;

public class FishingLootItem extends FishingLoot{

	private GameItem gameItem;
	private int minAmount;
	private int maxAmount;

	public FishingLootItem(GameItem gameItem, int minAmount, int maxAmount, int weight, int weightIndex) {
		super(weight, weightIndex);
		this.gameItem = gameItem;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
	}

	public GameItem getGameItem() {
		return gameItem;
	}

	public int getMinAmount() {
		return minAmount;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

}
