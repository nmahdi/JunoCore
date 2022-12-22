package io.github.nmahdi.JunoCore.player.display;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerStats;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import io.github.nmahdi.JunoCore.player.stats.Skill;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.*;

public class ActionBar {

    private final String empty = "          ";
    private JCore main;
    private GamePlayer player;
    private PlayerStats stats;
    private Component current;
    private int currentID = -1;

    public ActionBar(JCore main, GamePlayer player) {
        this.main = main;
        this.player = player;
        this.stats = player.getStats();
        this.current = Component.text(empty);
    }

    private void setCurrent(Component component){
        if(currentID != -1){
            main.getServer().getScheduler().cancelTask(currentID);
        }
        current = component;
        currentID = new BukkitRunnable(){

            @Override
            public void run() {
                current = Component.text(empty);
                currentID = -1;
            }

        }.runTaskLater(main, 20).getTaskId();
    }

    public void gainXP(Skill skill, int amount){
        setCurrent(Component.text(skill.getDisplayName() + " +" + amount + " XP").color(NamedTextColor.BLUE));
    }

    public void heal(double amount){
        setCurrent(Component.text("+" + amount + PlayerStat.MaxHealth.getSymbol()).color(PlayerStat.MaxHealth.getColor()));
    }

    public void send() {
        player.getPlayerObject().sendActionBar(
                Component.text((int)stats.getHealth() + "/" + (int)stats.getMaxHealth() + PlayerStat.MaxHealth.getSymbol() + " ").color(TextColor.color(PlayerStat.MaxHealth.getColor()))
                        .append(current)
                        .append(Component.text(" " + (int)stats.getMana() + "/" + (int)stats.getMaxMana() + PlayerStat.Mana.getSymbol()).color(TextColor.color(PlayerStat.MaxMana.getColor()))));
    }

}
