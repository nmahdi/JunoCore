package io.github.nmahdi.JunoCore.entity;

import io.github.nmahdi.JunoCore.loot.LootTable;
import org.bukkit.entity.EntityType;

public enum JEntity{
    Zombie(EntityType.ZOMBIE, "Zombie", "basic_zombie", 10, 100, 1, 10, LootTable.Zombie),
    Zealot(EntityType.ENDERMAN, "Zealot", "zealot", 50, 1000, 10, 20, LootTable.Zealot),

    ;

    private EntityType entityType;
    private String displayName;
    private String id;
    private int xp;
    private int baseHealth;

    private int minLevel;
    private int maxLevel;

    private LootTable lootTable;


    JEntity(EntityType entityType, String displayName, String id, int xp, int baseHealth, int minLevel, int maxLevel, LootTable lootTable){
        this.entityType = entityType;
        this.displayName = displayName;
        this.id = id;
        this.xp = xp;
        this.baseHealth = baseHealth;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.lootTable = lootTable;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }

    public int getXP(){
        return xp;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public LootTable getLootTable() {
        return lootTable;
    }
}