package io.github.nmahdi.JunoCore.generation;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import org.bukkit.Material;

public enum ResourceType {
    //Mining
    Cobblestone("cobblestone", Material.COBBLESTONE, GameItem.Cobblestone, GameItem.Stone, GameItem.Cobblestone,
            ItemType.Pickaxe, 450, Material.BEDROCK),

    Stone("stone", Material.STONE, GameItem.Cobblestone, GameItem.Stone, GameItem.Stone,
            ItemType.Pickaxe, 500, Material.COBBLESTONE),

    Clay("clay", Material.CLAY, GameItem.Clay, GameItem.Brick, GameItem.ClayBlock,
            ItemType.Shovel,200, Material.AIR),

    Flint("flint", Material.GRAVEL, GameItem.Flint, null, GameItem.Gravel,
            ItemType.Shovel,200, Material.AIR),

    Coal ("coal", Material.COAL_ORE, GameItem.Coal, null, GameItem.CoalOre,
            ItemType.Pickaxe,500, Material.STONE),

    LapisLazuli("lapis_lazuli", Material.LAPIS_LAZULI, GameItem.LapisLazuli, null, GameItem.LapisLazuliOre,
            ItemType.Pickaxe,650, Material.STONE),

    Redstone("redstone", Material.REDSTONE, GameItem.Redstone, null, GameItem.RedstoneOre,
            ItemType.Pickaxe,650, Material.STONE),

    Quartz("quartz", Material.QUARTZ, GameItem.Quartz, null, GameItem.QuartzOre,
            ItemType.Pickaxe,800, Material.NETHERRACK),

    Copper("copper", Material.COPPER_ORE, GameItem.CopperOre, GameItem.CopperBar, GameItem.CopperOre,
            ItemType.Pickaxe,500, Material.STONE),

    Iron("iron", Material.IRON_ORE, GameItem.IronOre, GameItem.IronBar, GameItem.IronOre,
            ItemType.Pickaxe,600, Material.STONE),

    Gold ("gold", Material.GOLD_ORE, GameItem.GoldOre, GameItem.GoldBar, GameItem.GoldOre,
            ItemType.Pickaxe,700, Material.STONE),

    Diamond("diamond", Material.DIAMOND_ORE, GameItem.Diamond, null, GameItem.DiamondOre,
            ItemType.Pickaxe,900, Material.STONE),

    Emerald("emerald", Material.EMERALD_ORE, GameItem.Emerald, null, GameItem.EmeraldOre,
            ItemType.Pickaxe,950, Material.STONE),

    Obsidian("obsidian", Material.OBSIDIAN, GameItem.Obsidian, null, GameItem.Obsidian,
            ItemType.Pickaxe, 2000000, Material.STONE),

    //Woodcutting
    Oak ("oak", Material.OAK_LOG, GameItem.OakLog, GameItem.Charcoal, GameItem.OakLog,
            ItemType.Axe,200, Material.AIR),

    Spruce("spruce", Material.SPRUCE_LOG, GameItem.SpruceLog, GameItem.Charcoal, GameItem.SpruceLog,
            ItemType.Axe,200, Material.AIR),

    Birch("birch", Material.BIRCH_LOG, GameItem.BirchLog, GameItem.Charcoal, GameItem.BirchLog,
            ItemType.Axe,200, Material.AIR),

    Jungle ("jungle", Material.JUNGLE_LOG, GameItem.JungleLog, GameItem.Charcoal, GameItem.JungleLog,
            ItemType.Axe,200, Material.AIR),

    Acacia("acacia", Material.ACACIA_LOG, GameItem.AcaciaLog, GameItem.Charcoal, GameItem.AcaciaLog,
            ItemType.Axe,200, Material.AIR),

    DarkOak("dark_oak", Material.DARK_OAK_LOG, GameItem.DarkOakLog, GameItem.Charcoal, GameItem.DarkOakLog,
            ItemType.Axe,200, Material.AIR),

    Magrove("magrove", Material.MANGROVE_LOG, GameItem.MangroveLog, GameItem.Charcoal, GameItem.MangroveLog,
            ItemType.Axe,200, Material.AIR),

    //Foraging
    Poppy("poppy", Material.POPPY, GameItem.Poppy, null, GameItem.Poppy,
            ItemType.Shears,0, Material.AIR),
    ;

    private String id;
    private Material material;
    private GameItem drop;
    private GameItem smelted;
    private GameItem silkTouch;
    private ItemType tool;
    private int breakingTime;
    private Material replacement;

    ResourceType(String id, Material material, GameItem drop, GameItem smelted, GameItem silkTouch, ItemType tool, int breakingTime, Material replacement){
        this.id = id;
        this.material = material;
        this.drop = drop;
        this.smelted = smelted;
        this.silkTouch = silkTouch;
        this.tool = tool;
        this.breakingTime = breakingTime;
        this.replacement = replacement;
    }

    public String getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }

    public GameItem getDrop() {
        return drop;
    }

    public GameItem getSmelted() {
        return smelted;
    }

    public GameItem getSilkTouch() {
        return silkTouch;
    }

    public ItemType getTool() {
        return tool;
    }

    public int getBreakingTime() {
        return breakingTime;
    }

    public Material getReplacement() {
        return replacement;
    }

    public static ResourceType getType(String id){
        for(ResourceType type : ResourceType.values()){
            if(type.getId().equals(id)) return type;
        }
        return null;
    }

    public static ResourceType getType(Material material){
        for(ResourceType type : ResourceType.values()){
            if(type.getMaterial().equals(material)) return type;
        }
        return null;
    }
}
