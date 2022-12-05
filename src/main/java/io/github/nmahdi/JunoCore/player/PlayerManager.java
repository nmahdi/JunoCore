package io.github.nmahdi.JunoCore.player;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.dependencies.SQLManager;
import io.github.nmahdi.JunoCore.generation.ResourceManager;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.player.display.ScoreboardManager;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import io.github.nmahdi.JunoCore.player.display.ActionBar;
import io.github.nmahdi.JunoCore.player.stats.PlayerStat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PlayerManager implements Listener, JunoManager {

    private JCore main;
    private SQLManager sqlManager;
    private ScoreboardManager scoreboardManager;

    private ArrayList<GamePlayer> players = new ArrayList<>();

    public PlayerManager(JCore main){
        this.main = main;
        this.main.getServer().getPluginManager().registerEvents(this, main);
        this.sqlManager = main.getSQLManager();
        this.scoreboardManager = new ScoreboardManager(main);
        for(Player player : main.getServer().getOnlinePlayers()){
            login(new GamePlayer(player));
        }
        JLogger.log("Player Manager has been enabled.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e){
        login(new GamePlayer(e.getPlayer()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent e){
        logout(getPlayer(e.getPlayer()));
    }

    public GamePlayer getPlayer(Player p){
        for(GamePlayer player : players){
            if(player.getName().equals(p.getName())) return player;
        }
        return null;
    }

    private void login(GamePlayer player){
        sqlManager.loadPlayerData(player);
        player.login(main, scoreboardManager);
        players.add(player);
    }

    private void logout(GamePlayer player){
        sqlManager.savePlayerData(player);
        player.logout(main);
        players.remove(player);
    }

    @Override
    public boolean isDebugging() {
        return false;
    }

    @Override
    public void onDisable() {
        scoreboardManager.onDisable();
        for(Player player : main.getServer().getOnlinePlayers()){
            logout(getPlayer(player));
        }
    }

}
