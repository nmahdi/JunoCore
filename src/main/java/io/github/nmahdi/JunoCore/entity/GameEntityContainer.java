package io.github.nmahdi.JunoCore.entity;

import io.github.nmahdi.JunoCore.entity.traits.GameTrait;
import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import io.github.nmahdi.JunoCore.loot.ILootTable;
import io.github.nmahdi.JunoCore.loot.LootTable;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public class GameEntityContainer {

	public EntityType entityType;
	public String displayName;
	public String id;

	public int combatXP;
	public int baseHealth;
	public int minLevel, maxLevel;
	public double damagePerHit;

	public double attackRange = 2;
	public double distanceMargin = 0.5;
	public int attackDelay = 20;
	public float speedModifier = 1f;

	public boolean invisible = false;
	public boolean ranged = false;
	public Material heldItem, helmet, chestplate, leggings, boots;

	public LootTable lootTable;

	public ArrayList<Class<? extends GameTrait>> traits = new ArrayList<>();

	public ArrayList<Class<? extends BehaviorGoalAdapter>> goals = new ArrayList<>();

	public GameEntityContainer(EntityType entityType, String displayName, String id, int combatXP, int baseHealth, int minLevel, int maxLevel, double damagePerHit, LootTable lootTable){
		this.entityType = entityType;
		this.displayName = displayName;
		this.id = id;
		this.combatXP = combatXP;
		this.baseHealth = baseHealth;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.damagePerHit = damagePerHit;
		this.lootTable = lootTable;
		this.traits.add(StatsTrait.class);
	}

	public GameEntityContainer setAttackRange(double attackRange){
		this.attackRange = attackRange;
		return this;
	}

	public GameEntityContainer setDistanceMargin(double distanceMargin){
		this.distanceMargin = distanceMargin;
		return this;
	}

	public GameEntityContainer setAttackDelay(int attackDelay){
		this.attackDelay = attackDelay;
		return this;
	}

	public GameEntityContainer setSpeedModifier(float speedModifier){
		this.speedModifier = speedModifier;
		return this;
	}

	public GameEntityContainer setInvisible() {
		this.invisible = true;
		return this;
	}

	public GameEntityContainer setRanged() {
		this.ranged = true;
		return this;
	}

	public GameEntityContainer setHeldItem(Material heldItem){
		this.heldItem = heldItem;
		return this;
	}

	public GameEntityContainer setHelmet(Material helmet) {
		this.helmet = helmet;
		return this;
	}

	public GameEntityContainer setChestplate(Material chestplate) {
		this.chestplate = chestplate;
		return this;
	}

	public GameEntityContainer setLeggings(Material leggings){
		this.leggings = leggings;
		return this;
	}

	public GameEntityContainer setBoots(Material boots){
		this.boots = boots;
		return this;
	}

	public GameEntityContainer addTrait(Class<? extends GameTrait> trait){
		this.traits.add(trait);
		return this;
	}

	public GameEntityContainer addGoal(Class<? extends BehaviorGoalAdapter> goal){
		this.goals.add(goal);
		return this;
	}

}
