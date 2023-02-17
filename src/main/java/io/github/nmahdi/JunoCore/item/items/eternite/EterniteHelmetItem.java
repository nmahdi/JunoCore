package io.github.nmahdi.JunoCore.item.items.eternite;

import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.crafting.Crafttable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import org.bukkit.Material;

public class EterniteHelmetItem extends StatItem implements Runeable, Crafttable {

	public EterniteHelmetItem(ItemManager itemManager) {
		super(itemManager, "eternite_helmet", "Eternite Helmet", Material.NETHERITE_HELMET, Rarity.Legendary, ItemType.Helmet);
		setHealth(50).setDefense(10).setEarthElement(10).setHarvestingSpeed(100).setFortune(20);
	}

	@Override
	public int getMaxRunes() {
		return 5;
	}
}
