package io.github.nmahdi.JunoCore.item.items.eternite;

import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.crafting.Crafttable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import org.bukkit.Material;

public class EterniteBootsItem extends StatItem implements Runeable, Crafttable {

	public EterniteBootsItem(ItemManager itemManager) {
		super(itemManager, "eternite_boots", "Eternite Boots", Material.NETHERITE_BOOTS, Rarity.Legendary, ItemType.Boots);
		setHealth(40).setDefense(8).setEarthElement(8).setHarvestingSpeed(80).setFortune(16);
	}

	@Override
	public int getMaxRunes() {
		return 4;
	}

}
