package io.github.nmahdi.JunoCore.player.listeners;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.player.JPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLoginListener implements Listener {

    private JCore main;
    private JPlayerManager playerManager;

    public PlayerLoginListener(JCore main){
        this.main = main;
        this.playerManager = main.getPlayerManager();
        main.getServer().getPluginManager().registerEvents(this, main);
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
