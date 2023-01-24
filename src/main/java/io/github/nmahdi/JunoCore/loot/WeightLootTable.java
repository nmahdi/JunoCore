package io.github.nmahdi.JunoCore.loot;

import io.github.nmahdi.JunoCore.item.GameItem;

import java.util.ArrayList;
import java.util.List;

public class WeightLootTable implements ILootTable {

    private int totalWeight = 0;
    private ArrayList<WeightLoot> loot = new ArrayList<>();

    public WeightLootTable(){
    }

    public WeightLootTable addItem(GameItem item, int amount, int weight){
        totalWeight+=weight;
        loot.add(new WeightLoot(new Loot(item, amount), weight, totalWeight));
        return this;
    }

    public WeightLootTable addItem(GameItem item, int minAmount, int maxAmount, int weight){
        totalWeight+=weight;
        loot.add(new WeightLoot(new Loot(item, minAmount, maxAmount), weight, totalWeight));
        return this;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public ArrayList<WeightLoot> getLoot() {
        return loot;
    }

    @Override
    public boolean isWeighted() {
        return true;
    }
}
