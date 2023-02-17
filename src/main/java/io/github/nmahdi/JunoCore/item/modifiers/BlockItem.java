package io.github.nmahdi.JunoCore.item.modifiers;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import org.bukkit.Material;

public class BlockItem extends GameItem {

	public BlockItem(ItemManager itemManager, String id, String displayName, Material material, Rarity rarity, ItemType itemType) {
		super(itemManager, id, displayName, material, rarity, itemType);
	}

}
