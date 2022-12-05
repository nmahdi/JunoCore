package io.github.nmahdi.JunoCore.item.stats;

import io.github.nmahdi.JunoCore.player.stats.PlayerStat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Rune {
	Efficiency(PlayerStat.HarvestingSpeed, 100, 1, ItemType.Catagory.TOOL),

	Fortune(PlayerStat.Fortune, 10, 1, ItemType.Catagory.TOOL),

	Smelting(null, 0, 1, ItemType.Catagory.TOOL),

	Silktouch(null, 0, 1, ItemType.Catagory.TOOL),

	Damage(PlayerStat.Damage, 10,1, ItemType.Catagory.WEAPON),

	Strength(PlayerStat.Strength, 25, 1, ItemType.Catagory.ARMOR, ItemType.Catagory.EQUIPMENT),

	CritDamage(PlayerStat.CritDamage, 10, 1, ItemType.Catagory.ARMOR, ItemType.Catagory.EQUIPMENT),

	CritChance(PlayerStat.CritChance, 5,1, ItemType.Catagory.ARMOR, ItemType.Catagory.EQUIPMENT),

	Defense(PlayerStat.Defense,20, 1, ItemType.Catagory.ARMOR, ItemType.Catagory.EQUIPMENT),

	Mana(PlayerStat.MaxMana, 50, 1, ItemType.Catagory.WEAPON, ItemType.Catagory.ARMOR, ItemType.Catagory.EQUIPMENT),

	Health(PlayerStat.MaxHealth, 25, 1, ItemType.Catagory.ARMOR, ItemType.Catagory.EQUIPMENT),

	//ADD ELEMENTAL
	;

	private PlayerStat stat;
	private int amount;
	private int cost;
	private ArrayList<Integer> appliedTo = new ArrayList<>();

	Rune(PlayerStat stat, int amount, int cost, int... appliedTo){
		this.stat = stat;
		this.amount = amount;
		this.cost = cost;
		for(Integer i : appliedTo){
			this.appliedTo.add(i);
		}
	}

	public PlayerStat getStat() {
		return stat;
	}

	public int getAmount() {
		return amount;
	}

	public int getCost() {
		return cost;
	}

	public boolean isStatBased(){
		return stat != null;
	}

	public boolean canBeAppliedTo(int catagory){
		return appliedTo.contains(catagory);
	}

	public ArrayList<Integer> getAppliedTo() {
		return appliedTo;
	}

	public static Rune getRune(PlayerStat stat){
		for(Rune rune : Rune.values()){
			if(rune.isStatBased()){
				if(rune.getStat().equals(stat)) return rune;
			}
		}
		return null;
	}
}
