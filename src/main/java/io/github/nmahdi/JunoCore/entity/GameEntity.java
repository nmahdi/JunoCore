package io.github.nmahdi.JunoCore.entity;

import io.github.nmahdi.JunoCore.loot.ILootTable;
import io.github.nmahdi.JunoCore.loot.Loot;
import io.github.nmahdi.JunoCore.loot.LootTable;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum GameEntity {
    Zombie(new GameEntityContainer(EntityType.ZOMBIE, "Zombie", "zombie", 10, 100,
            1, 5, 5, LootTable.Zombie).setAttackRange(2).setDistanceMargin(0.5d)),

    Spider(new GameEntityContainer(EntityType.SPIDER, "Spider", "spider", 20, 150,
            1, 5, 8, LootTable.Spider).setAttackRange(1.5).setDistanceMargin(0.5)),

    Endermite(new GameEntityContainer(EntityType.ENDERMITE, "Endermite", "endermite", 15, 50,
            2, 5, 3, LootTable.Endermite).setAttackRange(0.5).setDistanceMargin(0)),

    MagmaCube(new GameEntityContainer(EntityType.MAGMA_CUBE, "Magma Cube", "magma_cube", 20, 150,
            1, 5, 8, LootTable.MagmaCube).setAttackRange(0.5).setDistanceMargin(0.1)),

    Slime(new GameEntityContainer(EntityType.SLIME, "Slime", "slime", 20, 150,
            1, 5, 8, LootTable.Slime).setAttackRange(0.5).setDistanceMargin(0.1)),

    Skeleton(new GameEntityContainer(EntityType.SKELETON, "Skeleton", "skeleton", 10, 100,
            1, 5, 4, LootTable.Skeleton).setAttackRange(0).setDistanceMargin(6).setRanged().setHeldItem(Material.BOW).setAttackDelay(50)),

    Enderman(new GameEntityContainer(EntityType.ENDERMAN, "Enderman", "enderman", 30, 200,
            1, 5, 15, LootTable.Enderman).setAttackRange(2).setDistanceMargin(0.5)),

    Smasher(new GameEntityContainer(EntityType.ZOMBIE, "Smasher", "smasher", 50, 4000,
            10, 20, 15, LootTable.Zombie).setAttackRange(2).setDistanceMargin(0.5).setAttackDelay(10).setSpeedModifier(2).setHeldItem(Material.ANVIL)),

    ;

    private GameEntityContainer container;

    GameEntity(GameEntityContainer container){
        this.container = container;
    }

    public EntityType getEntityType() {
        return container.entityType;
    }

    public String getDisplayName() {
        return container.displayName;
    }

    public String getId() {
        return container.id;
    }

    public int getCombatXP() {
        return container.combatXP;
    }

    public int getBaseHealth() {
        return container.baseHealth;
    }

    public int getMinLevel() {
        return container.minLevel;
    }

    public int getMaxLevel() {
        return container.maxLevel;
    }

    public double getDamagePerHit() {
        return container.damagePerHit;
    }

    public double getAttackRange() {
        return container.attackRange;
    }


    public double getDistanceMargin() {
        return container.distanceMargin;
    }


    public int getAttackDelay() {
        return container.attackDelay;
    }

    public float getSpeedModifier() {
        return container.speedModifier;
    }

    public boolean isInvisible() {
        return container.invisible;
    }


    public boolean isRanged() {
        return container.ranged;
    }


    public boolean hasHeldItem(){
        return container.heldItem != null;
    }

    public Material heldItem(){
        return  container.heldItem;
    }

    public boolean hasHelmet(){
        return  container.helmet != null;
    }



    public Material getHelmet() {
        return  container.helmet;
    }

    public boolean hasChestplate(){
        return  container.chestplate != null;
    }


    public Material getChestplate() {
        return  container.chestplate;
    }

    public boolean hasLeggings(){
        return  container.leggings != null;
    }


    public Material getLeggings() {
        return  container.leggings;
    }

    public boolean hasBoots(){
        return  container.boots != null;
    }


    public Material getBoots() {
        return  container.boots;
    }

    public LootTable getLootTable() {
        return  container.lootTable;
    }

    public boolean isSlime(){
        return  container.entityType == EntityType.SLIME ||  container.entityType == EntityType.MAGMA_CUBE;
    }

    public static GameEntity getEntity(String id){
        for (GameEntity entity : GameEntity.values()) {
            if (entity.getId().equalsIgnoreCase(id)) return entity;
        }
        return null;
    }


}