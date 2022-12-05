package io.github.nmahdi.JunoCore.player.stats;

import io.github.nmahdi.JunoCore.item.builder.ItemStackBuilder;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.display.TextColors;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Skill {
    Combat("combat", "CombatXP", "Combat", 100, new ItemStackBuilder(Material.NETHERITE_SWORD)
            .addLore(Component.text("Kill monsters to level up this skill!").color(TextColors.GRAY)).skipLore().build()),

    Mining("mining", "MiningXP", "Mining", 40, new ItemStackBuilder(Material.NETHERITE_PICKAXE)
            .addLore(Component.text("Mine ores & gems to level up this skill!").color(TextColors.GRAY)).skipLore().build()),

    Foraging("foraging", "ForagingXP", "Foraging", 40, new ItemStackBuilder(Material.SHEARS)
            .addLore(Component.text("Forage for flowers & plants to level up").color(TextColors.GRAY))
            .addLore(Component.text("this skill!").color(TextColors.GRAY)).skipLore().build()),

    Fishing("fishing", "FishingXP", "Fishing", 40, new ItemStackBuilder(Material.FISHING_ROD)
            .addLore(Component.text("Fish treasures & kill underwater monsters").color(TextColors.GRAY))
            .addLore(Component.text("to level up this skill!").color(TextColors.GRAY)).skipLore().build()),

    Woodcutting("woodcutting", "WoodcuttingXP", "Woodcutting", 40, new ItemStackBuilder(Material.NETHERITE_AXE)
            .addLore(Component.text("Cut down trees to level up this skill!").color(TextColors.GRAY)).skipLore().build()),

    Farming("farming", "FarmingXP","Farming", 40, new ItemStackBuilder(Material.NETHERITE_HOE)
            .addLore(Component.text("Harvest crops to level up this skill!").color(TextColors.GRAY)).skipLore().build()),

    MetalDetecting("metal_detecting", "MetalDetectingXP","Metal Detecting", 40, new ItemStackBuilder(Material.LIGHTNING_ROD)
            .addLore(Component.text("Metal detect & find rare treasures to level").color(TextColors.GRAY))
            .addLore(Component.text("up this skill!").color(TextColors.GRAY)).skipLore().build())
    ;

    private String id;
    private String sqlName;
    private String displayName;
    private int maxLevel;
    private ItemStack menuItem;

    Skill(String id, String sqlName, String displayName, int maxLevel, ItemStack menuItem){
        this.id = id;
        this.sqlName = sqlName;
        this.displayName = displayName;
        this.maxLevel = maxLevel;
        this.menuItem = menuItem;
    }

    public String getId() {
        return id;
    }

    public String getSQLName() {
        return sqlName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public ItemStack getMenuItem() {
        return menuItem;
    }

    public static Skill getSkill(String id){
        for(Skill skill : Skill.values()){
            if(skill.getId().equals(id)) return skill;
        }
        return null;
    }

    public static int getCurrentLevel(long xp){
        if(xp > LEVELS[LEVELS.length-1]) return LEVELS.length;
        if(xp < LEVELS[0]) return 0;
        for(int i = 0; i < LEVELS.length; i++){
            if(xp > LEVELS[i] && xp < LEVELS[i+1]){
                return i+1;
            }
        }
        return 0;
    }

    public static long getXPNeeded(GamePlayer player, Skill skill){
        return getXPRequirement(player.getSkill(skill).getLevel())-player.getSkill(skill).getXP();
    }

    public static long getXPRequirement(int currentLevel){
        return LEVELS[currentLevel];
    }

    private static long[] LEVELS = {
            100,//1
            350,//2
            1350,//3
            6350,//4
            12350,//5
            20350,//6
            30350,//7
            45350,//8
            70350,//9
            110350,//10
            170350,//11
            250350,//12
            350350,//13
            500350,//14
            750350,//15
            1150350,//16
            1850350,//17
            2850350,//18
            4250350,//19
            6250350//20
    };


}
