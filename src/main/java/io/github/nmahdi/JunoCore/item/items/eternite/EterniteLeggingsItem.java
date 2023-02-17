package io.github.nmahdi.JunoCore.item.items.eternite;

import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.crafting.Crafttable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import org.bukkit.Material;

public class EterniteLeggingsItem extends StatItem implements Runeable, Crafttable {

	public EterniteLeggingsItem(ItemManager itemManager) {
		super(itemManager, "eternite_leggings", "Eternite Leggings", Material.NETHERITE_LEGGINGS, Rarity.Legendary, ItemType.Leggings);
		setHealth(70).setDefense(14).setEarthElement(14).setHarvestingSpeed(140).setFortune(28);
	}

	@Override
	public int getMaxRunes() {
		return 7;
	}
}
