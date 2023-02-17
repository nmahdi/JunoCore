package io.github.nmahdi.JunoCore.item.items.eternite;

import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.crafting.Crafttable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import org.bukkit.Material;

public class EterniteHeadbandItem extends StatItem implements Runeable, Crafttable {

	public EterniteHeadbandItem(ItemManager itemManager) {
		super(itemManager, "eternite_headband", "Eternite Headband", Material.NETHERITE_INGOT, Rarity.Legendary, ItemType.Headband);
		setHealth(20).setDefense(4).setEarthElement(4).setHarvestingSpeed(40).setFortune(8);
	}

	@Override
	public int getMaxRunes() {
		return 2;
	}
}
