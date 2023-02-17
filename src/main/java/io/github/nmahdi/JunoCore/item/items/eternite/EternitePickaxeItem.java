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

public class EternitePickaxeItem extends StatItem implements Runeable, Crafttable {

	public EternitePickaxeItem(ItemManager itemManager) {
		super(itemManager, "eternite_pickaxe", "Eternite Pickaxe", Material.NETHERITE_PICKAXE, Rarity.Legendary, ItemType.Pickaxe);

		setHarvestingSpeed(100);
		setFortune(10);

		recipes.add(new Recipe(itemManager.getCraftingManager(), this, 1, Recipe.Menu.Tools).addItems(itemManager.ETERNITE_BAR, 3));
	}

	@Override
	public int getMaxRunes() {
		return 5;
	}

}
