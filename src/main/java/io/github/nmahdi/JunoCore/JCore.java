package io.github.nmahdi.JunoCore;

import io.github.nmahdi.JunoCore.entity.JEntityCommand;
import io.github.nmahdi.JunoCore.entity.JEntityManager;
import io.github.nmahdi.JunoCore.entity.npc.BlacksmithNPC;
import io.github.nmahdi.JunoCore.hunter.HunterManager;
import io.github.nmahdi.JunoCore.item.JItemCommand;
import io.github.nmahdi.JunoCore.item.JItemManager;
import io.github.nmahdi.JunoCore.item.crafting.CraftingManager;
import io.github.nmahdi.JunoCore.item.listeners.ConsumableListener;
import io.github.nmahdi.JunoCore.player.listeners.EquipmentEquipListener;
import io.github.nmahdi.JunoCore.item.listeners.HunterPhoneListener;
import io.github.nmahdi.JunoCore.item.listeners.LeafBlowerListener;
import io.github.nmahdi.JunoCore.item.listeners.TreeFellerListener;
import io.github.nmahdi.JunoCore.player.JPlayerManager;
import io.github.nmahdi.JunoCore.player.listeners.JEntityListener;
import io.github.nmahdi.JunoCore.player.listeners.PlayerLoginListener;
import io.github.nmahdi.JunoCore.player.listeners.PlayerMenuListener;
import io.github.nmahdi.JunoCore.player.skills.SkillXPGainListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class JCore extends JavaPlugin {

    
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
        new PlayerMenuListener(this, playerManager);
        getServer().getPluginManager().registerEvents(new JEntityListener(random, playerManager, jEntityManager, hologramsManager, itemManager), this);
        getServer().getPluginManager().registerEvents(new HunterPhoneListener(itemManager, hunterManager), this);
        new SkillXPGainListener(this);
        new EquipmentEquipListener(this, itemManager);
        new BlacksmithNPC(this, itemManager);
        new ConsumableListener(this);
    }

    private void registerCommands(){
        getCommand("jitem").setExecutor(new JItemCommand(itemManager));
        getCommand("jentity").setExecutor(new JEntityCommand(jEntityManager));
    }

}
