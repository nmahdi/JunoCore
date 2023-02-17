package io.github.nmahdi.JunoCore.item;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.crafting.CraftingManager;
import io.github.nmahdi.JunoCore.item.items.LumberJackAxeItem;
import io.github.nmahdi.JunoCore.item.items.eternite.*;
import io.github.nmahdi.JunoCore.item.items.MagicWandItem;
import io.github.nmahdi.JunoCore.item.items.set.EterniteSet;
import io.github.nmahdi.JunoCore.item.items.set.SetEffect;
import io.github.nmahdi.JunoCore.item.modifiers.BlockItem;
import io.github.nmahdi.JunoCore.item.modifiers.CompactedItem;
import io.github.nmahdi.JunoCore.item.modifiers.ResourceItem;
import io.github.nmahdi.JunoCore.item.modifiers.stats.RuneItem;
import io.github.nmahdi.JunoCore.item.modifiers.stats.StatItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.player.collection.Collection;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemManager implements JunoManager {

	private boolean debugMode;
	private JCore main;
	private CraftingManager craftingManager = new CraftingManager();
	private ArrayList<GameItem> items = new ArrayList<>();
	private ArrayList<SetEffect> setEffects = new ArrayList<>();

	public ItemManager(JCore main){
		this.debugMode = main.getConfig().getBoolean("debug-mode.item");
		this.main = main;
		new EterniteSet(this, ETERNITE_HELMET, ETERNITE_CHESPLATE, ETERNITE_LEGGINGS, ETERNITE_BOOTS,
				ETERNITE_CAPE, ETERNITE_RING, ETERNITE_BRACELET, ETERNITE_HEADBAND, ETERNITE_NECKLACE);
		JLogger.log("Items have been initialized.");
	}

	public void addItem(GameItem item){
		this.items.add(item);
	}

	public GameItem getItem(String id){
		for(GameItem item : items){
			if(item.getId().equalsIgnoreCase(id)) return item;
		}
		return null;
	}

	public GameItem getItem(ItemStack stack){
		NBTGameItem nbt = new NBTGameItem(stack);
		if(nbt.hasJuno() && nbt.hasID()){
			for(GameItem item : items){
				if(item.getId().equalsIgnoreCase(nbt.getID())) return item;
			}
		}
		return null;
	}

	public ArrayList<GameItem> getItems() {
		return items;
	}

	public ArrayList<SetEffect> getSetEffects() {
		return setEffects;
	}

	public CraftingManager getCraftingManager() {
		return craftingManager;
	}

	@Override
	public void setDebugMode(boolean mode) {
		debugMode = mode;
	}

	@Override
	public boolean isDebugging() {
		return debugMode;
	}

	@Override
	public void onDisable() {

	}


	/**********Blocks*********/

	public BlockItem STONE = new BlockItem(this, "stone", "Stone", Material.STONE, Rarity.Common, ItemType.Block);
	public BlockItem ANDESITE = new BlockItem(this, "andesite", "Andesite", Material.ANDESITE, Rarity.Common, ItemType.Block);

	/**********Resources**********/

	//Ores
	public BlockItem COAL_ORE = new BlockItem(this, "coal_ore", "Coal Ore", Material.COAL_ORE, Rarity.Common, ItemType.Ore);
	public BlockItem IRON_ORE = new BlockItem(this, "iron_ore", "Iron Ore", Material.IRON_ORE, Rarity.Common, ItemType.Ore);
	public BlockItem GOLD_ORE = new BlockItem(this, "gold_ore", "Gold Ore", Material.GOLD_ORE, Rarity.Common, ItemType.Ore);
	public BlockItem EMERALD_ORE = new BlockItem(this, "emerald_ore", "Emerald Ore", Material.EMERALD_ORE, Rarity.Common, ItemType.Ore);
	public BlockItem DIAMOND_ORE = new BlockItem(this, "diamond_ore", "Diamond Ore", Material.DIAMOND_ORE, Rarity.Common, ItemType.Ore);
	public BlockItem NETHERITE_ORE = new BlockItem(this, "netherite_ore", "Netherite Ore", Material.ANCIENT_DEBRIS, Rarity.Common, ItemType.Ore);
	public BlockItem CLAY_BLOCK = new BlockItem(this, "clay_block", "Clay Block", Material.CLAY, Rarity.Common, ItemType.Block);
	public BlockItem GRAVEL = new BlockItem(this, "gravel", "Gravel", Material.GRAVEL, Rarity.Common, ItemType.Block);

	//Mining
	public ResourceItem COBBLESTONE = new ResourceItem(this, "cobblestone", "Cobblestone", Material.COBBLESTONE, Rarity.Common, ItemType.MiningResource, Collection.Cobblestone, 1);
	public CompactedItem COMPACTED_COBBLESTONE = new CompactedItem(this, "compacted_cobblestone", "Compacted Cobblestone", Material.COBBLESTONE, Rarity.Uncommon, ItemType.CompactedResource, COBBLESTONE, 160);

	public ResourceItem OBSIDIAN = new ResourceItem(this, "obsidian", "Obsidian", Material.OBSIDIAN, Rarity.Common, ItemType.MiningResource, Collection.Obsidian, 1);
	public CompactedItem COMPACTED_OBSIDIAN = new CompactedItem(this, "compacted_obsidian", "Compacted Obsidian", Material.OBSIDIAN, Rarity.Uncommon, ItemType.CompactedResource, OBSIDIAN, 160);

	public ResourceItem CLAY = new ResourceItem(this, "clay", "Clay", Material.CLAY_BALL, Rarity.Common, ItemType.MiningResource, Collection.Clay, 1);
	public CompactedItem COMPACTED_CLAY = new CompactedItem(this, "compacted_clay", "Compacted Clay", Material.CLAY_BALL, Rarity.Uncommon, ItemType.CompactedResource, CLAY, 160);

	public ResourceItem FLINT = new ResourceItem(this, "flint", "Flint", Material.FLINT, Rarity.Common, ItemType.MiningResource, Collection.Flint, 1);
	public CompactedItem COMPACTED_FLINT = new CompactedItem(this, "compacted_flint", "Compacted Flint", Material.FLINT, Rarity.Uncommon, ItemType.CompactedResource, FLINT, 160);

	public ResourceItem COAL = new ResourceItem(this, "coal", "Coal", Material.COAL, Rarity.Common, ItemType.MiningResource, Collection.Coal, 1);
	public CompactedItem COMPACTED_COAL = new CompactedItem(this, "compacted_coal", "Compacted Coal", Material.COAL, Rarity.Uncommon, ItemType.CompactedResource, COAL, 160);

	public ResourceItem IRON_BAR = new ResourceItem(this, "iron_bar", "Iron Bar", Material.IRON_INGOT, Rarity.Common, ItemType.MiningResource, Collection.Iron, 1);
	public CompactedItem COMPACTED_IRON_BAR = new CompactedItem(this, "compacted_iron_bar", "Compacted Iron Bar", Material.IRON_INGOT, Rarity.Uncommon, ItemType.CompactedResource, IRON_BAR, 160);

	public ResourceItem GOLD_BAR = new ResourceItem(this, "gold_bar", "Gold Bar", Material.GOLD_INGOT, Rarity.Common, ItemType.MiningResource, Collection.Gold, 1);
	public CompactedItem COMPACTED_GOLD_BAR = new CompactedItem(this, "compacted_gold_bar", "Compacted Gold Bar", Material.GOLD_INGOT, Rarity.Uncommon, ItemType.CompactedResource, GOLD_BAR, 160);

	public ResourceItem EMERALD = new ResourceItem(this, "emerald", "Emerald", Material.EMERALD, Rarity.Common, ItemType.MiningResource, Collection.Emerald, 1);
	public CompactedItem COMPACTED_EMERALD = new CompactedItem(this, "compacted_emerald", "Compacted Emerald", Material.EMERALD, Rarity.Uncommon, ItemType.CompactedResource, EMERALD, 160);

	public ResourceItem DIAMOND = new ResourceItem(this, "diamond", "Diamond", Material.DIAMOND, Rarity.Common, ItemType.MiningResource, Collection.Diamond, 1);
	public CompactedItem COMPACTED_DIAMOND = new CompactedItem(this, "compacted_diamond", "Compacted Diamond", Material.DIAMOND, Rarity.Uncommon, ItemType.CompactedResource, DIAMOND, 160);

	public ResourceItem NETHERITE_BAR = new ResourceItem(this, "netherite_bar", "Netherite Bar", Material.NETHERITE_INGOT, Rarity.Common, ItemType.MiningResource, Collection.Netherite, 1);
	public CompactedItem COMPACTED_NETHERITE_BAR = new CompactedItem(this, "compacted_netherite_bar", "Compacted Netherite Bar", Material.NETHERITE_INGOT, Rarity.Uncommon, ItemType.CompactedResource, NETHERITE_BAR, 160);

	public ResourceItem ETERNITE_BAR = new ResourceItem(this, "eternite_bar", "Eternite Bar", Material.NETHERITE_INGOT, Rarity.Common, ItemType.MiningResource, Collection.Eternite, 1);
	public CompactedItem COMPACTED_ETERNITE_BAR = new CompactedItem(this, "compacted_eternite_bar", "Compacted Eternite Bar", Material.NETHERITE_INGOT, Rarity.Uncommon, ItemType.CompactedResource, ETERNITE_BAR, 160);

	//Woodcutting
	public ResourceItem OAK_WOOD = new ResourceItem(this, "oak_wood", "Oak Wood", Material.OAK_LOG, Rarity.Common, ItemType.WoodcuttingResource, Collection.Oak, 1);
	public CompactedItem COMPACTED_OAK_WOOD = new CompactedItem(this, "compacted_oak_wood", "Compacted Oak Wood", Material.OAK_LOG, Rarity.Uncommon, ItemType.CompactedResource, OAK_WOOD, 160);

	public ResourceItem SPRUCE_WOOD = new ResourceItem(this, "spruce_wood", "Spruce Wood", Material.SPRUCE_LOG, Rarity.Common, ItemType.WoodcuttingResource, Collection.Spruce, 1);
	public CompactedItem COMPACTED_SPRUCE_WOOD = new CompactedItem(this, "compacted_spruce_wood", "Compacted Spruce Wood", Material.SPRUCE_LOG, Rarity.Uncommon, ItemType.WoodcuttingResource, SPRUCE_WOOD, 160);

	public ResourceItem BIRCH_WOOD = new ResourceItem(this, "birch_wood", "Birch Wood", Material.BIRCH_LOG, Rarity.Common, ItemType.WoodcuttingResource, Collection.Birch, 1);
	public CompactedItem COMPACTED_BIRCH_WOOD = new CompactedItem(this, "compacted_birch_wood", "Compacted Birch Wood", Material.BIRCH_LOG, Rarity.Uncommon, ItemType.WoodcuttingResource, BIRCH_WOOD, 160);

	public ResourceItem JUNGLE_WOOD = new ResourceItem(this, "jungle_wood", "Jungle Wood", Material.JUNGLE_LOG, Rarity.Common, ItemType.WoodcuttingResource, Collection.Jungle, 1);
	public CompactedItem COMPACTED_JUNGLE_WOOD = new CompactedItem(this, "compacted_jungle_wood", "Compacted Jungle Wood", Material.JUNGLE_LOG, Rarity.Uncommon, ItemType.WoodcuttingResource, JUNGLE_WOOD, 160);

	public ResourceItem ACACIA_WOOD = new ResourceItem(this, "acacia_wood", "Acacia Wood", Material.ACACIA_LOG, Rarity.Common, ItemType.WoodcuttingResource, Collection.Acacia, 1);
	public CompactedItem COMPACTED_ACACIA_WOOD = new CompactedItem(this, "compacted_acacia_wood", "Compacted Acacia Wood", Material.ACACIA_LOG, Rarity.Uncommon, ItemType.WoodcuttingResource, ACACIA_WOOD, 160);

	public ResourceItem DARK_OAK_WOOD = new ResourceItem(this, "dark_oak_wood", "Dark Oak Wood", Material.DARK_OAK_LOG, Rarity.Common, ItemType.WoodcuttingResource, Collection.DarkOak, 1);
	public CompactedItem COMPACTED_DARK_OAK_WOOD = new CompactedItem(this, "compacted_dark_oak_wood", "Compacted Dark Oak Wood", Material.DARK_OAK_LOG, Rarity.Uncommon, ItemType.WoodcuttingResource, DARK_OAK_WOOD, 160);

	public ResourceItem MANGROVE_WOOD = new ResourceItem(this, "mangrove_wood", "Mangrove Wood", Material.MANGROVE_LOG, Rarity.Common, ItemType.WoodcuttingResource, Collection.Mangrove, 1);
	public CompactedItem COMPACTED_MANGROVE_WOOD = new CompactedItem(this, "compacted_mangrove_wood", "Compacted Mangrove Wood", Material.MANGROVE_LOG, Rarity.Uncommon, ItemType.WoodcuttingResource, MANGROVE_WOOD, 160);


	//Foraging
	public ResourceItem POPPY = new ResourceItem(this,"poppy", "Poppy", Material.POPPY, Rarity.Common, ItemType.ForagingResource, Collection.Poppy, 1);
	public CompactedItem COMPACTED_POPPY = new CompactedItem(this, "compacted_poppy", "Compacted Poppy", Material.POPPY, Rarity.Uncommon, ItemType.ForagingResource, POPPY, 160);

	//Fishing
	public ResourceItem COD = new ResourceItem(this, "cod", "Cod", Material.COD, Rarity.Common, ItemType.FishingResource, Collection.Cod, 1);
	public CompactedItem COMPACTED_COD = new CompactedItem(this, "compacted_cod", "Compacted Cod", Material.COD, Rarity.Uncommon, ItemType.FishingResource, COD, 160);

	public ResourceItem PUFFERFISH = new ResourceItem(this, "pufferfish", "Pufferfish", Material.PUFFERFISH, Rarity.Common, ItemType.FishingResource, Collection.Pufferfish, 1);
	public CompactedItem COMPACTED_PUFFERFISH = new CompactedItem(this, "compacted_pufferfish", "Compacted Pufferfish", Material.PUFFERFISH, Rarity.Uncommon, ItemType.FishingResource, PUFFERFISH, 160);

	public ResourceItem SPONGE = new ResourceItem(this, "sponge", "Sponge", Material.SPONGE, Rarity.Common, ItemType.FishingResource, Collection.Sponge, 1);
	public CompactedItem COMPACTED_SPONGE = new CompactedItem(this, "compacted_sponge", "Compacted Sponge", Material.SPONGE, Rarity.Uncommon, ItemType.FishingResource, SPONGE, 160);

	//Combat
	public ResourceItem ROTTEN_FLESH = new ResourceItem(this, "rotten_flesh", "Rotten Flesh", Material.ROTTEN_FLESH, Rarity.Common, ItemType.CombatResource, Collection.RottenFlesh, 1);
	public CompactedItem COMPACTED_ROTTEN_FLESH = new CompactedItem(this, "compacted_rotten_flesh", "Compacted Rotten Flesh", Material.ROTTEN_FLESH, Rarity.Uncommon, ItemType.CombatResource, ROTTEN_FLESH, 160);

	public ResourceItem BONE = new ResourceItem(this, "bone", "Bone", Material.BONE, Rarity.Common, ItemType.CombatResource, Collection.Bone, 1);
	public CompactedItem COMPACTED_BONE = new CompactedItem(this, "compacted_bone", "Compacted Bone", Material.BONE, Rarity.Uncommon, ItemType.CombatResource, BONE, 160);


	//Farming
	public ResourceItem BEEF = new ResourceItem(this, "beef", "Beef", Material.BEEF, Rarity.Common, ItemType.FarmingResource, Collection.Beef, 1);
	public CompactedItem COMPACTED_BEEF = new CompactedItem(this, "compacted_beef", "Compacted Beef", Material.BEEF, Rarity.Uncommon, ItemType.FarmingResource, BEEF, 160);

	public ResourceItem LEATHER = new ResourceItem(this, "leather", "Leather", Material.LEATHER, Rarity.Common, ItemType.FarmingResource, Collection.Leather, 1);
	public CompactedItem COMPACTED_LEATHER = new CompactedItem(this, "compacted_leather", "Compacted Leather", Material.LEATHER, Rarity.Uncommon, ItemType.FarmingResource, LEATHER, 160);

	public ResourceItem CHICKEN = new ResourceItem(this, "chicken", "Chicken", Material.CHICKEN, Rarity.Common, ItemType.FarmingResource, Collection.Chicken, 1);
	public CompactedItem COMPACTED_CHICKEN = new CompactedItem(this, "compacted_chicken", "Compacted Chicken", Material.CHICKEN, Rarity.Uncommon, ItemType.FarmingResource, CHICKEN, 160);

	public ResourceItem FEATHER = new ResourceItem(this, "feather", "Feather", Material.FEATHER, Rarity.Common, ItemType.FarmingResource, Collection.Feather, 1);
	public CompactedItem COMPACTED_FEATHER = new CompactedItem(this, "compacted_feather", "Compacted Feather", Material.FEATHER, Rarity.Uncommon, ItemType.FarmingResource, FEATHER, 160);

	public ResourceItem MUTTON = new ResourceItem(this, "mutton", "Mutton", Material.MUTTON, Rarity.Common, ItemType.FarmingResource, Collection.Mutton, 1);
	public CompactedItem COMPACTED_MUTTON = new CompactedItem(this, "compacted_mutton", "Compacted Mutton", Material.MUTTON, Rarity.Uncommon, ItemType.FarmingResource, MUTTON, 160);

	public ResourceItem WOOL = new ResourceItem(this, "wool", "Wool", Material.WHITE_WOOL, Rarity.Common, ItemType.FarmingResource, Collection.Wool, 1);
	public CompactedItem COMPACTED_WOOL = new CompactedItem(this, "compacted_wool", "Compacted Wool", Material.WHITE_WOOL, Rarity.Uncommon, ItemType.FarmingResource, WOOL, 160);

	/**********Mob Drops**********/
	public GameItem ZOMBIE_ESSENCE = new GameItem(this, "zombie_essence", "Zombie Essence", Material.ENDER_EYE, Rarity.Rare, ItemType.MobDrop);
	public GameItem SKELETON_ESSENCE = new GameItem(this, "skeleton_essence", "Skeleton Essence", Material.ENDER_EYE, Rarity.Rare, ItemType.MobDrop);
	public GameItem CAVE_DWELLER_ESSENCE = new GameItem(this, "cave_dweller_essence", "Cave Dweller Essence", Material.ENDER_EYE, Rarity.Rare, ItemType.MobDrop);

	/**********Materials**********/
	public GameItem MAGIC_STONE = new GameItem(this, "magic_stone", "Magic Stone", Material.AMETHYST_SHARD, Rarity.Rare, ItemType.Resource);

	public GameItem LOW_QUALITY_GEODE = new GameItem(this, "low_quality_geode", "Low Quality Geode", Material.STONE_BUTTON, Rarity.Common, ItemType.Misc);
	public GameItem FAIR_QUALITY_GEODE = new GameItem(this, "fair_quality_geode", "Fair Quality Geode", Material.STONE_BUTTON, Rarity.Uncommon, ItemType.Misc);
	public GameItem HIGH_QUALITY_GEODE = new GameItem(this, "high_quality_geode", "High Quality Geode", Material.STONE_BUTTON, Rarity.Rare, ItemType.Misc);

	/**********Runes**********/
	public RuneItem EFFICIENCY_RUNE = new RuneItem(this, "efficiency_rune", "Efficiency Rune", Material.PAPER, Rarity.Uncommon, Rune.Efficiency, 1);
	public RuneItem FORTUNE_RUNE = new RuneItem(this, "fortune_rune", "Fortune Rune", Material.PAPER, Rarity.Uncommon, Rune.Fortune, 1);
	public RuneItem SMELTING_RUNE = new RuneItem(this, "smelting_rune", "Smelting Rune", Material.PAPER, Rarity.Uncommon, Rune.Smelting, 1);
	public RuneItem SILK_TOUCH_RUNE = new RuneItem(this, "silk_touch_rune", "Silk Touch Rune", Material.PAPER, Rarity.Uncommon, Rune.Silktouch, 1);
	public RuneItem STRENGTH_RUNE = new RuneItem(this, "strength_rune", "Strength Rune", Material.PAPER, Rarity.Uncommon, Rune.Strength, 1);
	public RuneItem CRIT_DAMAGE_RUNE = new RuneItem(this, "crit_damage_rune", "Crit Damage Rune", Material.PAPER, Rarity.Uncommon, Rune.CritDamage, 1);
	public RuneItem CRIT_CHANCE_RUNE = new RuneItem(this, "crit_chance_rune", "Crit Chance Rune", Material.PAPER, Rarity.Uncommon, Rune.CritChance, 1);
	public RuneItem DEFENSE_RUNE = new RuneItem(this, "defense_rune", "Defense Rune", Material.PAPER, Rarity.Uncommon, Rune.Defense, 1);
	public RuneItem MANA_RUNE = new RuneItem(this, "mana_rune", "Mana Rune", Material.PAPER, Rarity.Uncommon, Rune.Mana, 1);
	public RuneItem HEALTH_RUNE = new RuneItem(this, "health_rune", "Health Rune", Material.PAPER, Rarity.Uncommon, Rune.Health, 1);

	/**********Weapons**********/
	public MagicWandItem MAGIC_WAND = new MagicWandItem(this);
	public StatItem ADMIN_SCYTHE = new StatItem(this, "admin_scythe", "Admin Scythe", Material.NETHERITE_HOE, Rarity.Mythic, ItemType.Sword)
			.setDamage(10000000);

	/**********Tools**********/
	public EternitePickaxeItem ETERNITE_PICKAXE = new EternitePickaxeItem(this);

	public LumberJackAxeItem LUMBER_JACK_AXE = new LumberJackAxeItem(this);
	//Fishing Rod
	//Healing Wand


	/**********Armor & Equipment**********/

	//Eternite
	public EterniteHelmetItem ETERNITE_HELMET = new EterniteHelmetItem(this);
	public EterniteChestplateItem ETERNITE_CHESPLATE = new EterniteChestplateItem(this);
	public EterniteLeggingsItem ETERNITE_LEGGINGS = new EterniteLeggingsItem(this);
	public EterniteBootsItem ETERNITE_BOOTS = new EterniteBootsItem(this);

	public EterniteCapeItem ETERNITE_CAPE = new EterniteCapeItem(this);
	public EterniteRingItem ETERNITE_RING = new EterniteRingItem(this);
	public EterniteBraceletItem ETERNITE_BRACELET = new EterniteBraceletItem(this);
	public EterniteNecklaceItem ETERNITE_NECKLACE = new EterniteNecklaceItem(this);
	public EterniteHeadbandItem ETERNITE_HEADBAND = new EterniteHeadbandItem(this);

}
