package io.github.nmahdi.JunoCore.entity;

import io.github.nmahdi.JunoCore.entity.undeadmonstrosity.UndeadMonstrosityAttackGoal;
import io.github.nmahdi.JunoCore.entity.undeadmonstrosity.UndeadMonstrosityMinionGoal;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public enum GameEntity {
    //Animals
    Cow(new GameEntityContainer(EntityType.COW, "Cow", "cow", 1, 10,
            1, 5, 0).setAttackRange(2).setDistanceMargin(0.5d)),
    //Monsters
    Zombie(new GameEntityContainer(EntityType.ZOMBIE, "Zombie", "zombie", 10, 100,
            1, 5, 5).setAttackRange(2).setDistanceMargin(0.5d).setSpeedModifier(2f)),

    Skeleton(new GameEntityContainer(EntityType.SKELETON, "Skeleton", "skeleton", 10, 100,
            1, 5, 4).setAttackRange(0).setDistanceMargin(6).setRanged().setHeldItem(Material.BOW).setAttackDelay(50)),

    LostWizard(new GameEntityContainer(EntityType.PILLAGER, "Lost Wizard", "lost_wizard", 50, 200,
            1, 5, 100).setAttackRange(1).setDistanceMargin(10)),

    Smasher(new GameEntityContainer(EntityType.ZOMBIE, "Smasher", "smasher", 50, 4000,
            10, 20, 15).setAttackRange(2).setDistanceMargin(0.5).setAttackDelay(10).setSpeedModifier(2).setHeldItem(Material.ANVIL)),

    //Undead Monstrosity

    UndeadMonstrosity(new GameEntityContainer(EntityType.ZOMBIE, "Undead Monstrosity", "undead_monstrosity", 1000, 10000,
            100, 100, 100).setAttackRange(2).setDistanceMargin(0.5).setAttackDelay(10).setSpeedModifier(1.5f)
            .addGoal(UndeadMonstrosityAttackGoal.class).addGoal(UndeadMonstrosityMinionGoal.class)),

    UndeadMinion(new GameEntityContainer(EntityType.ZOMBIE, "Undead Minion", "undead_minion", 10, 500,
            20, 25, 20).setAttackRange(2).setDistanceMargin(0.5).setAttackDelay(10)),

    //Fishing Mobs
    Drowned(new GameEntityContainer(EntityType.DROWNED, "Drowned", "drowned", 10, 100,
            1, 5, 10).setAttackRange(2).setDistanceMargin(0.5d))

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

    public ArrayList<Class<? extends GameGoal>> getGoals(){
        return container.goals;
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