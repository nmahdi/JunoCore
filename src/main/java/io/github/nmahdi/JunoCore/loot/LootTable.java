package io.github.nmahdi.JunoCore.loot;


import io.github.nmahdi.JunoCore.item.JItem;

import java.util.ArrayList;
import java.util.Arrays;

public enum LootTable {
    Zombie(new Loot(JItem.RottenFlesh, 2, 5, 100f), new Loot(JItem.CompactedRottenFlesh, 1, 1, 50f)),
    Zealot(new Loot(JItem.EnderPearl, 1, 4, 100f));

    private ArrayList<Loot> jLoot = new ArrayList<>();

    LootTable(Loot... jLoot){
        this.jLoot.addAll(Arrays.asList(jLoot));
    }

    public ArrayList<Loot> getJLoot() {
        return jLoot;
    }

}
