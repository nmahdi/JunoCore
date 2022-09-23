package io.github.nmahdi.JunoCore.player.listeners;

import io.github.nmahdi.JunoCore.player.JPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLoginListener implements Listener {

    private JPlayerManager playerManager;

    public PlayerLoginListener(JPlayerManager playerManager){
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        playerManager.login(e.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        playerManager.logout(e.getPlayer());
    }

}
