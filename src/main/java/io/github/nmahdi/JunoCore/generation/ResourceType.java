package io.github.nmahdi.JunoCore.generation;

import io.github.nmahdi.JunoCore.item.GameItem;
import io.github.nmahdi.JunoCore.item.stats.ItemType;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class ResourceType {
    /*//Mining
    Cobblestone("cobblestone", Material.COBBLESTONE, Skill.Mining, 1,
            GameItem.Cobblestone, GameItem.Stone, null, 1,
            ItemType.Pickaxe, 450, Material.BEDROCK),

    Stone("stone", Material.STONE, Skill.Mining, 1,
            GameItem.Cobblestone, GameItem.Stone, GameItem.Stone, 1,
            ItemType.Pickaxe, 500, Material.BEDROCK),

    Andesite("andsite", Material.ANDESITE, Skill.Mining, 1,
            GameItem.Andesite, null, null, 1,
            ItemType.Pickaxe, 500, Material.BEDROCK),

    Clay("clay", Material.CLAY, Skill.Mining, 5,
            GameItem.Clay, null, GameItem.ClayBlock, 2,
            ItemType.Shovel,200, Material.AIR),

    Flint("flint", Material.GRAVEL, Skill.Mining, 5,
            GameItem.Flint, null, GameItem.Gravel, 1,
            ItemType.Shovel,200, Material.AIR),

    Coal ("coal", Material.COAL_ORE, Skill.Mining, 5,
            GameItem.Coal, null, GameItem.CoalOre, 1,
            ItemType.Pickaxe,500, Material.STONE),

    Iron("iron", Material.IRON_ORE, Skill.Mining, 10,
            GameItem.IronOre, GameItem.IronBar, null, 1,
            ItemType.Pickaxe,600, Material.STONE),

    Gold ("gold", Material.GOLD_ORE, Skill.Mining, 15,
            GameItem.GoldOre, GameItem.GoldBar, null, 1,
            ItemType.Pickaxe,700, Material.STONE),

    Diamond("diamond", Material.DIAMOND_ORE, Skill.Mining, 25,
            GameItem.Diamond, null, GameItem.DiamondOre, 1,
            ItemType.Pickaxe,900, Material.STONE),

    Emerald("emerald", Material.EMERALD_ORE, Skill.Mining, 30,
            GameItem.Emerald, null, GameItem.EmeraldOre, 1,
            ItemType.Pickaxe,950, Material.STONE),

    Obsidian("obsidian", Material.OBSIDIAN, Skill.Mining, 35,
            GameItem.Obsidian, null, null, 1,
            ItemType.Pickaxe, 10000, Material.STONE),

    Eternite("eternite", true, Material.GRAY_WOOL, Skill.Mining, 60,
            GameItem.EterniteBar, null, null, 1,
            ItemType.Pickaxe, 1000, Material.BEDROCK),

    //Woodcutting
    Oak ("oak", Material.OAK_LOG, Skill.Woodcutting, 5,
            GameItem.OakWood, GameItem.Charcoal, null, 1,
            ItemType.Axe,200, Material.AIR),

    Spruce("spruce", Material.SPRUCE_LOG, Skill.Woodcutting, 5,
            GameItem.SpruceWood, GameItem.Charcoal, null, 1,
            ItemType.Axe,200, Material.AIR),

    Birch("birch", Material.BIRCH_LOG, Skill.Woodcutting, 5,
            GameItem.BirchWood, GameItem.Charcoal, null, 1,
            ItemType.Axe,200, Material.AIR),

    Jungle ("jungle", Material.JUNGLE_LOG, Skill.Woodcutting, 5,
            GameItem.JungleWood, GameItem.Charcoal, null, 1,
            ItemType.Axe,200, Material.AIR),

    Acacia("acacia", Material.ACACIA_LOG, Skill.Woodcutting, 5,
            GameItem.AcaciaWood, GameItem.Charcoal, null, 1,
            ItemType.Axe,200, Material.AIR),

    DarkOak("dark_oak", Material.DARK_OAK_LOG, Skill.Woodcutting, 5,
            GameItem.DarkOakWood, GameItem.Charcoal, null, 1,
            ItemType.Axe,200, Material.AIR),

    Magrove("magrove", Material.MANGROVE_LOG, Skill.Woodcutting, 5,
            GameItem.MangroveWood, GameItem.Charcoal, null, 1,
            ItemType.Axe,200, Material.AIR),

    //Foraging
    Poppy("poppy", Material.POPPY, Skill.Foraging, 10,
            GameItem.Poppy, null, null, 1,
            ItemType.Shears,0, Material.AIR),
    ; */

    private final String id;
    private boolean useTag = false;
    private final Material material;
    private final Skill xpType;
    private final int xp;
    private final GameItem drop;
    private final GameItem smelted;
    private final GameItem silkTouch;
    private final int amount;
    private final ItemType tool;
    private final int breakingTime;
    private final Material replacement;

    public ResourceType(ResourceManager resourceManager, String id, Material material, Skill xpType, int xp, GameItem drop, GameItem smelted, GameItem silkTouch, int amount, ItemType tool, int breakingTime, Material replacement){
        this.id = id;
        this.material = material;
        this.xpType = xpType;
        this.xp =xp;
        this.drop = drop;
        this.smelted = smelted;
        this.silkTouch = silkTouch;
        this.amount = amount;
        this.tool = tool;
        this.breakingTime = breakingTime;
        this.replacement = replacement;
        resourceManager.addResource(this);
    }

    public ResourceType(ResourceManager resourceManager, String id, boolean useTag, Material material, Skill xpType, int xp, GameItem drop, GameItem smelted, GameItem silkTouch, int amount, ItemType tool, int breakingTime, Material replacement){
        this.id = id;
        this.useTag = useTag;
        this.material = material;
        this.xpType = xpType;
        this.xp =xp;
        this.drop = drop;
        this.smelted = smelted;
        this.silkTouch = silkTouch;
        this.amount = amount;
        this.tool = tool;
        this.breakingTime = breakingTime;
        this.replacement = replacement;
        resourceManager.addResource(this);
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

    public int getAmount() {
        return amount;
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

}
