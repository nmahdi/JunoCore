package io.github.nmahdi.JunoCore.player.collection;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public enum Collection {
	//Mining
	Cobblestone("Cobblestone", Menu.Mining),
	Obsidian("Obsidian", Menu.Mining),
	Clay("Clay", Menu.Mining),
	Flint("Flint", Menu.Mining),
	Coal("Coal", Menu.Mining),
	Iron("Iron Bar", Menu.Mining),
	Gold("Gold Bar", Menu.Mining),
	Emerald("Emerald", Menu.Mining),
	Diamond("Diamond", Menu.Mining),
	Netherite("Netherite Bar", Menu.Mining),
	Eternite("Eternite Bar", Menu.Mining),

	//Woodcutting
	Oak("Oak Wood", Menu.Woodcutting),
	Spruce("Spruce Wood", Menu.Woodcutting),
	Birch("Birch Wood", Menu.Woodcutting),
	Jungle("Jungle Wood", Menu.Woodcutting),
	Acacia("Acacia Wood", Menu.Woodcutting),
	DarkOak("Dark Oak Wood", Menu.Woodcutting),
	Mangrove("Mangrove Wood", Menu.Woodcutting),

	//Foraging
	Poppy("Poppy", Menu.Foraging),

	//Fishing
	Cod("Cod", Menu.Fishing),
	Pufferfish("Pufferfish", Menu.Fishing),
	Sponge("Sponge", Menu.Fishing),

	//Combat
	RottenFlesh("Rotten Flesh", Menu.Combat),
	Bone("Bone", Menu.Combat),

	//Farming
	Beef("Beef", Menu.Farming),
	Leather("Leather", Menu.Farming),
	Chicken("Chicken", Menu.Farming),
	Feather("Feather", Menu.Farming),
	Mutton("Mutton", Menu.Farming),
	Wool("Wool", Menu.Farming),
	;

	private String name;
	private Menu menu;

	Collection(String name, Menu menu){
		this.name = name;
		this.menu = menu;
	}

	public String getName() {
		return name;
	}

	public Menu getMenu() {
		return menu;
	}

	public static enum Menu{
		Mining,
		Woodcutting,
		Foraging,
		Fishing,
		Farming,
		Combat,
		;

	}

}
