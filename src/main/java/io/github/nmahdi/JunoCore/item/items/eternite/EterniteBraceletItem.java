package io.github.nmahdi.JunoCore.item.items.eternite;

import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.crafting.Crafttable;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import org.bukkit.Material;

import java.util.ArrayList;

public class EterniteBraceletItem extends StatItem implements Runeable, Crafttable {

	public EterniteBraceletItem(ItemManager itemManager) {
		super(itemManager, "eternite_barcelet", "Eternite Bracelet", Material.NETHERITE_INGOT, Rarity.Legendary, ItemType.Bracelet);
	}

	@Override
	public int getMaxRunes() {
		return 4;
	}

}
