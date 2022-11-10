package io.github.nmahdi.JunoCore.loot;

import io.github.nmahdi.JunoCore.item.JItem;

public class Loot{

    private JItem item;
    private int minAmount;
    private int maxAmount;

    Loot(JItem item, int minAmount, int maxAmount){
        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    Loot(JItem item, int amount){
        this.item = item;
        this.minAmount = amount;
        this.maxAmount = amount;
    }

    public JItem getItem() {
        return item;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

}