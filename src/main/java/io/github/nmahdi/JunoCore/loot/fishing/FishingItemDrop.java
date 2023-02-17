package io.github.nmahdi.JunoCore.loot.fishing;

import io.github.nmahdi.JunoCore.item.GameItem;

public class FishingItemDrop extends FishingDrop {

	private GameItem gameItem;
	private int minAmount;
	private int maxAmount;

	public FishingItemDrop(GameItem gameItem, int minAmount, int maxAmount, int weight, int weightIndex) {
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
