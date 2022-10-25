package io.github.nmahdi.JunoCore.player.skills;

public enum SkillID {
    Combat("combat", "Combat", 100),
    Mining("mining", "Mining", 40),
    Foraging("foraging", "Foraging", 40),
    Fishing("fishing", "Fishing", 40),
    Woodcutting("woodcutting", "Woodcutting", 40),
    Farming("farming", "Farming", 40);
    ;

    private String id;
    private String displayName;
    private int maxLevel;

    SkillID(String id, String displayName, int maxLevel){
        this.id = id;
        this.displayName = displayName;
        this.maxLevel = maxLevel;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
