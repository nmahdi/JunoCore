package io.github.nmahdi.JunoCore.item.modifiers;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.crafting.Crafttable;
import io.github.nmahdi.JunoCore.item.crafting.Recipe;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.player.collection.Collection;
import org.bukkit.Material;

import java.util.ArrayList;

public class CompactedItem extends ResourceItem implements Crafttable {

	public CompactedItem(ItemManager itemManager, String id, String displayName, Material material, Rarity rarity, ItemType itemType, ResourceItem baseItem, int amount) {
		super(itemManager, id, displayName, material, rarity, itemType, baseItem.getCollection(), amount);
		recipes.add(new Recipe(itemManager.getCraftingManager(), this, 1, Recipe.Menu.Materials).addItems(baseItem, amount));
	}
}
