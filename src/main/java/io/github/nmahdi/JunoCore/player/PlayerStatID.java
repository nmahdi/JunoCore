package io.github.nmahdi.JunoCore.player;

public enum PlayerStatID {
    Health("health", "Health", "&4\u2665", "%health%", 100),
    MaxHealth("max_health", "Health", "&4\u2665", "%max_health%",true, "&c", Health.baseValue),
    Mana("mana", "Mana", "", "%mana%", 100),
    MaxMana("max_mana", "Mana", "", "%max_mana%", true, "&b", Mana.baseValue),
    Defense("defense", "Defense", "&2\u22c7", "%defense%", true, "&a", 0),
    FireDefense("fire_defense", "Fire Defense", "&c\u2668", "%fire_defense%",true, "&c", 0),
    WaterDefense("water_defense", "Water Defense", "&b\u224b", "%water_defense%", true, "&b", 0),
    LightningDefense("lightning_defense", "Lightning Defense", "&e\u26a1", "%lightning_defense%", true, "&e", 0),
    IceDefense("ice_defense", "Ice Defense", "&3\u2746", "%ice_defense%", true, "&b", 0),
    Speed("speed", "Speed", "&f\u00BB", "%speed%", true, "&f", 100),
    Damage("base_damage", "Damage", "&4\u273a", "%damage%", true, "&4", 1),
    Strength("strength", "Strength", "&c\u2735", "%strength", true, "&4", 0),
    CritChance("crit_chance", "Crit Chance", "&9\u0025", "%crit_chance%",true, "&9", 30),
    CritDamage("crit_damage", "Crit Damage", "&9\u2749", "%crit_damage%",true, "&9", 20),
    Luck("luck", "Luck", "", "%luck%", true, "&b", 0),
    Fortune("fortune", "Fortune", "", "%fortune%", true, "&5", 0)
    ;

    private String id;
    private String displayName;
    private String symbol;
    private String placeHolder;
    private boolean onMenu;
    private String color;
    private Number baseValue;

    PlayerStatID(String id, String displayName, String symbol, String placeHolder, boolean onMenu, String color, Number baseValue){
        this.id = id;
        this.displayName = displayName;
        this.symbol = symbol;
        this.placeHolder = placeHolder;
        this.onMenu = onMenu;
        this.color = color;
        this.baseValue = baseValue;
    }

    PlayerStatID(String id, String displayName, String symbol, String placeHolder, Number baseValue){
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

    public String getColor() {
        return color;
    }

    public Number getBaseValue() {
        return baseValue;
    }

}
