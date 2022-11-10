package io.github.nmahdi.JunoCore.item;

import io.github.nmahdi.JunoCore.item.stats.*;
import io.github.nmahdi.JunoCore.loot.LootTable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public enum JItem {
    //Blocks
    Stone("stone", "Stone", Material.STONE),
    Granite("granite", "Granite", Material.GRANITE),
    PolishedGranite("polished_granite", "Polished Granite", Material.POLISHED_GRANITE),
    Diorite("diorite", "Diorite", Material.DIORITE),
    PolishedDiorite("polished_diorite", "Polished Diorite", Material.POLISHED_DIORITE),
    Andesite("andesite", "Andesite", Material.ANDESITE),
    PolishedAndesite("polished_andesite", "Polished Andeiste", Material.POLISHED_ANDESITE),

    //Mob Drops
    Bone("bone", "Bone", Material.BONE),
    EnderPearl("ender_pearl", "Ender Pearl", Material.ENDER_PEARL),
    RottenFlesh("rotten_flesh", "Rotten Flesh", Material.ROTTEN_FLESH),
    Gunpowder("gunpowder", "Gunpowder", Material.GUNPOWDER),
    GhastTear("ghast_tear", "Ghast Tear", Material.GHAST_TEAR),
    BlazeRod("blaze_rod", "Blaze Rod", Material.BLAZE_ROD),
    SpiderEye("spider_eye", "Spider Eye", Material.SPIDER_EYE),
    String("string", "String", Material.STRING),
    Feather("feather", "Feather", Material.FEATHER),
    Leather("leather", "Leather", Material.LEATHER),
    RabbitLeather("rabbit_leather", "Rabbit Leather", Material.RABBIT_HIDE),

    //Materials
    Clay("clay", "Clay", Material.CLAY),
    Flint("flint", "Flint", Material.FLINT),
    Coal("coal", "Coal", Material.COAL),
    Charcoal("charcoal", "Charcoal", Material.CHARCOAL),
    LapisLazuli("lapis_lazuli", "Lapis Lazuli", Material.LAPIS_LAZULI),
    Redstone("redstone", "Redstone", Material.REDSTONE),
    Quartz("quartz", "Quartz", Material.QUARTZ),
    IronBar("iron_bar", "Iron Bar", Material.IRON_INGOT),
    GoldBar("gold_bar", "Gold Bar", Material.GOLD_INGOT),
    Diamond("diamond", "Diamond", Material.DIAMOND),
    NetheriteBar("netherite_bar", "Netherite Bar", Material.NETHERITE_INGOT),
    Emerald("emerald", "Emerald", Material.EMERALD),

    //Compacted Items
    CompactedCobble("compacted_cobble", Rarity.Uncommon, "Compacted Cobblestone", Material.COBBLESTONE),
    CompactedCoal("compacted_coal",  Rarity.Uncommon,"Compacted Coal", Material.COAL),
    CompactedIron("compacted_iron", Rarity.Uncommon, "Compacted Iron", Material.IRON_INGOT),
    CompactedGold("compacted_gold", Rarity.Uncommon,"Compacted Gold", Material.GOLD_INGOT),
    CompactedDiamond("compacted_diamond", Rarity.Uncommon,"Compacted Diamond", Material.DIAMOND),
    CompactedEmerald("compacted_emerald", Rarity.Uncommon,"Compacted Emerald", Material.EMERALD),
    CompactedRottenFlesh("compacted_rotten_flesh", Rarity.Uncommon,"Compacted Rotten Flesh", Material.ROTTEN_FLESH),

    //Custom Items
    MagicStone("magic_stone", Rarity.Rare, "Magic Stone", Material.AMETHYST_SHARD,
            "&7Used to upgrade equipment!"),

    //Mining
    Geode("basic_geode", Rarity.Common , "Basic Geode", SkullURL.GEODE, new StatContainer().setLootTable(LootTable.Geode)
            ,"&8The most common type of geode.\nBring this to a blacksmith to break open\nfor a chance to drop quality loot."),

    //Food
    HealthPotion("health_potion", Rarity.Uncommon, "Health Potion", Material.POTION,
            new StatContainer().setConsumable(ConsumableID.Health, 50)
            ,"&8Right click to consume."),
    PumpkinPie("pumpkin_pie", Rarity.Uncommon, "Pumpkin Pie", Material.PUMPKIN_PIE,
            new StatContainer().setConsumable(ConsumableID.Mana, 50)
            ,"&8Right click to consume."),

    //Weapons
    RookieSword("rookie_sword", Rarity.Rare, "Rookie Sword", Material.IRON_SWORD,
            new StatContainer().setWeaponType(WeaponType.Sword).setDamage(30).setStrength(10).setCritChance(10).setCritDamage(100).setLootTable(LootTable.RareEquipment)
            ,"&7A sword that the Ultimate Rookie God wielded!"),

    SlashScythe("slash_scythe", Rarity.Mythic, "Slash's Scythe", Material.NETHERITE_HOE,
            new StatContainer().setWeaponType(WeaponType.Sword).setDamage(1000000)
            ,"&7This is a hoe."),

    //Armor
    RookieHelmet("rookie_helmet", Rarity.Rare, "Rookie Helmet", Material.IRON_BLOCK,
            new StatContainer().setEquipmentSlot(EquipmentSlotID.Helmet).setHealth(10).setDefense(20)
                    .setFireDefense(20).setWaterDefense(20).setLightningDefense(20).setIceDefense(20).setLootTable(LootTable.RareEquipment)
            ,"&7A helmet that the Ultimate Rookie God wore!"),

    RookieChestplate("rookie_chestplate", Rarity.Rare, "Rookie Chestplate", Material.IRON_CHESTPLATE,
            new StatContainer().setEquipmentSlot(EquipmentSlotID.Chestplate).setHealth(20).setDefense(30)
                    .setFireDefense(30).setWaterDefense(30).setLightningDefense(30).setIceDefense(30).setLootTable(LootTable.RareEquipment)
            ,"&7A chestplate that the Ultimate Rookie God wore!"),

    RookieLeggings("rookie_leggings", Rarity.Rare, "Rookie Leggings", Material.IRON_LEGGINGS,
            new StatContainer().setEquipmentSlot(EquipmentSlotID.Leggings).setHealth(15).setDefense(25)
                    .setFireDefense(25).setWaterDefense(25).setLightningDefense(25).setIceDefense(25).setLootTable(LootTable.RareEquipment)
            ,"&7Leggings that the Ultimate Rookie God wore!"),

    RookieBoots("rookie_boots", Rarity.Rare, "Rookie Boots", Material.IRON_BOOTS,
            new StatContainer().setEquipmentSlot(EquipmentSlotID.Boots).setHealth(5).setDefense(15)
                    .setFireDefense(15).setWaterDefense(15).setLightningDefense(15).setIceDefense(15).setLootTable(LootTable.RareEquipment)
            ,"&7Boots that the Ultimate Rookie God wore!"),

    HelmetTester("test_helmet", Rarity.Mythic, "Helmet Tester", Material.IRON_HELMET,
            new StatContainer().setEquipmentSlot(EquipmentSlotID.Helmet).setHealth(1000).setDefense(1000).setLootTable(LootTable.RareEquipment)
            ,"&7AAAAAAAAAAAAAAAAAA"),

    //Tools
    TreeFeller("tree_feller", Rarity.Unique, "Tree Feller", Material.GOLDEN_AXE,
            new StatContainer().setBreakingPower(10)
            ,"&7An Epic axe forged in the depths of tartarus."),

    NegativeStatTest("nstat_test", Rarity.Junk, "Negative Stat Tester", Material.STICK,
            new StatContainer().setWeaponType(WeaponType.Sword).setHealth(-9999).setDefense(-9999).setFireDefense(-9999).setWaterDefense(-9999)
                    .setLightningDefense(-9999).setIceDefense(-9999).setSpeed(-9999).setDamage(-9999).setStrength(-9999).setCritChance(-9999)
                    .setCritDamage(-9999).setBreakingPower(-9999), null),

    StatTest("nstat_test", Rarity.Mythic, "Stat Tester", Material.STICK,
            new StatContainer().setWeaponType(WeaponType.Sword).setHealth(9999).setDefense(9999).setFireDefense(9999).setWaterDefense(9999)
                    .setLightningDefense(9999).setIceDefense(9999).setSpeed(9999).setDamage(9999).setStrength(9999).setCritChance(9999)
                    .setCritDamage(9999).setBreakingPower(9999), null),

    ;

    private String id;
    private Rarity rarity;
    private String displayName;
    private Material materialType;
    private ArrayList<String> lore = new ArrayList<>();
    private StatContainer statContainer;

    private String texture;

    //For Common Items
    JItem(String id, String displayName, Material materialType){
        this.id = id;
        this.rarity = Rarity.Common;
        this.displayName = ChatColor.translateAlternateColorCodes('&', "&" + rarity.getColor() + displayName);
        this.materialType = materialType;
    }

    //For items without lore & stats
    JItem(String id, Rarity rarity, String displayName, Material materialType){
        this.id = id;
        this.rarity = rarity;
        this.displayName = ChatColor.translateAlternateColorCodes('&', "&" + rarity.getColor() + displayName);
        this.materialType = materialType;
    }

    //For items with lore & no stats
    JItem(String id, Rarity rarity, String displayName, Material materialType, String lore){
        this.id = id;
        this.rarity = rarity;
        this.displayName = ChatColor.translateAlternateColorCodes('&', "&" + rarity.getColor() + displayName);
        this.materialType = materialType;
        if(lore != null && !lore.isEmpty()) this.lore.add(ChatColor.translateAlternateColorCodes('&', lore));
    }

    //For items with lore & stats
    JItem(String id, Rarity rarity, String displayName, Material materialType, StatContainer statContainer, String lore){
        this.id = id;
        this.rarity = rarity;
        this.displayName = ChatColor.translateAlternateColorCodes('&', "&" + rarity.getColor() + displayName);
        this.materialType = materialType;
        this.statContainer = statContainer;
        if(lore != null && !lore.isEmpty()) this.lore.add(ChatColor.translateAlternateColorCodes('&', lore));
    }

    //For Skulls
    JItem(String id, Rarity rarity, String displayName, String texture, StatContainer statContainer, String lore){
        this.id = id;
        this.rarity = rarity;
        this.displayName = ChatColor.translateAlternateColorCodes('&', "&" + rarity.getColor() + displayName);
        this.texture = texture;
        this.materialType = Material.PLAYER_HEAD;
        this.statContainer = statContainer;
        if(lore != null && !lore.isEmpty()) this.lore.add(ChatColor.translateAlternateColorCodes('&', lore));
    }


    public String getId() {
        return id;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterialType() {
        return materialType;
    }

    public ArrayList<java.lang.String> getLore() {
        return lore;
    }

    public boolean hasStats(){
        return statContainer != null;
    }
    public StatContainer getStatContainer() {
        return statContainer;
    }

    public boolean isSkull(){
        return texture != null;
    }

    public java.lang.String getTexture() {
        return texture;
    }

    public static String getItemID(ItemStack item){
        return new NBTJItem(item).getID();
    }

    public static String getItemID(NBTJItem item){
        return item.getID();
    }

    public static JItem getItemByID(String id){
        for(int i = 0; i < JItem.values().length; i++){
            if(JItem.values()[i].getId().equalsIgnoreCase(id)){
                return JItem.values()[i];
            }
        }
        return null;
    }

    public static class SkullURL{
        public static final String GEODE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTg1ZjlkNDMzMjNlNTAzYjM3MGRlM2JmZGE3OGU0MDg2YmY0NjYyZmYyMzZiNTc5MmY1YmYzNzIzYzQzMzBiZiJ9fX0=";
    }

}
