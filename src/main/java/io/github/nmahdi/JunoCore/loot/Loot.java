package io.github.nmahdi.JunoCore.loot;

import io.github.nmahdi.JunoCore.item.JItem;

public class Loot {

    private JItem item;
    private int minAmount;
    private int maxAmount;
    private float chance;

    Loot(JItem item, int minAmount, int maxAmount, float chance){
        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.chance = chance;
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

    public float getChance() {
        return chance;
    }

}
