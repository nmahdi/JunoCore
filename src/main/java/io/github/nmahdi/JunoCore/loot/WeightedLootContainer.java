package io.github.nmahdi.JunoCore.loot;

import io.github.nmahdi.JunoCore.item.JItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeightedLootContainer implements ILootContainer {

    private int totalWeight = 0;
    private ArrayList<WeightedLoot> loot = new ArrayList<>();

    public WeightedLootContainer addItem(JItem item, int amount, int weight){
        totalWeight+=weight;
        loot.add(new WeightedLoot(new Loot(item, amount), weight, totalWeight));
        return this;
    }

    public WeightedLootContainer addItem(JItem item, int minAmount, int maxAmount, int weight){
        totalWeight+=weight;
        loot.add(new WeightedLoot(new Loot(item, minAmount, maxAmount), weight, totalWeight));
        return this;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public ArrayList<WeightedLoot> getLoot() {
        return loot;
    }

    @Override
    public boolean isWeighted() {
        return true;
    }
}
