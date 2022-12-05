package io.github.nmahdi.JunoCore.loot;

import io.github.nmahdi.JunoCore.item.GameItem;

import java.util.ArrayList;

public class WeightLootTable implements ILootTable {

    private String id;
    private int totalWeight = 0;
    private ArrayList<WeightedLoot> loot = new ArrayList<>();

    public WeightLootTable(String id){
        this.id = id;
    }

    public WeightLootTable addItem(GameItem item, int amount, int weight){
        totalWeight+=weight;
        loot.add(new WeightedLoot(new Loot(item, amount), weight, totalWeight));
        return this;
    }

    public WeightLootTable addItem(GameItem item, int minAmount, int maxAmount, int weight){
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
    public String getID() {
        return id;
    }

    @Override
    public boolean isWeighted() {
        return true;
    }
}
