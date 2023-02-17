package io.github.nmahdi.JunoCore.item.modifiers;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.modifiers.stats.CollectionItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.player.collection.Collection;
import org.bukkit.Material;

public class ResourceItem extends GameItem implements CollectionItem {

	private Collection collection;
	private int collectionAmount;

	public ResourceItem(ItemManager itemManager, String id, String displayName, Material material, Rarity rarity, ItemType itemType, Collection collection, int collectionAmount) {
		super(itemManager, id, displayName, material, rarity, itemType);
		this.collection = collection;
		this.collectionAmount = collectionAmount;
	}

	@Override
	public Collection getCollection() {
		return collection;
	}

	@Override
	public int getCollectionAmount() {
		return collectionAmount;
	}
}
