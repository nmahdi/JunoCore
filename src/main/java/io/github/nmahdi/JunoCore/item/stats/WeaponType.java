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
}
