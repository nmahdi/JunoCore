package io.github.nmahdi.JunoCore.loot;

import io.github.nmahdi.JunoCore.item.JItem;

import java.util.HashMap;

public class LootContainer implements ILootContainer {

    HashMap<Loot, Float> loot = new HashMap<>();

    public LootContainer addItem(JItem item, int amount, float chance){
        loot.put(new Loot(item, amount), chance);
        return this;
    }

    public LootContainer addItem(JItem item, int minAmount, int maxAmount, float chance){
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
    public boolean isWeighted() {
        return false;
    }

}
