package com.junocore.item.stats;

import com.junocore.player.PlayerStatID;

public enum ItemStatID {
    Health(PlayerStatID.Health),
    Mana(PlayerStatID.Mana),
    Defense(PlayerStatID.Defense),
    FireDefense(PlayerStatID.FireDefense),
    WaterDefense(PlayerStatID.WaterDefense),
    LightningDefense(PlayerStatID.LightningDefense),
    IceDefense(PlayerStatID.IceDefense),
    Speed(PlayerStatID.Speed),
    Damage(PlayerStatID.Damage),
    Strength(PlayerStatID.Strength),
    CritChance(PlayerStatID.CritChance),
    CritDamage(PlayerStatID.CritDamage),
    Power("power", "Power", "&6\u2726"),

    //Hidden
    WeaponType("weapon_type", true),
    Durability("durability", true),
    MaxDurability("max_durability", true),
    EquipmentSlot("armor_slot", true);

    ;

    private String id;
    private String displayName;

    private String symbol;

    private boolean hidden;

    ItemStatID(PlayerStatID playerID){
        this.id = playerID.getId();
        this.displayName = playerID.getDisplayName();
        this.symbol = playerID.getSymbol();
        this.hidden = false;
    }

    ItemStatID(String id, String displayName, String symbol){
        this.id = id;
        this.displayName = displayName;
        this.symbol = symbol;
        this.hidden = false;
    }

    ItemStatID(String id, boolean hidden){
        this.id = id;
        this.hidden = hidden;
    }

    public String getID(){
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isHidden() {
        return hidden;
    }

}
