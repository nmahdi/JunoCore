package io.github.nmahdi.JunoCore.loot;

import io.github.nmahdi.JunoCore.item.GameItem;

import java.util.HashMap;

public class ChanceLootTable implements ILootTable {

    private String id;
    private HashMap<Loot, Float> loot = new HashMap<>();

    public ChanceLootTable(String id){
        this.id = id;
    }

    public ChanceLootTable addItem(GameItem item, int amount, float chance){
        loot.put(new Loot(item, amount), chance);
        return this;
    }

    public ChanceLootTable addItem(GameItem item, int minAmount, int maxAmount, float chance){
        loot.put(new Loot(item, minAmount, maxAmount), chance);
        return this;
    }

    public float getChance(Loot loot){
        return this.loot.get(loot);
    }

    public HashMap<Loot, Float> getLoot() {
        return loot;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public boolean isWeighted() {
        return false;
    }

}
