package io.github.nmahdi.JunoCore.item.stats;

import io.github.nmahdi.JunoCore.player.PlayerStatID;

public enum ItemStatID {
    MaxHealth(PlayerStatID.MaxHealth),
    Health(PlayerStatID.Health),
    MaxMana(PlayerStatID.MaxMana),
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
    BreakingPower("breaking_power", "Breaking Power", "&6\u2726"),

    //Hidden
    WeaponType("weapon_type"),
    Durability("durability"),
    MaxDurability("max_durability"),
    EquipmentSlot("armor_slot"),
    Consumable("consumable"),
    Dismantlable ("dismantlable"),
    UUID("uuid"),
    ;

    private String id;
    private String displayName;

    private String symbol;


    ItemStatID(PlayerStatID playerID){
        this.id = playerID.getId();
        this.displayName = playerID.getDisplayName();
        this.symbol = playerID.getSymbol();
    }

    ItemStatID(String id, String displayName, String symbol){
        this.id = id;
        this.displayName = displayName;
        this.symbol = symbol;
    }

    ItemStatID(String id){
        this.id = id;
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

    public static ItemStatID getID(String id){
        for(int i = 0; i < ItemStatID.values().length; i++){
            if(ItemStatID.values()[i].getID().equalsIgnoreCase(id)){
                return ItemStatID.values()[i];
            }
        }
        return null;
    }

    public static boolean isPlayerStat(ItemStatID stat){
        for(PlayerStatID id : PlayerStatID.values()){
            if(id.getId().equals(stat.getID())) return true;
        }
        return false;
    }

}
