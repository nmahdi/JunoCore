package com.junocore.player.skills;

import com.junocore.JCore;
import com.junocore.events.SkillXPGainEvent;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillXPGainListener implements Listener {

    public SkillXPGainListener(JCore main){
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onXPGain(SkillXPGainEvent e){
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
    }

}
