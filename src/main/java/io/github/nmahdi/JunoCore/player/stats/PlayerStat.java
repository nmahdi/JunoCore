package io.github.nmahdi.JunoCore.player.stats;

import io.github.nmahdi.JunoCore.player.display.TextColors;
import io.github.nmahdi.JunoCore.player.display.TextSymbols;
import net.kyori.adventure.text.format.TextColor;

public enum PlayerStat {
    Health("health", "Health", TextSymbols.HEALTH, "%health%", 100),

    MaxHealth("max_health", "Health", TextSymbols.HEALTH, "%max_health%",true, TextColors.HEALTH, Health.baseValue),

    HealthRegen("health_regen", "Health Regeneration", TextSymbols.HEALTH, "%health_regen%", false, MaxHealth.getColor(), 2),

    Mana("mana", "Mana", TextSymbols.MANA, "%mana%", 100),

    MaxMana("max_mana", "Mana", TextSymbols.MANA, "%max_mana%", true, TextColors.MANA, Mana.baseValue),

    ManaRegen("mana_regen", "Mana Regeneration", TextSymbols.MANA, "%mana_regen%", false, MaxMana.getColor(), 2),

    Defense("defense", "Defense", TextSymbols.DEFENSE, "%defense%", true, TextColors.DEFENSE, 0),

    FireElement("fire_element", "Fire Element", TextSymbols.FIRE_ELEMENT, "%fire_element%",true, TextColors.FIRE_ELEMENT, 0),

    WaterElement("water_element", "Water Element", TextSymbols.WATER_ELEMENT, "%water_element%", true, TextColors.WATER_ELEMENT, 0),

    AirElement("air_element", "Air Element", TextSymbols.AIR_ELEMENT, "%air_element%", true, TextColors.AIR_ELEMENT, 0),

    EarthElement("earth_element", "Earth Element", TextSymbols.EARTH_ELEMENT, "%earth_element%", true, TextColors.EARTH_ELEMENT, 0),

    LightningElement("lightning_element", "Lightning Element", TextSymbols.LIGHTNING_ELEMENT, "%lightning_element%", true, TextColors.LIGHTNING_ELEMENT, 0),

    IceElement("ice_element", "Ice Element", TextSymbols.ICE_ELEMENT, "%ice_element%", true, TextColors.ICE_ELEMENT, 0),

    Speed("speed", "Speed", TextSymbols.SPEED, "%speed%", true, TextColors.SPEED, 300),

    Damage("base_damage", "Damage", TextSymbols.DAMAGE, "%damage%", true, TextColors.DAMAGE, 1),

    Strength("strength", "Strength", TextSymbols.STRENGTH, "%strength", true, TextColors.STRENGTH, 0),

    CritChance("crit_chance", "Crit Chance", TextSymbols.CRIT_CHANCE, "%crit_chance%",true, TextColors.CRIT_CHANCE, 20),

    CritDamage("crit_damage", "Crit Damage", TextSymbols.CRIT_DAMAGE, "%crit_damage%",true, TextColors.CRIT_DAMAGE, 20),

    AttackSpeed("attack_speed", "Attack Speed", TextSymbols.ATTACK_SPEED, "%attack_speed%", true, TextColors.ATTACK_SPEED, 0),

    Luck("luck", "Luck", TextSymbols.LUCK, "%luck%", true, TextColors.LUCK, 0),

    Fortune("fortune", "Fortune", TextSymbols.FORTUNE, "%fortune%", true, TextColors.FORTUNE, 0),

    HarvestingSpeed("harvesting_speed", "Harvesting Speed", TextSymbols.HARVESTING_SPEED, "%harvesting_speed%", true, TextColors.HARVESTING_SPEED, 10),

    FishingSpeed("fishing_speed", "Fishing Speed", TextSymbols.FISHING_SPEED, "%fishing_speed%", true, TextColors.FISHING_SPEED, 10)
    ;

    private String id;
    private String displayName;
    private String symbol;
    private String placeHolder;
    private boolean onMenu;
    private TextColor color;
    private int baseValue;

    PlayerStat(String id, String displayName, String symbol, String placeHolder, boolean onMenu, TextColor color, int baseValue){
        this.id = id;
        this.displayName = displayName;
        this.symbol = symbol;
        this.placeHolder = placeHolder;
        this.onMenu = onMenu;
        this.color = color;
        this.baseValue = baseValue;
    }

    PlayerStat(String id, String displayName, String symbol, String placeHolder, int baseValue){
        this.id = id;
        this.displayName = displayName;
        this.symbol = symbol;
        this.placeHolder = placeHolder;
        this.onMenu = false;
        this.baseValue = baseValue;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public boolean isOnMenu() {
        return onMenu;
    }

    public TextColor getColor() {
        return color;
    }

    public int getBaseValue() {
        return baseValue;
    }

}
