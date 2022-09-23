package io.github.nmahdi.JunoCore.player.skills;

import de.tr7zw.nbtapi.NBTCompound;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.events.SkillXPGainEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SkillXPGainListener implements Listener {

    public SkillXPGainListener(JCore main){
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onXPGain(SkillXPGainEvent e){
        NBTCompound skills = e.getJuno().getCompound("skills");
        skills.getCompound(e.getSkill().getId()).setInteger("xp", skills.getCompound(e.getSkill().getId()).getInteger("xp")+e.getAmount());
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(e.getBlock().getType().isAir()) return;
        if(e.getBlock().getType().toString().contains("LOG")){
            Bukkit.getServer().getPluginManager().callEvent(new SkillXPGainEvent(e.getPlayer(), SkillID.Foraging, BlockXPAmount.Logs.getAmount()));
        }
    }

    public enum BlockXPAmount{
        Logs(5);

        private int amount;

        BlockXPAmount(int amount){
            this.amount = amount;
        }

        public int getAmount() {
            return amount;
        }
    }

}
