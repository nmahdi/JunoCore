package io.github.nmahdi.JunoCore.item;

import io.github.nmahdi.JunoCore.item.ability.equipment.EquipmentAbility;
import io.github.nmahdi.JunoCore.item.ability.item.ItemAbility;
import io.github.nmahdi.JunoCore.item.builder.CustomModelData;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.loot.LootTable;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public enum GameItem {

	//CopperOre(new ItemContainer("copper_ore", "Copper Ore", Material.COPPER_ORE, Rarity.Common, ItemType.Block)),
	//LapisLazuliOre(new ItemContainer("lapis_lazuli_ore", "Lapis Lazuli Ore", Material.LAPIS_ORE, Rarity.Common, ItemType.Block)),
	//RedstoneOre(new ItemContainer("redstone_ore", "Redstone Ore", Material.REDSTONE_ORE, Rarity.Common, ItemType.Block)),
	//QuartzOre(new ItemContainer("quartz_ore", "Quartz Ore", Material.NETHER_QUARTZ_ORE, Rarity.Common, ItemType.Block)),
	//EnderPearl(new ItemContainer("ender_pearl", "Ender Pearl", Material.ENDER_PEARL, Rarity.Common, ItemType.Misc)),
	//Gunpowder(new ItemContainer("gunpowder", "Gun Powder", Material.GUNPOWDER, Rarity.Common, ItemType.Misc)),
	//GhastTear(new ItemContainer("ghast_tear", "Ghast Tear", Material.GHAST_TEAR, Rarity.Common, ItemType.Misc)),
	//BlazeRod(new ItemContainer("blaze_rod", "Blaze Rod", Material.BLAZE_ROD, Rarity.Common, ItemType.Misc)),
	//SpiderEye(new ItemContainer("spider_eye", "Spider Eye", Material.SPIDER_EYE, Rarity.Common, ItemType.Misc)),
	//String(new ItemContainer("string", "String", Material.STRING, Rarity.Common, ItemType.Misc)),
	//RabbitHide(new ItemContainer("rabbit_hide", "Rabbit Hide", Material.RABBIT_HIDE, Rarity.Common, ItemType.Misc)),
	//MagmaCream(new ItemContainer("magma_cream", "Magma Cream", Material.MAGMA_CREAM, Rarity.Common, ItemType.Misc)),
	//SlimeBall(new ItemContainer("slime_ball", "Slime Ball", Material.SLIME_BALL, Rarity.Common, ItemType.Misc)),
	//LapisLazuli(new ItemContainer("lapis_lazuli", "Lapis Lazuli", Material.LAPIS_LAZULI, Rarity.Common, ItemType.Misc)),
	//Redstone(new ItemContainer("redstone", "Redstone", Material.REDSTONE, Rarity.Common, ItemType.Misc)),
	//Quartz(new ItemContainer("quartz", "Quartz", Material.QUARTZ, Rarity.Common, ItemType.Misc)),
	//CopperBar(new ItemContainer("copper_bar", "Copper Bar", Material.COPPER_INGOT, Rarity.Common, ItemType.Misc)),

	/*RookieHelmet(new ItemContainer("rookie_helmet", "Rookie Helmet", Material.IRON_BLOCK, Rarity.Rare, ItemType.Helmet)
			.setRuneSlots(5).setDismantlable(LootTable.RareEquipment)
			.setHealth(10).setDefense(20)
			.setFireElement(20).setWaterElement(20).setLightningElement(20).setIceElement(20)
			.addDescription(Component.text("A helmet that the Ultimate Rookie God wore!").color(TextColors.GRAY_DESCRIPTION))),

	RookieChestplate(new ItemContainer("rookie_chestplate", "Rookie Chestplate", Material.IRON_CHESTPLATE, Rarity.Rare, ItemType.Chestplate)
			.setRuneSlots(5).setDismantlable(LootTable.RareEquipment)
			.setHealth(20).setDefense(30)
			.setFireElement(30).setWaterElement(30).setLightningElement(30).setIceElement(30)
			.addDescription(Component.text("A chestplate that the Ultimate Rookie God wore!").color(TextColors.GRAY_DESCRIPTION))),

	RookieLeggings(new ItemContainer("rookie_leggings", "Rookie Leggings", Material.IRON_LEGGINGS, Rarity.Rare, ItemType.Leggings)
			.setRuneSlots(5).setDismantlable(LootTable.RareEquipment)
			.setHealth(15).setDefense(25)
			.setFireElement(25).setWaterElement(25).setLightningElement(25).setIceElement(25)
			.addDescription(Component.text("Leggings that the Ultimate Rookie God wore!").color(TextColors.GRAY_DESCRIPTION))),

	RookieBoots(new ItemContainer("rookie_boots", "Rookie Boots", Material.IRON_BOOTS, Rarity.Rare, ItemType.Boots)
			.setRuneSlots(5).setDismantlable(LootTable.RareEquipment)
			.setHealth(5).setDefense(15)
			.setFireElement(15).setWaterElement(15).setLightningElement(15).setIceElement(15)
			.addDescription(Component.text("Boots that the Ultimate Rookie God wore!").color(TextColors.GRAY_DESCRIPTION))),

	RookieRing(new ItemContainer("rookie_ring", "Rookie Ring", Material.IRON_NUGGET, Rarity.Rare, ItemType.Ring)
			.setRuneSlots(5).setHealth(5).setDefense(15).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A ring that the Ultimate Rookie God wore!").color(TextColors.GRAY_DESCRIPTION))),

	RookieBracelet(new ItemContainer("rookie_bracelet", "Rookie Bracelet", Material.STRING, Rarity.Rare, ItemType.Bracelet)
			.setRuneSlots(5).setHealth(5).setDefense(15).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A bracelet that the Ultimate Rookie God wore!").color(TextColors.GRAY_DESCRIPTION))),

	RookieNecklace(new ItemContainer("rookie_necklace", "Rookie Necklace", Material.REDSTONE, Rarity.Rare, ItemType.Necklace)
			.setRuneSlots(5).setHealth(5).setDefense(15).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A necklace that the Ultimate Rookie God wore!").color(TextColors.GRAY_DESCRIPTION))),

	RookieHeadband(new ItemContainer("rookie_headband", "Rookie Headband", Material.CARROT, Rarity.Rare, ItemType.Headband)
			.setRuneSlots(5).setHealth(5).setDefense(15).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A headband that the Ultimate Rookie God wore!").color(TextColors.GRAY_DESCRIPTION))),

	RookieCape(new ItemContainer("rookie_cape", "Rookie Cape", Material.DIAMOND, Rarity.Rare, ItemType.Cape)
			.setRuneSlots(5).setHealth(5).setDefense(15).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A cape that the Ultimate Rookie God wore!").color(TextColors.GRAY_DESCRIPTION))), */
	/*RookieSword(new ItemContainer("rookie_sword", "Rookie Sword", Material.IRON_SWORD, Rarity.Rare, ItemType.Sword)
			.setRuneSlots(5).setDamage(30).setStrength(10).setCritChance(10).setCritDamage(100).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A sword that the Ultimate Rookie God wielded!").color(TextColors.GRAY_DESCRIPTION))), */

	/*********************************************
	 					Blocks
	 *********************************************/

	Stone(new ItemContainer("stone", "Stone Block", Material.STONE, Rarity.Common, ItemType.Block)),
	Andesite(new ItemContainer("andesite", "Andesite", Material.ANDESITE, Rarity.Common, ItemType.Block)),

	/*********************************************
	 				Resources
	 *********************************************/

	//Ores
	CoalOre(new ItemContainer("coal_ore", "Coal Ore", Material.COAL_ORE, Rarity.Common, ItemType.Ore)),
	IronOre(new ItemContainer("iron_ore", "Iron Ore", Material.IRON_ORE, Rarity.Common, ItemType.Ore)),
	GoldOre(new ItemContainer("gold_ore", "Gold Ore", Material.GOLD_ORE, Rarity.Common, ItemType.Ore)),
	EmeraldOre(new ItemContainer("emerald_ore", "Emerald Ore", Material.EMERALD_ORE, Rarity.Common, ItemType.Ore)),
	DiamondOre(new ItemContainer("diamond_ore", "Diamond Ore", Material.DIAMOND_ORE, Rarity.Common, ItemType.Ore)),
	NetheriteOre(new ItemContainer("netherite_ore", "Netherite Ore", Material.ANCIENT_DEBRIS, Rarity.Common, ItemType.Ore)),
	ClayBlock(new ItemContainer("clay_block", "Clay Block", Material.CLAY, Rarity.Common, ItemType.Ore)),
	Gravel(new ItemContainer("gravel", "Gravel", Material.GRAVEL, Rarity.Common, ItemType.Ore)),

	//Mining
	Cobblestone(new ItemContainer("cobblestone", "Cobblestone", Material.COBBLESTONE, Rarity.Common, ItemType.MiningResource)),
	CompactedCobblestone(new ItemContainer("compacted_cobblestone", "Compacted Cobblestone", Material.COBBLESTONE, Rarity.Uncommon, ItemType.CompactedResource)),

	Obsidian(new ItemContainer("obsidian", "Obsidian", Material.OBSIDIAN, Rarity.Common, ItemType.MiningResource)),
	CompactedObsidian(new ItemContainer("compacted_obsidian", "Compacted Obsidian", Material.OBSIDIAN, Rarity.Uncommon, ItemType.CompactedResource)),

	Clay(new ItemContainer("clay", "Clay", Material.CLAY_BALL, Rarity.Common, ItemType.MiningResource)),
	CompactedClay(new ItemContainer("compacted_clay", "Compacted Clay", Material.CLAY_BALL, Rarity.Uncommon, ItemType.CompactedResource)),

	Flint(new ItemContainer("flint", "Flint", Material.FLINT, Rarity.Common, ItemType.MiningResource)),
	CompactedFlint(new ItemContainer("compacted_flint", "Compacted Flint", Material.FLINT, Rarity.Uncommon, ItemType.CompactedResource)),

	Coal(new ItemContainer("coal", "Coal", Material.COAL, Rarity.Common, ItemType.MiningResource)),
	CompactedCoal(new ItemContainer("compacted_coal", "Compacted Coal", Material.COAL, Rarity.Uncommon, ItemType.CompactedResource)),
	Charcoal(new ItemContainer("charcoal", "Charcoal", Material.CHARCOAL, Rarity.Common, ItemType.MiningResource)),

	IronBar(new ItemContainer("iron", "Iron Bar", Material.IRON_INGOT, Rarity.Common, ItemType.MiningResource)),
	CompactedIronBar(new ItemContainer("compacted_iron", "Compacted Iron Bar", Material.IRON_INGOT, Rarity.Uncommon, ItemType.CompactedResource)),

	GoldBar(new ItemContainer("gold", "Gold Bar", Material.GOLD_INGOT, Rarity.Common, ItemType.MiningResource)),
	CompactedGoldBar(new ItemContainer("compacted_gold", "Compacted Gold Bar", Material.GOLD_INGOT, Rarity.Uncommon, ItemType.CompactedResource)),

	Emerald(new ItemContainer("emerald", "Emerald", Material.EMERALD, Rarity.Common, ItemType.MiningResource)),
	CompactedEmerald(new ItemContainer("compacted_emerald", "Compacted Emerald", Material.EMERALD, Rarity.Uncommon, ItemType.CompactedResource)),

	Diamond(new ItemContainer("diamond", "Diamond", Material.DIAMOND, Rarity.Common, ItemType.MiningResource)),
	CompactedDiamond(new ItemContainer("compacted_diamond", "Compacted Diamond", Material.DIAMOND, Rarity.Uncommon, ItemType.CompactedResource)),

	NetheriteBar(new ItemContainer("netherite", "Netherite Bar", Material.NETHERITE_INGOT, Rarity.Common, ItemType.MiningResource)),
	CompactedNetheriteBar(new ItemContainer("compacted_netherite", "Compacted Netherite Bar", Material.NETHERITE_INGOT, Rarity.Uncommon, ItemType.CompactedResource)),

	EterniteBar(new ItemContainer("eternite", "Eternite Bar", Material.NETHERITE_INGOT, Rarity.Uncommon, ItemType.MiningResource)),
	CompactedEterniteBar(new ItemContainer("compacted_eternite", "Compacted Eternite Bar", Material.NETHERITE_INGOT, Rarity.Rare, ItemType.CompactedResource)),

	//Woodcutting
	OakWood(new ItemContainer("oak_wood", "Oak Wood", Material.OAK_WOOD, Rarity.Common, ItemType.WoodcuttingResource)),
	CompactedOakWood(new ItemContainer("compacted_oak_wood", "Compacted Oak Wood", Material.OAK_WOOD, Rarity.Uncommon, ItemType.CompactedResource)),

	SpruceWood(new ItemContainer("spruce_wood", "Spruce Wood", Material.SPRUCE_WOOD, Rarity.Common, ItemType.WoodcuttingResource)),
	CompactedSpruceWood(new ItemContainer("compacted_spruce_wood", "Compacted Spruce Wood", Material.SPRUCE_WOOD, Rarity.Uncommon, ItemType.CompactedResource)),

	BirchWood(new ItemContainer("birch_wood", "Birch wood", Material.BIRCH_WOOD, Rarity.Common, ItemType.WoodcuttingResource)),
	CompactedBirchWood(new ItemContainer("compacted_birch_wood", "Compacted Birch Wood", Material.BIRCH_WOOD, Rarity.Uncommon, ItemType.WoodcuttingResource)),

	JungleWood(new ItemContainer("jungle_wood", "Jungle wood", Material.JUNGLE_WOOD, Rarity.Common, ItemType.WoodcuttingResource)),
	CompactedJungleWood(new ItemContainer("compacted_jungle_wood", "Compacted Jungle Wood", Material.JUNGLE_WOOD, Rarity.Uncommon, ItemType.CompactedResource)),

	AcaciaWood(new ItemContainer("acacia_wood", "Acacia wood", Material.ACACIA_WOOD, Rarity.Common, ItemType.WoodcuttingResource)),
	CompactedAcaciaWood(new ItemContainer("compacted_acacia_wood", "Compacted Acacia Wood", Material.ACACIA_WOOD, Rarity.Uncommon, ItemType.CompactedResource)),

	DarkOakWood(new ItemContainer("dark_oak_wood", "Dark Oak wood", Material.DARK_OAK_WOOD, Rarity.Common, ItemType.WoodcuttingResource)),
	CompactedDarkOakWood(new ItemContainer("compacted_dark_oak_wood", "Compacted Dark Oak Wood", Material.DARK_OAK_WOOD, Rarity.Uncommon, ItemType.CompactedResource)),

	MangroveWood(new ItemContainer("mangrove_wood", "Mangrove wood", Material.MANGROVE_WOOD, Rarity.Common, ItemType.WoodcuttingResource)),
	CompactedMangroveWood(new ItemContainer("compacted_mangrove_wood", "Compacted Mangrove Wood", Material.MANGROVE_WOOD, Rarity.Uncommon, ItemType.CompactedResource)),

	//Foraging
	Poppy(new ItemContainer("poppy", "Poppy", Material.POPPY, Rarity.Common, ItemType.ForagingResource)),
	CompactedPoppy(new ItemContainer("compacted_poppy", "Compacted Poppy", Material.POPPY, Rarity.Uncommon, ItemType.CompactedResource)),

	//Fishing
	Cod(new ItemContainer("cod", "Cod", Material.COD, Rarity.Common, ItemType.FishingResource)),
	CompactedCod(new ItemContainer("compacted_cod", "Compacted Cod", Material.COD, Rarity.Uncommon, ItemType.CompactedResource)),

	Pufferfish(new ItemContainer("pufferfish", "Pufferfish", Material.PUFFERFISH, Rarity.Common, ItemType.FishingResource)),
	CompactedPufferfish(new ItemContainer("compacted_pufferfish", "Compacted Pufferfish", Material.PUFFERFISH, Rarity.Uncommon, ItemType.CompactedResource)),

	Sponge(new ItemContainer("sponge", "Sponge", Material.SPONGE, Rarity.Common, ItemType.FishingResource)),
	CompactedSponge(new ItemContainer("compacted_sponge", "Compacted Sponge", Material.SPONGE, Rarity.Uncommon, ItemType.CompactedResource)),

	//Combat
	RottenFlesh(new ItemContainer("rotten_flesh", "Rotten Flesh", Material.ROTTEN_FLESH, Rarity.Common, ItemType.CombatResource)),
	CompactedRottenFlesh(new ItemContainer("compacted_rotten_flesh", "Compacted Rotten Flesh", Material.ROTTEN_FLESH, Rarity.Uncommon, ItemType.CompactedResource)),

	Bone(new ItemContainer("bone", "Bone", Material.BONE, Rarity.Common, ItemType.CombatResource)),
	CompactedBone(new ItemContainer("compacted_bone", "Compacted Bone", Material.BONE, Rarity.Uncommon, ItemType.CompactedResource)),

	Beef(new ItemContainer("beef", "Beef", Material.BEEF, Rarity.Common, ItemType.FarmingResource)),
	CompactedBeef(new ItemContainer("compacted_beef", "Compacted Beef", Material.BEEF, Rarity.Uncommon, ItemType.CompactedResource)),

	Leather(new ItemContainer("leather", "Leather", Material.LEATHER, Rarity.Common, ItemType.FarmingResource)),
	CompactedLeather(new ItemContainer("compacted_leather", "Compacted Leather", Material.LEATHER, Rarity.Uncommon, ItemType.CompactedResource)),

	Chicken(new ItemContainer("chicken", "Chicken", Material.CHICKEN, Rarity.Common, ItemType.FarmingResource)),
	CompactedChicken(new ItemContainer("compacted_chicken", "Compacted Chicken", Material.CHICKEN, Rarity.Uncommon, ItemType.CompactedResource)),

	Feather(new ItemContainer("feather", "Feather", Material.FEATHER, Rarity.Common, ItemType.FarmingResource)),
	CompactedFeather(new ItemContainer("compacted_feather", "Compacted Feather", Material.FEATHER, Rarity.Uncommon, ItemType.CompactedResource)),

	Mutton(new ItemContainer("mutton", "Mutton", Material.MUTTON, Rarity.Common, ItemType.FarmingResource)),
	CompactedMutton(new ItemContainer("compacted_mutton", "Compacted Mutton", Material.MUTTON, Rarity.Uncommon, ItemType.CompactedResource)),

	Wool(new ItemContainer("wool", "Wool", Material.WHITE_WOOL, Rarity.Common, ItemType.FarmingResource)),
	CompactedWool(new ItemContainer("compacted_wool", "Compacted Wool", Material.WHITE_WOOL, Rarity.Uncommon, ItemType.CompactedResource)),

	/*********************************************
	 				Mob Drops
	 *********************************************/
	ZombieEssence(new ItemContainer("zombie_essence", "Zombie Essence", Material.ENDER_EYE, Rarity.Rare, ItemType.Resource)),
	SkeletonEssence(new ItemContainer("skeleton_essence", "Skeleton Essence", Material.ENDER_EYE, Rarity.Rare, ItemType.Resource)),
	CaveDwellerEssence(new ItemContainer("cave_dweller_essence", "Cave Dweller Essence", Material.ENDER_EYE, Rarity.Rare, ItemType.Resource)),

	/*********************************************
	 					Items
	 *********************************************/
	//Misc
	Stick(new ItemContainer("stick", "Stick", Material.STICK, Rarity.Common, ItemType.Misc)),

	MagicStone(new ItemContainer("magic_stone", "Magic Stone", Material.AMETHYST_SHARD, Rarity.Rare, ItemType.Misc)
			.addDescription("A magical crystal that can be used to upgrade items!")),

	//Geodes
	LowQualityGeode(new ItemContainer("low_quality_geode", "Low Quality Geode", Material.STONE, Rarity.Common, ItemType.Misc)
			.setCustomModelData(CustomModelData.LOW_QUALITY_GEODE).setDismantlable(LootTable.LowQualityGeode)
			.addDescription("The most common type of geode. Bring this to a blacksmith to break open for a chance to drop quality loot.")),

	FairQualityGeode(new ItemContainer("fair_quality_geode", "Fair Quality Geode", Material.STONE, Rarity.Uncommon, ItemType.Misc)
			.setCustomModelData(CustomModelData.FAIR_QUALITY_GEODE).setDismantlable(LootTable.FairQualityGeode)
			.addDescription("An uncommon type of geode. Bring this to a blacksmith to break open for a chance to drop quality loot.")),

	HighQualityGeode(new ItemContainer("high_quality_geode", "High Quality Geode", Material.STONE, Rarity.Rare, ItemType.Misc)
			.setCustomModelData(CustomModelData.HIGH_QUALITY_GEODE).setDismantlable(LootTable.HighQualityGeode)
			.addDescription("The rarest type of geode. Bring this to a blacksmith to break open for a chance to drop quality loot.")),


	//Runes
	EfficiencyRune(new ItemContainer("efficiency_rune", "Efficiency Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Efficiency)
			.addDescription("Apply to any harvesting tools at the rune station.")),

	FortuneRune(new ItemContainer("fortune_rune", "Fortune Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Fortune)
			.addDescription("Apply to any harvesting tools at the rune station.")),

	SmeltingRune(new ItemContainer("smelting_rune", "Smelting Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Smelting)
			.addDescription("Apply to mining & woodcutting tools at the rune station.")),

	SilktouchRune(new ItemContainer("silktouch_rune", "Silktouch Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Silktouch)
			.addDescription("Apply to tools at the rune station.")),

	StrengthRune(new ItemContainer("strength_rune", "Strength Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Strength)
			.addDescription("Apply to any weapons at the rune station.")),

	CritDamageRune(new ItemContainer("crit_damage_rune", "Crit Damage Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.CritDamage)
			.addDescription("Apply to any weapons at the rune station.")),

	CritChanceRune(new ItemContainer("crit_chance_rune", "Crit Chance Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.CritChance)
			.addDescription("Apply to any weapons at the rune station.")),

	DefenseRune(new ItemContainer("defense_rune", "Defense Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Defense)
			.addDescription("Apply to any equipment piece at the rune station.")),

	ManaRune(new ItemContainer("mana_rune", "Mana Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Mana)
			.addDescription("Apply to any weapon or piece of equipment at the rune station.")),

	HealthRune(new ItemContainer("health_rune", "Health Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Health)
			.addDescription("Apply to any weapon or piece of equipment at the rune station.")),

	/*********************************************
	 				Armor & Equipment
	 *********************************************/

	//Eternite
	EterniteHelmet(new ItemContainer("eternite_helmet", "Eternite Helmet", Material.NETHERITE_HELMET, Rarity.Legendary, ItemType.Helmet)
			.setHealth(50).setDefense(10).setEarthElement(10).setHarvestingSpeed(100).setFortune(20)
			.setRuneSlots(5)),

	EterniteChestplate(new ItemContainer("eternite_chestplate", "Eternite Chestplate", Material.NETHERITE_CHESTPLATE, Rarity.Legendary, ItemType.Chestplate)
			.setHealth(80).setDefense(16).setEarthElement(16).setHarvestingSpeed(160).setFortune(32)
			.setRuneSlots(8)),

	EterniteLeggings(new ItemContainer("eternite_leggings", "Eternite Leggings", Material.NETHERITE_LEGGINGS, Rarity.Legendary, ItemType.Leggings)
			.setHealth(70).setDefense(14).setEarthElement(14).setHarvestingSpeed(140).setFortune(28)
			.setRuneSlots(7)),

	EterniteBoots(new ItemContainer("eternite_boots", "Eternite Boots", Material.NETHERITE_BOOTS, Rarity.Legendary, ItemType.Boots)
			.setHealth(40).setDefense(8).setEarthElement(8).setHarvestingSpeed(80).setFortune(16)
			.setRuneSlots(4)),

	EterniteCape(new ItemContainer("eternite_cape", "Eternite Cape", Material.NETHERITE_INGOT, Rarity.Legendary, ItemType.Cape)
			.setHealth(40).setDefense(8).setEarthElement(8).setHarvestingSpeed(80).setFortune(16)
			.setRuneSlots(4)),

	EterniteRing(new ItemContainer("eternite_ring", "Eternite Ring", Material.NETHERITE_INGOT, Rarity.Legendary, ItemType.Ring).setCustomModelData(CustomModelData.ETERNITE_RING)
			.setHealth(20).setDefense(4).setEarthElement(4).setHarvestingSpeed(40).setFortune(8)
			.setRuneSlots(2)),

	EterniteBracelet(new ItemContainer("eternite_barcelet", "Eternite Bracelet", Material.NETHERITE_INGOT, Rarity.Legendary, ItemType.Bracelet)
			.setHealth(40).setDefense(8).setEarthElement(8).setHarvestingSpeed(80).setFortune(16)
			.setRuneSlots(4)),

	EterniteNecklace(new ItemContainer("eternite_necklace", "Eternite Necklace", Material.NETHERITE_INGOT, Rarity.Legendary, ItemType.Necklace)
			.setHealth(60).setDefense(6).setEarthElement(6).setHarvestingSpeed(60).setFortune(10)
			.setRuneSlots(3)),

	EterniteHeadband(new ItemContainer("eternite_headband", "Eternite Headband", Material.NETHERITE_INGOT, Rarity.Legendary, ItemType.Headband)
			.setHealth(20).setDefense(4).setEarthElement(4).setHarvestingSpeed(40).setFortune(8)
			.setRuneSlots(2)),

	//Fisherman

	FishermanHelmet(new ItemContainer("fisherman_helmet", "Fisherman Helmet", Material.LEATHER_HELMET, Rarity.Rare, ItemType.Helmet)
			.setFishingSpeed(10)),

	FishermanChestplate(new ItemContainer("fisherman_chestplate", "Fisherman Chestplate", Material.LEATHER_CHESTPLATE, Rarity.Rare, ItemType.Chestplate)
			.setFishingSpeed(10)),

	FishermanLeggings(new ItemContainer("fisherman_leggings", "Fisherman Leggings", Material.LEATHER_LEGGINGS, Rarity.Rare, ItemType.Leggings)
			.setFishingSpeed(10)),

	FishermanBoots(new ItemContainer("fisherman_boots", "Fisherman Boots", Material.LEATHER_BOOTS, Rarity.Rare, ItemType.Boots)
			.setFishingSpeed(10)),


	/*********************************************
	 					Weapons
	 *********************************************/

	AdminScythe(new ItemContainer("admin_scythe", "Admin Scythe", Material.NETHERITE_HOE, Rarity.Mythic, ItemType.Sword)
			.setDamage(1000000)
			.addDescription("This is a hoe.")),

	MagicWand(new ItemContainer("magic_wand", "Magic Wand", Material.STICK, Rarity.Epic, ItemType.Wand)
			.setDamage(100).setMana(100)),

	ClassicBow(new ItemContainer("classic_bow", "Classic Bow", Material.BOW, Rarity.Unique, ItemType.Bow)
			.setDamage(100).setRuneSlots(5)
			.addDescription("It's just a bow.")),

	BloodthirstyDagger(new ItemContainer("blood_thirsty_dagger", "Blood Thirsty Dagger", Material.GOLDEN_SWORD, Rarity.Rare, ItemType.Sword)
			.setDamage(10).setRuneSlots(5)),

	/*********************************************
	 					Tools
	 *********************************************/

	EternitePickaxe(new ItemContainer("eternite_pickaxe", "Eternite Pickaxe", Material.NETHERITE_PICKAXE, Rarity.Legendary, ItemType.Pickaxe)
			.setHarvestingSpeed(500).setFortune(50)
			.setRuneSlots(10)),

	LumberjacksLegacy(new ItemContainer("lumberjacks_legacy", "Lumberjack's Legacy", Material.GOLDEN_AXE, Rarity.Unique, ItemType.Axe)
			.setDamage(10).setRuneSlots(5)
			.addDescription("A forgotten Lumberjack wielded this axe.")),

	DiamondPickaxe(new ItemContainer("diamond_pickaxe", "Diamond Pickaxe", Material.DIAMOND_PICKAXE, Rarity.Legendary, ItemType.Pickaxe)
			.setDamage(10).setHarvestingSpeed(100).setFortune(10).setNPCSellPrice(100).setRuneSlots(50)
			.addDescription("A simple diamond pickaxe")),

	FishingRod(new ItemContainer("fishing_rod", "Fishing Rod", Material.FISHING_ROD, Rarity.Rare, ItemType.FishingRod)
			.setFishingSpeed(10)),

	HealingWand(new ItemContainer("healing_wand", "Healing Wand", Material.STICK, Rarity.Epic, ItemType.Wand)),

	SpeedsterHelmet(new ItemContainer("speedster_helmet", "Speedster Helmet", Material.TURTLE_HELMET, Rarity.Mythic, ItemType.Helmet)
			.setSpeed(400))

	;

	private ItemContainer item;

	GameItem(ItemContainer item) {
		this.item = item;
	}

	public String getId() {
		return item.id;
	}

	public String getDisplayName() {
		return item.displayName;
	}

	public Material getMaterial() {
		return item.material;
	}

	public Rarity getRarity() {
		return item.rarity;
	}

	public String getDescription() {
		return item.description;
	}

	public boolean hasUUID() {
		return item.hasUUID;
	}

	public ItemType getItemType() {
		return item.itemType;
	}

	public int getNpcSellPrice() {
		return item.npcSellPrice;
	}

	public int getNpcBuyPrice() {
		return item.npcBuyPrice;
	}

	public boolean isDismantlable() {
		return item.dismantlable;
	}

	public LootTable getLootTable() {
		return item.lootTable;
	}

	public int getRuneSlots() {
		return item.runeSlots;
	}

	public int getHealth() {
		return getInt(PlayerStat.MaxHealth);
	}

	public int getMana() {
		return getInt(PlayerStat.MaxMana);
	}

	public int getDefense() {
		return getInt(PlayerStat.Defense);
	}

	public int getFireElement() {
		return getInt(PlayerStat.FireElement);
	}

	public int getWaterElement() {
		return getInt(PlayerStat.WaterElement);
	}

	public int getAirElement() {
		return getInt(PlayerStat.AirElement);
	}

	public int getEarthElement() {
		return getInt(PlayerStat.EarthElement);
	}

	public int getLightningElement() {
		return getInt(PlayerStat.LightningElement);
	}

	public int getIceElement() {
		return getInt(PlayerStat.IceElement);
	}

	public int getSpeed() {
		return getInt(PlayerStat.Speed);
	}

	public int getDamage() {
		return getInt(PlayerStat.Damage);
	}

	public int getStrength() {
		return getInt(PlayerStat.Strength);
	}

	public int getCritChance() {
		return getInt(PlayerStat.CritChance);
	}

	public int getCritDamage() {
		return getInt(PlayerStat.CritDamage);
	}

	public int getFortune() {
		return getInt(PlayerStat.Fortune);
	}

	public int getHarvestingSpeed() {
		return getInt(PlayerStat.HarvestingSpeed);
	}

	public int getFishingSpeed() {
		return getInt(PlayerStat.FishingSpeed);
	}

	public boolean hasStat(PlayerStat id) {
		return item.stats.containsKey(id);
	}

	public HashMap<PlayerStat, String> getStats() {
		return item.stats;
	}

	public String getStat(PlayerStat id) {
		return item.stats.get(id);
	}

	public boolean hasStats() {
		return getStats().size() > 0;
	}

	private int getInt(PlayerStat id) {
		return Integer.parseInt(getStat(id));
	}

	public boolean isRune() {
		return item.rune != null;
	}

	public boolean canApplyRunes() {
		return item.runeSlots > 0;
	}

	public boolean hasDescription() {
		return item.description != null && !item.description.isEmpty();
	}

	public boolean isArmor() {
		return item.itemType.getCatagory() == ItemType.Catagory.ARMOR;
	}

	public boolean isEquipment() {
		return item.itemType.getCatagory() == ItemType.Catagory.EQUIPMENT;
	}

	public boolean isWeapon() {
		return item.itemType.getCatagory() == ItemType.Catagory.WEAPON;
	}

	public boolean isTool() {
		return item.itemType.getCatagory() == ItemType.Catagory.TOOL;
	}

	public boolean isHandEquipable() {
		return isTool() || isWeapon();
	}

	public boolean hasCustomModelData() {
		return item.modelData != 999;
	}

	public Rune getRune() {
		return item.rune;
	}

	public int getCusomModelData() {
		return item.modelData;
	}

	public boolean hasItemAbility(){
		return item.itemAbility != null;
	}

	public ItemAbility getItemAbility() {
		return item.itemAbility;
	}

	public void setItemAbility(ItemAbility ability) {
		item.setItemAbility(ability);
	}

	public boolean hasEquipmentAbility(){ return item.equipmentAbility != null; }

	public void setEquipmentAbility(EquipmentAbility ability){
		item.setEquipmentAbility(ability);
	}

	public EquipmentAbility getEquipmentAbility(){
		return item.equipmentAbility;
	}

	public static GameItem getItem(String id) {
		for (GameItem item : GameItem.values()) {
			if (item.getId().equals(id)) return item;
		}
		return null;
	}

	public static GameItem getItem(ItemStack stack) {
		if (InventoryHelper.isAirOrNull(stack)) return null;
		NBTGameItem item = new NBTGameItem(stack);
		if (item.hasJuno() && item.hasID()) {
			for (GameItem gameItem : GameItem.values()) {
				if (gameItem.getId().equalsIgnoreCase(item.getID())) {
					return gameItem;
				}
			}
		}
		return null;
	}
}
