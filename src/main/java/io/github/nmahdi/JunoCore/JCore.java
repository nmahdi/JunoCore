package io.github.nmahdi.JunoCore;

import io.github.nmahdi.JunoCore.entity.JEntityCommand;
import io.github.nmahdi.JunoCore.entity.JEntityManager;
import io.github.nmahdi.JunoCore.gui.PlayerMenuGUI;
import io.github.nmahdi.JunoCore.gui.blacksmith.BlacksmithGUI;
import io.github.nmahdi.JunoCore.hunter.HunterManager;
import io.github.nmahdi.JunoCore.item.JItemCommand;
import io.github.nmahdi.JunoCore.item.listeners.ConsumableListener;
import io.github.nmahdi.JunoCore.player.listeners.EquipmentEquipListener;
import io.github.nmahdi.JunoCore.item.listeners.TreeFellerListener;
import io.github.nmahdi.JunoCore.player.JPlayerManager;
import io.github.nmahdi.JunoCore.player.listeners.PlayerCombatListener;
import io.github.nmahdi.JunoCore.player.listeners.PlayerLoginListener;
import io.github.nmahdi.JunoCore.player.skills.SkillXPGainListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.logging.Level;

public class JCore extends JavaPlugin{

    
    private Random random = new Random();
    private JHologramsManager hologramsManager;
    private JPlayerManager playerManager;
    private HunterManager hunterManager;
    private JEntityManager jEntityManager;


    @Override
    public void onEnable() {
        if(getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        hologramsManager = new JHologramsManager(this);
        playerManager = new JPlayerManager(this);
        hunterManager = new HunterManager();
        jEntityManager = new JEntityManager(this, random);
        registerListeners();
        registerCommands();
        //CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(JunoTrait.class));
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners(){
        new TreeFellerListener(this);
        new PlayerLoginListener(this);
        new PlayerMenuGUI(this);
        new PlayerCombatListener(this);
        new SkillXPGainListener(this);
        new EquipmentEquipListener(this);
        new ConsumableListener(this);
        new BlacksmithGUI(this);
    }

    private void registerCommands(){
        getCommand("jitem").setExecutor(new JItemCommand());
        getCommand("jentity").setExecutor(new JEntityCommand(jEntityManager));
    }

    public Random getRandom() {
        return random;
    }

    public JEntityManager getEntityManager(){
        return jEntityManager;
    }

    public JPlayerManager getPlayerManager() {
        return playerManager;
    }

    public HunterManager getHunterManager() {
        return hunterManager;
    }

    public JHologramsManager getHologramsManager() {
        return hologramsManager;
    }


}
