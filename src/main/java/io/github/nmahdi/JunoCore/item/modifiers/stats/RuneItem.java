package io.github.nmahdi.JunoCore.item.modifiers.stats;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class RuneItem extends GameItem {

	private Rune runeType;
	private int cost;

	public RuneItem(ItemManager itemManager, String id, String displayName, Material material, Rarity rarity, Rune runeType, int cost) {
		super(itemManager, id, displayName, material, rarity, ItemType.Rune);
		this.runeType = runeType;
		this.cost = cost;
	}


	public Rune getRuneType() {
		return runeType;
	}

	public int getCost() {
		return cost;
	}

	public boolean canBeAppliedTo(ItemType.Catagory catagory){
		return runeType.canBeAppliedTo(catagory);
	}

}
