package io.github.nmahdi.JunoCore.player.collection;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CollectionContainer {

	public String displayName;
	public Skill category;
	public HashMap<GameItem, Integer> items = new HashMap<>();

	public CollectionContainer(String displayName, Skill category) {
		this.displayName = displayName;
		this.category = category;
	}

	public CollectionContainer addItem(GameItem item, int amount){
		this.items.put(item, amount);
		return this;
	}

}
