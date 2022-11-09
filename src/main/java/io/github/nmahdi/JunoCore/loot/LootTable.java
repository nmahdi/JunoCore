package io.github.nmahdi.JunoCore.loot;


import io.github.nmahdi.JunoCore.item.JItem;

import java.util.ArrayList;
import java.util.Arrays;

public enum LootTable {
    Zombie(new Loot(JItem.RottenFlesh, 2, 5), new Loot(JItem.CompactedRottenFlesh, 1, 1)),
    Zealot(new Loot(JItem.EnderPearl, 1, 4)),
    Geode(true, new Loot(JItem.GoldBar, 1, 1))

    ;

    private boolean weighted;
    private ArrayList<Loot> jLoot = new ArrayList<>();

    LootTable(Loot... jLoot){
        this.weighted = false;
        this.jLoot.addAll(Arrays.asList(jLoot));
    }

    LootTable(boolean weighted, Loot... jLoot){
        this.weighted = weighted;
        this.jLoot.addAll(Arrays.asList(jLoot));
    }

    public ArrayList<Loot> getJLoot() {
        return jLoot;
    }

}
