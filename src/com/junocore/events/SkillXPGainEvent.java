package com.junocore.events;

import com.junocore.player.skills.SkillID;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillXPGainEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private SkillID skill;
    private int amount;


    public SkillXPGainEvent(Player player, SkillID skill, int amount){
        this.player = player;
        this.skill = skill;
        this.amount = amount;
    }

    public Player getPlayer() {
        return player;
    }

    public SkillID getSkill() {
        return skill;
    }

    public void setSkill(SkillID skill) {
        this.skill = skill;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
