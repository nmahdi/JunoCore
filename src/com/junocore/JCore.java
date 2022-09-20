package com.junocore;

import com.junocore.entity.JEntityCommand;
import com.junocore.entity.JEntityManager;
import com.junocore.hunter.HunterManager;
import com.junocore.item.JItemCommand;
import com.junocore.item.JItemManager;
import com.junocore.item.listeners.EquipmentEquipListener;
import com.junocore.item.listeners.HunterPhoneListener;
import com.junocore.item.listeners.LeafBlowerListener;
import com.junocore.item.listeners.TreeFellerListener;
import com.junocore.player.JPlayerManager;
import com.junocore.player.listeners.JEntityListener;
import com.junocore.player.listeners.PlayerLoginListener;
import com.junocore.player.listeners.PlayerMenuListener;
import com.junocore.player.skills.SkillXPGainListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class JCore extends JavaPlugin {

    /**
     * TODO: Fix LeafBlower and TreeFeller
     */
    private Random random = new Random();
    private JHologramsManager hologramsManager;
    private JItemManager itemManager;
    private JPlayerManager playerManager;
    private HunterManager hunterManager;
    private JEntityManager jEntityManager;


    @Override
    public void onEnable() {
        hologramsManager = new JHologramsManager(this);
        itemManager = new JItemManager();
        playerManager = new JPlayerManager(this);
        hunterManager = new HunterManager();
        jEntityManager = new JEntityManager(this, random);
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new LeafBlowerListener(), this);
        getServer().getPluginManager().registerEvents(new TreeFellerListener(this, itemManager), this);
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerMenuListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new JEntityListener(random, playerManager, jEntityManager, hologramsManager, itemManager), this);
        getServer().getPluginManager().registerEvents(new HunterPhoneListener(itemManager, hunterManager), this);
        new SkillXPGainListener(this);
        new EquipmentEquipListener(this);
    }

    private void registerCommands(){
        getCommand("jitem").setExecutor(new JItemCommand(itemManager));
        getCommand("jentity").setExecutor(new JEntityCommand(jEntityManager));
    }

}
