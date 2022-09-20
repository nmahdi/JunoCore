package com.junocore.loot;

import com.junocore.item.JItem;

import java.util.ArrayList;
import java.util.Arrays;

public enum JLootTable {
    Zombie(new JLoot(JItem.RottenFlesh, 2, 5, 100f), new JLoot(JItem.CompactedRottenFlesh, 1, 1, 50f)),
    Zealot(new JLoot(JItem.EnderPearl, 1, 4, 100f));

    private ArrayList<JLoot> jLoot = new ArrayList<>();

    JLootTable(JLoot... jLoot){
        this.jLoot.addAll(Arrays.asList(jLoot));
    }

    public ArrayList<JLoot> getJLoot() {
        return jLoot;
    }

}
