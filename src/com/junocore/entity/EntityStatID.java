package com.junocore.entity;

public enum EntityStatID {
    ID("id"),
    Name("display_name"),
    Level("level"),
    XP("xp"),
    MaxHealth("max_health"),
    Health("health")
    ;

    private String id;

    EntityStatID(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
