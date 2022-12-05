package io.github.nmahdi.JunoCore.loot;

import io.github.nmahdi.JunoCore.item.GameItem;

public class Loot{

    private GameItem item;
    private int minAmount;
    private int maxAmount;

    Loot(GameItem item, int minAmount, int maxAmount){
        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    Loot(GameItem item, int amount){
        this.item = item;
        this.minAmount = amount;
        this.maxAmount = amount;
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