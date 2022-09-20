package com.junocore.player;

public enum PlayerStatID {
    Health("health", "Health", "&4\u2665"),
    MaxHealth("max_health", "Health", "&4\u2665", true, "&c"),
    Mana("mana", "Mana", ""),
    MaxMana("max_mana", "Mana", "", true, "&b"),
    Defense("defense", "Defense", "&2\u22c7", true, "&a"),
    FireDefense("fire_defense", "Fire Defense", "&c\u2668", true, "&c"),
    WaterDefense("water_defense", "Water Defense", "&b\u224b", true, "&b"),
    LightningDefense("lightning_defense", "Lightning Defense", "&e\u26a1", true, "&e"),
    IceDefense("ice_defense", "Ice Defense", "&3\u2746", true, "&b"),
    Speed("speed", "Speed", "&f\u00BB", true, "&f"),
    Damage("base_damage", "Damage", "&4\u273a", true, "&4"),
    Strength("strength", "Strength", "&c\u2735", true, "&4"),
    CritChance("crit_chance", "Crit Chance", "&9\u0025", true, "&9"),
    CritDamage("crit_damage", "Crit Damage", "&9\u2749", true, "&9")
    ;

    private String id;
    private String displayName;
    private String symbol;
    private boolean onMenu;
    private String color;

    PlayerStatID(String id, String displayName, String symbol, boolean onMenu, String color){
        this.id = id;
        this.displayName = displayName;
        this.symbol = symbol;
        this.onMenu = onMenu;
        this.color = color;
    }

    PlayerStatID(String id, String displayName, String symbol){
        this.id = id;
        this.displayName = displayName;
        this.symbol = symbol;
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

    public boolean isOnMenu() {
        return onMenu;
    }

    public String getColor() {
        return color;
    }
}
