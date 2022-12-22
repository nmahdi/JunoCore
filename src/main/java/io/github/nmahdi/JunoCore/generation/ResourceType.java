package io.github.nmahdi.JunoCore.generation;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import org.bukkit.Material;
import org.bukkit.block.Block;

public enum ResourceType {
    //Mining
    Cobblestone("cobblestone", Material.COBBLESTONE, Skill.Mining, 1,
            GameItem.Cobblestone, GameItem.Stone, GameItem.Cobblestone,
            ItemType.Pickaxe, 450, Material.BEDROCK),

    Stone("stone", Material.STONE, Skill.Mining, 1,
            GameItem.Cobblestone, GameItem.Stone, GameItem.Stone,
            ItemType.Pickaxe, 500, Material.BEDROCK),

    Andesite("andsite", Material.ANDESITE, Skill.Mining, 1,
            GameItem.Andesite, null, null,
            ItemType.Pickaxe, 500, Material.BEDROCK),

    Clay("clay", Material.CLAY, Skill.Mining, 5,
            GameItem.Clay, GameItem.Brick, GameItem.ClayBlock,
            ItemType.Shovel,200, Material.AIR),

    Flint("flint", Material.GRAVEL, Skill.Mining, 5,
            GameItem.Flint, null, GameItem.Gravel,
            ItemType.Shovel,200, Material.AIR),

    Coal ("coal", Material.COAL_ORE, Skill.Mining, 5,
            GameItem.Coal, null, GameItem.CoalOre,
            ItemType.Pickaxe,500, Material.STONE),

    LapisLazuli("lapis_lazuli", Material.LAPIS_LAZULI, Skill.Mining, 8,
            GameItem.LapisLazuli, null, GameItem.LapisLazuliOre,
            ItemType.Pickaxe,650, Material.STONE),

    Redstone("redstone", Material.REDSTONE, Skill.Mining, 8,
            GameItem.Redstone, null, GameItem.RedstoneOre,
            ItemType.Pickaxe,650, Material.STONE),

    Quartz("quartz", Material.QUARTZ, Skill.Mining, 15,
            GameItem.Quartz, null, GameItem.QuartzOre,
            ItemType.Pickaxe,800, Material.NETHERRACK),

    Copper("copper", Material.COPPER_ORE, Skill.Mining, 8,
            GameItem.CopperOre, GameItem.CopperBar, GameItem.CopperOre,
            ItemType.Pickaxe,500, Material.STONE),

    Iron("iron", Material.IRON_ORE, Skill.Mining, 10,
            GameItem.IronOre, GameItem.IronBar, GameItem.IronOre,
            ItemType.Pickaxe,600, Material.STONE),

    Gold ("gold", Material.GOLD_ORE, Skill.Mining, 15,
            GameItem.GoldOre, GameItem.GoldBar, GameItem.GoldOre,
            ItemType.Pickaxe,700, Material.STONE),

    Diamond("diamond", Material.DIAMOND_ORE, Skill.Mining, 25,
            GameItem.Diamond, null, GameItem.DiamondOre,
            ItemType.Pickaxe,900, Material.STONE),

    Emerald("emerald", Material.EMERALD_ORE, Skill.Mining, 30,
            GameItem.Emerald, null, GameItem.EmeraldOre,
            ItemType.Pickaxe,950, Material.STONE),

    Obsidian("obsidian", Material.OBSIDIAN, Skill.Mining, 35,
            GameItem.Obsidian, null, GameItem.Obsidian,
            ItemType.Pickaxe, 10000, Material.STONE),

    Mithiril("mithril", true, Material.LIGHT_BLUE_WOOL, Skill.Mining, 60,
            GameItem.Bone, null, null,
            ItemType.Pickaxe, 1000, Material.BEDROCK),

    //Woodcutting
    Oak ("oak", Material.OAK_LOG, Skill.Woodcutting, 5,
            GameItem.OakLog, GameItem.Charcoal, GameItem.OakLog,
            ItemType.Axe,200, Material.AIR),

    Spruce("spruce", Material.SPRUCE_LOG, Skill.Woodcutting, 5,
            GameItem.SpruceLog, GameItem.Charcoal, GameItem.SpruceLog,
            ItemType.Axe,200, Material.AIR),

    Birch("birch", Material.BIRCH_LOG, Skill.Woodcutting, 5,
            GameItem.BirchLog, GameItem.Charcoal, GameItem.BirchLog,
            ItemType.Axe,200, Material.AIR),

    Jungle ("jungle", Material.JUNGLE_LOG, Skill.Woodcutting, 5,
            GameItem.JungleLog, GameItem.Charcoal, GameItem.JungleLog,
            ItemType.Axe,200, Material.AIR),

    Acacia("acacia", Material.ACACIA_LOG, Skill.Woodcutting, 5,
            GameItem.AcaciaLog, GameItem.Charcoal, GameItem.AcaciaLog,
            ItemType.Axe,200, Material.AIR),

    DarkOak("dark_oak", Material.DARK_OAK_LOG, Skill.Woodcutting, 5,
            GameItem.DarkOakLog, GameItem.Charcoal, GameItem.DarkOakLog,
            ItemType.Axe,200, Material.AIR),

    Magrove("magrove", Material.MANGROVE_LOG, Skill.Woodcutting, 5,
            GameItem.MangroveLog, GameItem.Charcoal, GameItem.MangroveLog,
            ItemType.Axe,200, Material.AIR),

    //Foraging
    Poppy("poppy", Material.POPPY, Skill.Foraging, 10,
            GameItem.Poppy, null, GameItem.Poppy,
            ItemType.Shears,0, Material.AIR),
    ;

    private final String id;
    private boolean useTag = false;
    private final Material material;
    private final Skill xpType;
    private final int xp;
    private final GameItem drop;
    private final GameItem smelted;
    private final GameItem silkTouch;
    private final ItemType tool;
    private final int breakingTime;
    private final Material replacement;

    ResourceType(String id, Material material, Skill xpType, int xp, GameItem drop, GameItem smelted, GameItem silkTouch, ItemType tool, int breakingTime, Material replacement){
        this.id = id;
        this.material = material;
        this.xpType = xpType;
        this.xp =xp;
        this.drop = drop;
        this.smelted = smelted;
        this.silkTouch = silkTouch;
        this.tool = tool;
        this.breakingTime = breakingTime;
        this.replacement = replacement;
    }

    ResourceType(String id, boolean useTag, Material material, Skill xpType, int xp, GameItem drop, GameItem smelted, GameItem silkTouch, ItemType tool, int breakingTime, Material replacement){
        this.id = id;
        this.useTag = useTag;
        this.material = material;
        this.xpType = xpType;
        this.xp =xp;
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

    public boolean usesTag() {
        return useTag;
    }

    public Material getMaterial() {
        return material;
    }

    public Skill getXpType() {
        return xpType;
    }

    public int getXp() {
        return xp;
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

    public static ResourceType getType(Block block){
       for(ResourceType type : ResourceType.values()){
           if(type.getMaterial() == block.getType()) return type;
       }
       return null;
    }

}
