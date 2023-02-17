package io.github.nmahdi.JunoCore.loot.items;

import io.github.nmahdi.JunoCore.item.GameItem;

import java.util.Random;

public class ItemDrop {

	private GameItem item;
	private int minAmount, maxAmount;

	public ItemDrop(GameItem item, int minAmount, int maxAmount){
		this.item = item;
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
	}

	public ItemDrop(GameItem item, int amount){
		this.item = item;
		this.minAmount = amount;
		this.maxAmount = amount;
	}

	public int rollAmount(Random random){
		return random.nextInt(minAmount, maxAmount+1);
	}

	public GameItem getItem() {
		return item;
	}

	public int getMinAmount() {
		return minAmount;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

}
