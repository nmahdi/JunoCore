package io.github.nmahdi.JunoCore.loot;

public class WeightLoot {

    private Loot loot;
    private int weight;
    private int weightIndex;

    public WeightLoot(Loot loot, int weight, int weightIndex){
        this.loot = loot;
        this.weight = weight;
        this.weightIndex = weightIndex;
    }

    public Loot getLoot() {
        return loot;
    }

    public int getWeight() {
        return weight;
    }

    public int getWeightIndex() {
        return weightIndex;
    }

}
