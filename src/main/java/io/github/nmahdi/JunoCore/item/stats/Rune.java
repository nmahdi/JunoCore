package io.github.nmahdi.JunoCore.item.stats;

import io.github.nmahdi.JunoCore.player.stats.PlayerStat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Rune {
	Efficiency(PlayerStat.HarvestingSpeed, 100, 1, ItemType.Catagory.Tool),

	Fortune(PlayerStat.Fortune, 10, 1, ItemType.Catagory.Tool),

	Smelting(null, 0, 1, ItemType.Catagory.Tool),

	Silktouch(null, 0, 1, ItemType.Catagory.Tool),

	Strength(PlayerStat.Strength, 25, 1, ItemType.Catagory.Weapon),

	CritDamage(PlayerStat.CritDamage, 10, 1, ItemType.Catagory.Weapon),

	CritChance(PlayerStat.CritChance, 5,1, ItemType.Catagory.Weapon),

	Defense(PlayerStat.Defense,20, 1, ItemType.Catagory.Armor, ItemType.Catagory.Equipment),

	Mana(PlayerStat.MaxMana, 50, 1, ItemType.Catagory.Armor, ItemType.Catagory.Equipment),

	Health(PlayerStat.MaxHealth, 25, 1, ItemType.Catagory.Armor, ItemType.Catagory.Equipment),

	//ADD ELEMENTAL
	;

	private PlayerStat stat;
	private int amount;
	private int cost;
	private ArrayList<ItemType.Catagory> appliedTo = new ArrayList<>();

	Rune(PlayerStat stat, int amount, int cost, ItemType.Catagory... appliedTo){
		this.stat = stat;
		this.amount = amount;
		this.cost = cost;
		this.appliedTo.addAll(Arrays.asList(appliedTo));
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

	public boolean canBeAppliedTo(ItemType.Catagory catagory){
		return appliedTo.contains(catagory);
	}

	public ArrayList<ItemType.Catagory> getAppliedTo() {
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
