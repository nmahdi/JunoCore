package io.github.nmahdi.JunoCore.player.display;

import io.github.nmahdi.JunoCore.player.GamePlayer;
import io.github.nmahdi.JunoCore.player.PlayerStats;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class ActionBar {

    private GamePlayer player;
    private PlayerStats stats;

    public ActionBar(GamePlayer player) {
        this.player = player;
        this.stats = player.getStats();
    }

    public void send() {
        player.getPlayerObject().sendActionBar(
                Component.text(stats.getHealth() + "/" + stats.getMaxHealth() + PlayerStat.Health.getSymbol() + "          ").color(TextColor.color(PlayerStat.MaxHealth.getColor()))
                        .append(Component.text(stats.getMana() + "/" + stats.getMaxMana() + PlayerStat.Mana.getSymbol()).color(TextColor.color(PlayerStat.MaxMana.getColor()))));
    }

}
