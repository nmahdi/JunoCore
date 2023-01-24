package io.github.nmahdi.JunoCore.player.collection;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public enum Collection {
	//Mining
	Cobblestone(new CollectionContainer("Cobblestone", Skill.Mining).addItem(GameItem.Cobblestone, 1).addItem(GameItem.CompactedCobblestone, 160)),
	Obsidian(new CollectionContainer("Obsidian", Skill.Mining).addItem(GameItem.Obsidian, 1).addItem(GameItem.CompactedObsidian, 160)),
	Clay(new CollectionContainer("Clay", Skill.Mining).addItem(GameItem.Clay, 1).addItem(GameItem.CompactedClay, 160)),
	Flint(new CollectionContainer("Flint", Skill.Mining).addItem(GameItem.Flint, 1).addItem(GameItem.CompactedFlint, 160)),
	Coal(new CollectionContainer("Coal", Skill.Mining).addItem(GameItem.Coal, 1).addItem(GameItem.CompactedCoal, 160).addItem(GameItem.Charcoal, 1)),
	IronBar(new CollectionContainer("Iron Bar", Skill.Mining).addItem(GameItem.IronBar, 1).addItem(GameItem.CompactedIronBar, 160)),
	GoldBar(new CollectionContainer("Gold Bar", Skill.Mining).addItem(GameItem.GoldBar, 1).addItem(GameItem.CompactedGoldBar, 160)),
	Emerald(new CollectionContainer("Emerald", Skill.Mining).addItem(GameItem.Emerald, 1).addItem(GameItem.CompactedEmerald, 160)),
	Diamond(new CollectionContainer("Diamond", Skill.Mining).addItem(GameItem.Diamond, 1).addItem(GameItem.CompactedDiamond, 160)),
	NetheriteBar(new CollectionContainer("Netherite Bar", Skill.Mining).addItem(GameItem.NetheriteBar, 1).addItem(GameItem.CompactedNetheriteBar, 160)),
	EterniteBar(new CollectionContainer("Eternite Bar", Skill.Mining).addItem(GameItem.EterniteBar, 1).addItem(GameItem.CompactedEterniteBar, 160)),

	//Woodcutting
	OakWood(new CollectionContainer("Oak Wood", Skill.Woodcutting).addItem(GameItem.OakWood, 1).addItem(GameItem.CompactedOakWood, 160)),
	SpruceWood(new CollectionContainer("Spruce Wood", Skill.Woodcutting).addItem(GameItem.SpruceWood, 1).addItem(GameItem.SpruceWood, 160)),

	//Foraging
	Poppy(new CollectionContainer("Poppy", Skill.Foraging).addItem(GameItem.Poppy, 1).addItem(GameItem.CompactedPoppy, 160)),

	//Fishing
	;

	private CollectionContainer container;

	Collection(CollectionContainer container){
		this.container = container;
	}

	public String getDisplayName() {
		return container.displayName;
	}

	public Skill getCategory() {
		return container.category;
	}

	public HashMap<GameItem, Integer> getItems() {
		return container.items;
	}

}
