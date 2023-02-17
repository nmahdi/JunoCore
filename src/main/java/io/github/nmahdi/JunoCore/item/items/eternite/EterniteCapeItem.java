package io.github.nmahdi.JunoCore.item.items.eternite;

import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.crafting.Crafttable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import org.bukkit.Material;

public class EterniteCapeItem extends StatItem implements Runeable, Crafttable {

	public EterniteCapeItem(ItemManager itemManager) {
		super(itemManager, "eternite_cape", "Eternite Cape", Material.NETHERITE_INGOT, Rarity.Legendary, ItemType.Cape);
		setHealth(40).setDefense(8).setEarthElement(8).setHarvestingSpeed(80).setFortune(16);
	}

	@Override
	public int getMaxRunes() {
		return 4;
	}
}
