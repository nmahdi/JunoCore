package com.junocore.hunter;

public class HunterQuest {

    private HunterManager.HunterQuestID questID;
    private int currentXp;

    public HunterQuest(HunterManager.HunterQuestID questID, int currentXp){
        this.questID = questID;
        this.currentXp = currentXp;
    }

    public HunterManager.HunterQuestID getQuestID() {
        return questID;
    }

    public int getCurrentXp() {
        return currentXp;
    }

    public void setCurrentXp(int currentXp) {
        this.currentXp = currentXp;
    }

    public int getTotalXP(){
        return questID.getTotalXp();
    }

}
