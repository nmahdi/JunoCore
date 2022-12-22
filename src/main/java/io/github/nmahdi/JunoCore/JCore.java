package io.github.nmahdi.JunoCore;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.nmahdi.JunoCore.dependencies.HologramManager;
import io.github.nmahdi.JunoCore.dependencies.SQLManager;
import io.github.nmahdi.JunoCore.effects.PacketManager;
import io.github.nmahdi.JunoCore.entity.GameEntityCommand;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import io.github.nmahdi.JunoCore.entity.spawnzone.SpawnZoneCommand;
import io.github.nmahdi.JunoCore.generation.GeneratorCommand;
import io.github.nmahdi.JunoCore.generation.ResourceManager;
import io.github.nmahdi.JunoCore.gui.player.PlayerMenuGUI;
import io.github.nmahdi.JunoCore.gui.blacksmith.BlacksmithGUI;
import io.github.nmahdi.JunoCore.gui.runetable.RuneTableGUI;
import io.github.nmahdi.JunoCore.item.GameItemCommand;
import io.github.nmahdi.JunoCore.item.ItemManager;
import io.github.nmahdi.JunoCore.item.listeners.ConsumableListener;
import io.github.nmahdi.JunoCore.player.listeners.GameItemPickupListener;
import io.github.nmahdi.JunoCore.player.stats.CoinCommand;
import io.github.nmahdi.JunoCore.player.listeners.PlayerInventoryListener;
import io.github.nmahdi.JunoCore.player.PlayerManager;
import io.github.nmahdi.JunoCore.player.listeners.combat.HealCommand;
import io.github.nmahdi.JunoCore.player.listeners.combat.PlayerCombatListener;
import io.github.nmahdi.JunoCore.player.listeners.resource.PlayerResourceListener;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class JCore extends JavaPlugin implements Listener {

    private WorldManager worldManager;
    private RegionContainer regionContainer;
    private RegionManager regionManager;

    private Random random = new Random();

    private SQLManager sqlManager;
    private PacketManager packetManager;
    private HologramManager hologramsManager;
    private ItemManager itemManager;
    private PlayerManager playerManager;
    private GameEntityManager entityManager;
    private ResourceManager resourceManager;


    @Override
    public void onEnable() {
        if(getServer().getPluginManager().getPlugin("Citizens") == null || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
            JLogger.error("Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        worldManager = new WorldManager(this);

        regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        regionManager = regionContainer.get(BukkitAdapter.adapt(getPrimaryWorld()));

        sqlManager = new SQLManager(this);
        packetManager = new PacketManager(this);
        hologramsManager = new HologramManager(this);
        entityManager = new GameEntityManager(this);
        itemManager = new ItemManager(this);
        playerManager = new PlayerManager(this);
        resourceManager = new ResourceManager(this);

        itemManager.postInit();
        entityManager.postInit();

        getServer().clearRecipes();

        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        sqlManager.onDisable();
        packetManager.onDisable();
        hologramsManager.onDisable();
        playerManager.onDisable();
        entityManager.onDisable();
        resourceManager.onDisable();
    }

    @EventHandler
    public void serverPing(PaperServerListPingEvent e){
        e.setNumPlayers(5);
        e.setHidePlayers(true);
        e.setMotd(ChatColor.translateAlternateColorCodes('&',
                "&6&l--------------[&c&lJunoMMO&6&l]--------------\n          &e&lUnder Heavy Development."));
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(this, this);
        //Player
        new PlayerCombatListener(this);
        new PlayerResourceListener(this);

        //GUI
        new PlayerMenuGUI(this);
        new BlacksmithGUI(this);
        new RuneTableGUI(this);

        //Items
        new PlayerInventoryListener(this);
        new ConsumableListener(this);
        new GameItemPickupListener(this);
    }

    private void registerCommands(){
        new GameItemCommand(this);
        new GameEntityCommand(this);
        new SpawnZoneCommand(this);
        new CustomSkullCommand(this);
        new GeneratorCommand(this);
        new CoinCommand(this);
        new HealCommand(this);
        new JunoCommand(this);
    }

    public World getPrimaryWorld() {
        return worldManager.getPrimaryWorld();
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public RegionContainer getRegionContainer() {
        return regionContainer;
    }

    public RegionManager getRegionManager() {
        return regionManager;
    }

    public Random getRandom() {
        return random;
    }

    public SQLManager getSQLManager() {
        return sqlManager;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public GameEntityManager getEntityManager(){
        return entityManager;
    }

    public HologramManager getHologramsManager() {
        return hologramsManager;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

}
