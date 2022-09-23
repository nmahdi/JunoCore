package io.github.nmahdi.JunoCore.item;

import io.github.nmahdi.JunoCore.item.stats.*;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

public enum JItem {
    //Blocks
    Stone("stone", "Stone", Material.STONE),
    Granite("granite", "Granite", Material.GRANITE),
    PolishedGranite("polished_granite", "Polished Granite", Material.POLISHED_GRANITE),
    Diorite("diorite", "Diorite", Material.DIORITE),
    PolishedDiorite("polished_diorite", "Polished Diorite", Material.POLISHED_DIORITE),
    Andesite("andesite", "Andesite", Material.ANDESITE),
    PolishedAndesite("polished_andesite", "Polsihed Andeiste", Material.POLISHED_ANDESITE),

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
    GoldVar("gold_bar", "Gold Bar", Material.GOLD_INGOT),
    Diamond("diamond", "Diamond", Material.DIAMOND),
    NetheriteBar("netherite_bar", "Netherite Bar", Material.NETHERITE_INGOT),
    Emerald("emerald", "Emerald", Material.EMERALD),

    //Compacted Items
    CompactedCobble("compacted_cobble", "Compacted Cobblestone", Material.COBBLESTONE, Rarity.Uncommon),
    CompactedCoal("compacted_coal", "Compacted Coal", Material.COAL, Rarity.Uncommon),
    CompactedIron("compacted_iron", "Compacted Iron", Material.IRON_INGOT, Rarity.Uncommon),
    CompactedGold("compacted_gold", "Compacted Gold", Material.GOLD_INGOT, Rarity.Uncommon),
    CompactedDiamond("compacted_diamond", "Compacted Diamond", Material.DIAMOND, Rarity.Uncommon),
    CompactedEmerald("compacted_emerald", "Compacted Emerald", Material.EMERALD, Rarity.Uncommon),
    CompactedRottenFlesh("compacted_rotten_flesh", "Compacted Rotten Flesh", Material.ROTTEN_FLESH, Rarity.Uncommon),

    //Mining
    Geode("basic_geode", "Basic Geode", Material.COBBLESTONE, Rarity.Common,
            "&8The most common type of geode.\nBring this to a blacksmith to break open\nfor a chance to drop quality loot."),

    //Food


    //Weapons
    RookieSword("rookie_sword", "Rookie Sword", Material.IRON_SWORD, Rarity.Rare, "&7A sword that the Ultimate Rookie God wielded!",
            new Stat(ItemStatID.WeaponType, WeaponType.Sword.getId()),
            new Stat(ItemStatID.Damage, "30"),
            new Stat(ItemStatID.Strength, "10"),
            new Stat(ItemStatID.CritDamage, "100"),
            new Stat(ItemStatID.CritChance, "10")),

    //Armor
    RookieHelmet("rookie_helmet", "Rookie Helmet", Material.IRON_BLOCK, Rarity.Rare, "&7A helmet that the Ultimate Rookie God wore!",
            new Stat(ItemStatID.EquipmentSlot, EquipmentSlotID.Helmet.getId()),
            new Stat(ItemStatID.Health, "10"),
            new Stat(ItemStatID.Defense, "20"),
            new Stat(ItemStatID.FireDefense, "20"),
            new Stat(ItemStatID.WaterDefense, "20"),
            new Stat(ItemStatID.LightningDefense, "20"),
            new Stat(ItemStatID.IceDefense, "20")),

    //Tools
    TreeFeller("tree_feller", "Tree Feller", Material.GOLDEN_AXE, Rarity.Unique, "&7An Epic axe forged in the depths of tartarus.",
            new Stat(ItemStatID.Power, "10")),

    NegativeStatTest("nstat_test", "Negative Stat Tester", Material.STICK, Rarity.Junk,
            new Stat(ItemStatID.Health, "-9999"),
            new Stat(ItemStatID.Defense, "-9999"),
            new Stat(ItemStatID.FireDefense, "-9999"),
            new Stat(ItemStatID.WaterDefense, "-9999"),
            new Stat(ItemStatID.LightningDefense, "-9999"),
            new Stat(ItemStatID.IceDefense, "-9999"),
            new Stat(ItemStatID.Speed, "-9999"),
            new Stat(ItemStatID.Damage, "-9999"),
            new Stat(ItemStatID.Strength, "-9999"),
            new Stat(ItemStatID.CritChance, "-9999"),
            new Stat(ItemStatID.CritDamage, "-9999"),
            new Stat(ItemStatID.Power, "-9999")),

    StatTest("stat_test", "Stat Tester", Material.STICK, Rarity.Mythic,
            new Stat(ItemStatID.Health, "9999"),
            new Stat(ItemStatID.Defense, "9999"),
            new Stat(ItemStatID.FireDefense, "9999"),
            new Stat(ItemStatID.WaterDefense, "9999"),
            new Stat(ItemStatID.LightningDefense, "9999"),
            new Stat(ItemStatID.IceDefense, "9999"),
            new Stat(ItemStatID.Speed, "9999"),
            new Stat(ItemStatID.Damage, "9999"),
            new Stat(ItemStatID.Strength, "9999"),
            new Stat(ItemStatID.CritChance, "9999"),
            new Stat(ItemStatID.CritDamage, "9999"),
            new Stat(ItemStatID.Power, "9999")),


    ;



    private String id;
    private String displayName;
    private Material materialType;
    private Rarity rarity;
    private ArrayList<String> lore = new ArrayList<>();
    private ArrayList<Stat> stats = new ArrayList<>();

    JItem(String id, String displayName, Material materialType, Rarity rarity, String lore, Stat... stats){
        this.id = id;
        this.displayName = displayName;
        this.materialType = materialType;
        this.rarity = rarity;
        if(lore != null) this.lore.add(lore);
        if(stats != null) this.stats.addAll(Arrays.asList(stats));
    }

    JItem(String id, String displayName, Material materialType, Rarity rarity, Stat... stats){
        this.id = id;
        this.displayName = displayName;
        this.materialType = materialType;
        this.rarity = rarity;
        if(stats != null) this.stats.addAll(Arrays.asList(stats));
    }

    JItem(String id, String displayName, Material materialType){
        this.id = id;
        this.displayName = displayName;
        this.materialType = materialType;
        this.rarity = Rarity.Common;
    }


    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterialType() {
        return materialType;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public ArrayList<Stat> getStats() {
        return stats;
    }
}
