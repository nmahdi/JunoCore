package io.github.nmahdi.JunoCore.item.temp;

import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import org.bukkit.Material;

public abstract class RuneItem extends TempItem{

	private Rune runeType;

	public RuneItem(ItemManager itemManager, String id, String displayName, Material material, Rarity rarity, Rune runeType) {
		super(itemManager, id, displayName, material, rarity, ItemType.Rune);
	}

	public Rune getRuneType() {
		return runeType;
	}
}
