package io.github.nmahdi.JunoCore.item;

import io.github.nmahdi.JunoCore.item.builder.CustomModelData;
import io.github.nmahdi.JunoCore.item.builder.nbt.NBTGameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.item.stats.Rarity;
import io.github.nmahdi.JunoCore.item.stats.Rune;
import io.github.nmahdi.JunoCore.loot.ILootTable;
import io.github.nmahdi.JunoCore.loot.LootTable;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.utils.InventoryHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public enum GameItem {

	 Stone(new ItemContainer("stone", "Stone Block",Material.STONE, Rarity.Common, ItemType.Block)),
	 CopperOre(new ItemContainer("copper_ore", "Copper Ore", Material.COPPER_ORE, Rarity.Common, ItemType.Block)),
	 CoalOre(new ItemContainer("coal_ore", "Coal Ore", Material.COAL_ORE, Rarity.Common, ItemType.Block)),
	 IronOre(new ItemContainer("iron_ore", "Iron Ore", Material.IRON_ORE, Rarity.Common, ItemType.Block)),
	 GoldOre(new ItemContainer("gold_ore", "Gold Ore", Material.GOLD_ORE, Rarity.Common, ItemType.Block)),
	 EmeraldOre(new ItemContainer("emerald_ore", "Emerald Ore", Material.EMERALD_ORE, Rarity.Common, ItemType.Block)),
	 DiamondOre(new ItemContainer("diamond_ore", "Diamond Ore", Material.DIAMOND_ORE, Rarity.Common, ItemType.Block)),
	 LapisLazuliOre(new ItemContainer("lapis_lazuli_ore", "Lapis Lazuli Ore", Material.LAPIS_ORE, Rarity.Common, ItemType.Block)),
	 RedstoneOre(new ItemContainer("redstone_ore", "Redstone Ore", Material.REDSTONE_ORE, Rarity.Common, ItemType.Block)),
	 QuartzOre(new ItemContainer("quartz_ore", "Quartz Ore", Material.NETHER_QUARTZ_ORE, Rarity.Common, ItemType.Block)),

	 ClayBlock(new ItemContainer("clay_block", "Clay Block", Material.CLAY, Rarity.Common, ItemType.Block)),
	 Gravel(new ItemContainer("gravel", "Gravel", Material.GRAVEL, Rarity.Common, ItemType.Block)),
	 Obsidian(new ItemContainer("obsidian", "Obsidian", Material.OBSIDIAN, Rarity.Common, ItemType.Block)),

	//Items
	 Stick(new ItemContainer("stick", "Stick", Material.STICK, Rarity.Common, ItemType.Misc)),
	 Brick(new ItemContainer("brick", "Brick", Material.BRICK, Rarity.Common, ItemType.Misc)),

	//Mob Drops
	 Bone(new ItemContainer("bone", "Bone", Material.BONE, Rarity.Common, ItemType.Misc)),
	 EnderPearl(new ItemContainer("ender_pearl", "Ender Pearl", Material.ENDER_PEARL, Rarity.Common, ItemType.Misc)),
	 RottenFlesh(new ItemContainer("rotten_flesh", "Rotten Flesh", Material.ROTTEN_FLESH, Rarity.Common, ItemType.Misc)),
	 Gunpowder(new ItemContainer("gunpowder", "Gun Powder", Material.GUNPOWDER, Rarity.Common, ItemType.Misc)),
	 GhastTear(new ItemContainer("ghast_tear", "Ghast Tear", Material.GHAST_TEAR, Rarity.Common, ItemType.Misc)),
	 BlazeRod(new ItemContainer("blaze_rod", "Blaze Rod", Material.BLAZE_ROD, Rarity.Common, ItemType.Misc)),
	 SpiderEye(new ItemContainer("spider_eye", "Spider Eye", Material.SPIDER_EYE, Rarity.Common, ItemType.Misc)),
	 String(new ItemContainer("string", "String", Material.STRING, Rarity.Common, ItemType.Misc)),
	 Feather(new ItemContainer("feather", "Feather", Material.FEATHER, Rarity.Common, ItemType.Misc)),
	 Leather(new ItemContainer("leather", "Leather", Material.LEATHER, Rarity.Common, ItemType.Misc)),
	 RabbitHide(new ItemContainer("rabbit_hide", "Rabbit Hide", Material.RABBIT_HIDE, Rarity.Common, ItemType.Misc)),
	 MagmaCream(new ItemContainer("magma_cream", "Magma Cream", Material.MAGMA_CREAM, Rarity.Common, ItemType.Misc)),
	 SlimeBall(new ItemContainer("slime_ball", "Slime Ball", Material.SLIME_BALL, Rarity.Common, ItemType.Misc)),

	//Materials
	 Cobblestone(new ItemContainer("cobblestone", "Cobblestone", Material.COBBLESTONE, Rarity.Common, ItemType.Block)),
	 Clay(new ItemContainer("clay", "Clay", Material.CLAY, Rarity.Common, ItemType.Misc)),
	 Flint(new ItemContainer("flint", "Flint", Material.FLINT, Rarity.Common, ItemType.Misc)),
	 Coal(new ItemContainer("coal", "Coal", Material.COAL, Rarity.Common, ItemType.Misc)),
	 Charcoal(new ItemContainer("charcoal", "Charcoal", Material.CHARCOAL, Rarity.Common, ItemType.Misc)),
	 LapisLazuli(new ItemContainer("lapis_lazuli", "Lapis Lazuli", Material.LAPIS_LAZULI, Rarity.Common, ItemType.Misc)),
	 Redstone(new ItemContainer("redstone", "Redstone", Material.REDSTONE, Rarity.Common, ItemType.Misc)),
	 Quartz(new ItemContainer("quartz", "Quartz", Material.QUARTZ, Rarity.Common, ItemType.Misc)),
	 CopperBar(new ItemContainer("copper_bar", "Copper Bar", Material.COPPER_INGOT, Rarity.Common, ItemType.Misc)),
	 IronBar(new ItemContainer("iron_bar", "Iron Bar", Material.IRON_INGOT, Rarity.Common, ItemType.Misc)),
	 GoldBar(new ItemContainer("gold_bar", "Gold Bar", Material.GOLD_INGOT, Rarity.Common, ItemType.Misc)),
	 Emerald(new ItemContainer("emerald", "Emerald", Material.EMERALD, Rarity.Common, ItemType.Misc)),
	 Diamond(new ItemContainer("diamond", "Diamond", Material.DIAMOND, Rarity.Common, ItemType.Misc)),
	 NetheriteBar(new ItemContainer("netherite_bar", "Netherite Bar", Material.NETHERITE_INGOT, Rarity.Common, ItemType.Misc)),

	 OakLog(new ItemContainer("oak_log", "Oak Log", Material.OAK_LOG, Rarity.Common, ItemType.Misc)),
	 SpruceLog(new ItemContainer("spruce_log", "Spruce Log", Material.SPRUCE_LOG, Rarity.Common, ItemType.Misc)),
	 BirchLog(new ItemContainer("birch_log", "Birch Log", Material.BIRCH_LOG, Rarity.Common, ItemType.Misc)),
	 JungleLog(new ItemContainer("jungle_log", "Jungle Log", Material.SPRUCE_LOG, Rarity.Common, ItemType.Misc)),
	 AcaciaLog(new ItemContainer("acacia_log", "Acacia Log", Material.ACACIA_LOG, Rarity.Common, ItemType.Misc)),
	 DarkOakLog(new ItemContainer("dark_oak_log", "Dark Oak Log", Material.DARK_OAK_LOG, Rarity.Common, ItemType.Misc)),
	 MangroveLog(new ItemContainer("mangrove_log", "Mangrove Log", Material.MANGROVE_LOG, Rarity.Common, ItemType.Misc)),

	 Poppy(new ItemContainer("poppy", "Poppy", Material.POPPY, Rarity.Common, ItemType.Misc)),

	//Compacted Items
	 CompactedCobblestone(new ItemContainer("compacted_cobblestone", "Compacted Cobblestone", Material.COBBLESTONE, Rarity.Uncommon, ItemType.Misc)),
	 CompactedCoal(new ItemContainer("compacted_coal", "Compacted Coal", Material.COAL, Rarity.Uncommon, ItemType.Misc)),
	 CompactedIron(new ItemContainer("compacted_iron", "Compacted Iron", Material.IRON_INGOT, Rarity.Uncommon, ItemType.Misc)),
	 CompactedGold(new ItemContainer("compacted_gold", "Compacted Gold", Material.GOLD_INGOT, Rarity.Uncommon, ItemType.Misc)),
	 CompactedEmerald(new ItemContainer("compacted_emerald", "Compacted Emerald", Material.EMERALD, Rarity.Uncommon, ItemType.Misc)),
	 CompactedDiamond(new ItemContainer("compacted_diamond", "Compacted Diamond", Material.DIAMOND, Rarity.Uncommon, ItemType.Misc)),
	 CompactedRottenFlesh(new ItemContainer("compacted_rotten_flesh", "Compacted Rotten Flesh", Material.ROTTEN_FLESH, Rarity.Uncommon, ItemType.Misc)),

	//Custom Items
	 MagicStone(new ItemContainer("magic_stone", "Magic Stone", Material.AMETHYST_SHARD, Rarity.Rare, ItemType.Misc)
			.addDescription(Component.text("Used to upgrade equipment!").color(TextColors.GRAY))),
	 BasicGeode(new ItemContainer("basic_geode", "Basic Geode", Material.STONE, Rarity.Common, ItemType.Misc)
			.setCustomModelData(CustomModelData.GEODE).setDismantlable(LootTable.BasicGeode)
			.addDescription(Component.text("The most common type of geode.").color(TextColors.GRAY),
					Component.text("Bring this to a blacksmith to break open").color(TextColors.GRAY),
					Component.text("for a chance to drop quality loot.").color(TextColors.GRAY))),

	//Runes
	 EfficiencyRune(new ItemContainer("efficiency_rune", "Efficiency Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Efficiency)
			.addDescription(Component.text("Apply to any harvesting tools at the rune station.").color(TextColors.GRAY))),

	 FortuneRune(new ItemContainer("fortune_rune", "Fortune Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Fortune)
			.addDescription(Component.text("Apply to any harvesting tools at the rune station.").color(TextColors.GRAY))),

	 SmeltingRune(new ItemContainer("smelting_rune", "Smelting Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Smelting)
			.addDescription(Component.text("Apply to mining & woodcutting tools at the rune station.").color(TextColors.GRAY))),

	 SilktouchRune(new ItemContainer("silktouch_rune", "Silktouch Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Silktouch)
			.addDescription(Component.text("Apply to tools at the rune station.").color(TextColors.GRAY))),

	 DamageRune(new ItemContainer("damage_rune", "Damage Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Damage)
			.addDescription(Component.text("Apply to any weapons at the rune station.").color(TextColors.GRAY))),

	 StrengthRune(new ItemContainer("strength_rune", "Strength Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Strength)
			.addDescription(Component.text("Apply to any weapons at the rune station.").color(TextColors.GRAY))),

	 CritDamageRune(new ItemContainer("crit_damage_rune", "Crit Damage Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.CritDamage)
			.addDescription(Component.text("Apply to any weapons at the rune station.").color(TextColors.GRAY))),

	 CritChanceRune(new ItemContainer("crit_chance_rune", "Crit Chance Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.CritChance)
			.addDescription(Component.text("Apply to any weapons at the rune station.").color(TextColors.GRAY))),

	 DefenseRune(new ItemContainer("defense_rune", "Defense Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Defense)
			.addDescription(Component.text("Apply to any equipment piece at the rune station.").color(TextColors.GRAY))),

	 ManaRune(new ItemContainer("mana_rune", "Mana Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Mana)
			.addDescription(Component.text("Apply to any weapon or piece of equipment ").color(TextColors.GRAY), Component.text("at the rune station.").color(TextColors.GRAY))),

	 HealthRune(new ItemContainer("health_rune", "Health Rune", Material.PAPER, Rarity.Uncommon, ItemType.Rune)
			.setRune(Rune.Health)
			.addDescription(Component.text("Apply to any weapon or piece of equipment ").color(TextColors.GRAY), Component.text("at the rune station.").color(TextColors.GRAY))),


	//Equipment
	 RookieHelmet(new ItemContainer("rookie_helmet", "Rookie Helmet", Material.IRON_BLOCK, Rarity.Rare, ItemType.Helmet)
			.setRuneSlots(5).setDismantlable(LootTable.RareEquipment)
			.setHealth(10).setDefense(20)
			.setFireElement(20).setWaterElement(20).setLightningElement(20).setIceElement(20)
			.addDescription(Component.text("A helmet that the Ultimate Rookie God wore!").color(TextColors.GRAY))),

	 RookieChestplate(new ItemContainer("rookie_chestplate", "Rookie Chestplate", Material.IRON_CHESTPLATE, Rarity.Rare, ItemType.Chestplate)
			.setRuneSlots(5).setDismantlable(LootTable.RareEquipment)
			.setHealth(20).setDefense(30)
			.setFireElement(30).setWaterElement(30).setLightningElement(30).setIceElement(30)
			.addDescription(Component.text("A chestplate that the Ultimate Rookie God wore!").color(TextColors.GRAY))),

	 RookieLeggings(new ItemContainer("rookie_leggings", "Rookie Leggings", Material.IRON_LEGGINGS, Rarity.Rare, ItemType.Leggings)
			.setRuneSlots(5).setDismantlable(LootTable.RareEquipment)
			.setHealth(15).setDefense(25)
			.setFireElement(25).setWaterElement(25).setLightningElement(25).setIceElement(25)
			.addDescription(Component.text("Leggings that the Ultimate Rookie God wore!").color(TextColors.GRAY))),

	 RookieBoots(new ItemContainer("rookie_boots", "Rookie Boots", Material.IRON_BOOTS, Rarity.Rare, ItemType.Boots)
			.setRuneSlots(5).setDismantlable(LootTable.RareEquipment)
			.setHealth(5).setDefense(15)
			.setFireElement(15).setWaterElement(15).setLightningElement(15).setIceElement(15)
			.addDescription(Component.text("Boots that the Ultimate Rookie God wore!").color(TextColors.GRAY))),

	 RookieRing(new ItemContainer("rookie_ring", "Rookie Ring", Material.IRON_NUGGET, Rarity.Rare, ItemType.Ring)
			.setRuneSlots(5).setHealth(5).setDefense(15).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A ring that the Ultimate Rookie God wore!").color(TextColors.GRAY))),

	 RookieBracelet(new ItemContainer("rookie_bracelet", "Rookie Bracelet", Material.STRING, Rarity.Rare, ItemType.Bracelet)
			.setRuneSlots(5).setHealth(5).setDefense(15).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A bracelet that the Ultimate Rookie God wore!").color(TextColors.GRAY))),

	 RookieNecklace(new ItemContainer("rookie_necklace", "Rookie Necklace", Material.REDSTONE, Rarity.Rare, ItemType.Necklace)
			 .setRuneSlots(5).setHealth(5).setDefense(15).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A necklace that the Ultimate Rookie God wore!").color(TextColors.GRAY))),

	 RookieHeadband(new ItemContainer("rookie_headband", "Rookie Headband", Material.CARROT, Rarity.Rare, ItemType.Headband)
			.setRuneSlots(5).setHealth(5).setDefense(15).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A headband that the Ultimate Rookie God wore!").color(TextColors.GRAY))),

	 RookieCape(new ItemContainer("rookie_cape", "Rookie Cape", Material.DIAMOND, Rarity.Rare, ItemType.Cape)
			.setRuneSlots(5).setHealth(5).setDefense(15).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A cape that the Ultimate Rookie God wore!").color(TextColors.GRAY))),

	//Weapons
	 RookieSword(new ItemContainer("rookie_sword", "Rookie Sword", Material.IRON_SWORD, Rarity.Rare, ItemType.Sword)
			.setDamage(30).setStrength(10).setCritChance(10).setCritDamage(100).setDismantlable(LootTable.RareEquipment)
			.addDescription(Component.text("A sword that the Ultimate Rookie God wielded!").color(TextColors.GRAY))),

	 AdminScythe(new ItemContainer("admin_scythe", "Admin Scythe", Material.NETHERITE_HOE, Rarity.Mythic, ItemType.Sword)
			.setDamage(1000000)
			.addDescription(Component.text("This is a hoe.").color(TextColors.GRAY))),

	 MagicWand(new ItemContainer("magic_wand", "Magic Wand", Material.STICK, Rarity.Epic, ItemType.Wand)
			.setDamage(100).setMana(100)),

	 ClassicBow(new ItemContainer("classic_bow", "Classic Bow", Material.BOW, Rarity.Unique, ItemType.Bow)
			.setDamage(100).setRuneSlots(5)
			.addDescription(Component.text("It's just a bow.").color(TextColors.GRAY))),

	 TreeFeller(new ItemContainer("tree_feller", "Tree Feller", Material.GOLDEN_AXE, Rarity.Unique, ItemType.Axe)
			.setDamage(10).setRuneSlots(5)
			.addDescription(Component.text("An Epic axe forged in the depths of tartarus.").color(TextColors.GRAY))),

	 DiamondPickaxe(new ItemContainer("diamond_pickaxe", "Diamond Pickaxe", Material.DIAMOND_PICKAXE, Rarity.Legendary, ItemType.Pickaxe)
			.setDamage(10).setHarvestingSpeed(100).setFortune(10).setNPCSellPrice(100).setRuneSlots(50).setHasUUID()
			.addDescription(Component.text("A simple diamond pickaxe").color(TextColors.GRAY)))
	;

	 private ItemContainer item;

	 GameItem(ItemContainer item){
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

	public ArrayList<Component> getDescription() {
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
	public int getHealth(){
		return getInt(PlayerStat.MaxHealth);
	}
	public int getMana(){
		return getInt(PlayerStat.MaxMana);
	}
	public int getDefense(){
		return getInt(PlayerStat.Defense);
	}
	public int getFireElement(){
		return getInt(PlayerStat.FireElement);
	}
	public int getWaterElement(){
		return getInt(PlayerStat.WaterElement);
	}
	public int getAirElement(){
		return getInt(PlayerStat.AirElement);
	}
	public int getEarthElement(){
		return getInt(PlayerStat.EarthElement);
	}
	public int getLightningElement(){
		return getInt(PlayerStat.LightningElement);
	}
	public int getIceElement(){
		return getInt(PlayerStat.IceElement);
	}
	public int getSpeed(){
		return getInt(PlayerStat.Speed);
	}
	public int getDamage(){
		return getInt(PlayerStat.Damage);
	}
	public int getStrength(){
		return getInt(PlayerStat.Strength);
	}
	public int getCritChance(){
		return getInt(PlayerStat.CritChance);
	}
	public int getCritDamage(){
		return getInt(PlayerStat.CritDamage);
	}
	public int getFortune(){
		return getInt(PlayerStat.Fortune);
	}

	public int getHarvestingSpeed(){
		return getInt(PlayerStat.HarvestingSpeed);
	}
	public int getFishingSpeed(){
		return getInt(PlayerStat.FishingSpeed);
	}

	public boolean hasStat(PlayerStat id){
		return item.stats.containsKey(id);
	}

	public HashMap<PlayerStat, String> getStats() {
		return item.stats;
	}

	public String getStat(PlayerStat id){
		return item.stats.get(id);
	}

	public boolean hasStats(){
		return getStats().size() > 0;
	}
	private int getInt(PlayerStat id){
		return Integer.parseInt(getStat(id));
	}
	public boolean isRune(){
		return item.rune != null;
	}

	public boolean canApplyRunes(){
		return item.runeSlots > 0;
	}

	public boolean hasDescription(){
		return !item.description.isEmpty();
	}

	public boolean isArmor(){
		return item.itemType.getCatagory() == ItemType.Catagory.ARMOR;
	}

	public boolean isEquipment(){
		return item.itemType.getCatagory() == ItemType.Catagory.EQUIPMENT;
	}

	public boolean isWeapon(){
		return item.itemType.getCatagory() == ItemType.Catagory.WEAPON;
	}

	public boolean isTool(){
		return item.itemType.getCatagory() == ItemType.Catagory.TOOL;
	}

	public boolean isHandEquipable(){
		 return isTool() || isWeapon();
	}

	public boolean hasCustomModelData(){
		return item.modelData != 999;
	}
	public Rune getRune() {
		return item.rune;
	}
	public int getCusomModelData() {
		return item.modelData;
	}

	public static GameItem getItem(String id){
		for (GameItem item : GameItem.values()) {
			if (item.getId().equals(id)) return item;
		}
		return null;
	}

	public static GameItem getItem(ItemStack stack){
		if(InventoryHelper.isAirOrNull(stack)) return null;
		NBTGameItem item = new NBTGameItem(stack);
		if(item.hasJuno() && item.hasID()) {
			for (GameItem gameItem : GameItem.values()) {
				if (gameItem.getId().equalsIgnoreCase(item.getID())) {
					return gameItem;
				}
			}
		}
		return null;
	}
}
