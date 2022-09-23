package io.github.nmahdi.JunoCore.hunter;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class HunterManager {

    private HashMap<Player, HunterQuest> quests = new HashMap<>();

    public HunterManager(){

    }

    /**
     *
     * @param user User
     * @param questId HunterQuestID
     */

    public void startQuest(Player player, HunterQuestID questId){
        quests.put(player, new HunterQuest(questId, 0));
    }

    public boolean hasQuest(Player player){
        if(quests.containsKey(player)){
            return true;
        }
        return false;
    }

    public enum HunterQuestID{
        Zombie(10, 1000);

        int price;
        int totalXp;

        HunterQuestID(int price, int totalXp){
            this.price = price;
            this.totalXp = totalXp;
        }

        public int getPrice() {
            return price;
        }

        public int getTotalXp() {
            return totalXp;
        }
    }

}
