package io.github.nmahdi.JunoCore.player;

public enum PlayerStatID {
    Health("health", "Health", "&4\u2665", "%health%"),
    MaxHealth("max_health", "Health", "&4\u2665", "%max_health",true, "&c"),
    Mana("mana", "Mana", "", "%mana%"),
    MaxMana("max_mana", "Mana", "", "%max_mana%", true, "&b"),
    Defense("defense", "Defense", "&2\u22c7", "%defense%", true, "&a"),
    FireDefense("fire_defense", "Fire Defense", "&c\u2668", "%fire_defense%",true, "&c"),
    WaterDefense("water_defense", "Water Defense", "&b\u224b", "%water_defense%", true, "&b"),
    LightningDefense("lightning_defense", "Lightning Defense", "&e\u26a1", "%lightning_defense%", true, "&e"),
    IceDefense("ice_defense", "Ice Defense", "&3\u2746", "%ice_defense%", true, "&b"),
    Speed("speed", "Speed", "&f\u00BB", "%speed%", true, "&f"),
    Damage("base_damage", "Damage", "&4\u273a", "%damage%", true, "&4"),
    Strength("strength", "Strength", "&c\u2735", "%strength", true, "&4"),
    CritChance("crit_chance", "Crit Chance", "&9\u0025", "%crit_chance%",true, "&9"),
    CritDamage("crit_damage", "Crit Damage", "&9\u2749", "%crit_damage%",true, "&9")
    ;

    private String id;
    private String displayName;
    private String symbol;
    private String actionBarID;
    private boolean onMenu;
    private String color;

    PlayerStatID(String id, String displayName, String symbol, String actionBarID, boolean onMenu, String color){
        this.id = id;
        this.displayName = displayName;
        this.symbol = symbol;
        this.actionBarID = actionBarID;
        this.onMenu = onMenu;
        this.color = color;
    }

    PlayerStatID(String id, String displayName, String symbol, String actionBarID){
        this.id = id;
        this.displayName = displayName;
        this.symbol = symbol;
        this.actionBarID = actionBarID;
        this.onMenu = false;
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

    public String getActionBarID() {
        return actionBarID;
    }

    public boolean isOnMenu() {
        return onMenu;
    }

    public String getColor() {
        return color;
    }
}
