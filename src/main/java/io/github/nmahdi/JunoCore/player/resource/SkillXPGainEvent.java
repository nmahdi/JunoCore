package io.github.nmahdi.JunoCore.player.resource;

import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SkillXPGainEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private GamePlayer player;
    private Skill skill;
    private int amount;


    public SkillXPGainEvent(GamePlayer player, Skill skill, int amount){
        this.player = player;
        this.skill = skill;
        this.amount = amount;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
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
