package io.github.nmahdi.JunoCore.item.items.eternite;

import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.crafting.Crafttable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.Runeable;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import org.bukkit.Material;

public class EterniteChestplateItem extends StatItem implements Runeable, Crafttable {

	public EterniteChestplateItem(ItemManager itemManager) {
		super(itemManager, "eternite_chestplate", "Eternite Chestplate", Material.NETHERITE_CHESTPLATE, Rarity.Legendary, ItemType.Chestplate);
		setHealth(80).setDefense(16).setEarthElement(16).setHarvestingSpeed(160).setFortune(32);
	}

	@Override
	public int getMaxRunes() {
		return 8;
	}

}
