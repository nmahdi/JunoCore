package io.github.nmahdi.JunoCore.item.stats;

public enum WeaponType {
    Sword("sword"),
    Bow("bow");

    private String id;

    WeaponType(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static WeaponType getWeaponType(String id){
        for(WeaponType type : WeaponType.values()){
            if(type.getId().equalsIgnoreCase(id)){
                return type;
            }
        }
        return null;
    }
}
