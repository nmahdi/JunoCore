package io.github.nmahdi.JunoCore.entity;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.traits.SmasherTrait;
import io.github.nmahdi.JunoCore.entity.traits.SlimeSplitTrait;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import io.github.nmahdi.JunoCore.dependencies.WorldEditManager;
import io.github.nmahdi.JunoCore.entity.spawnzone.SpawnZone;
import io.github.nmahdi.JunoCore.entity.traits.JunoTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameEntityManager implements JunoManager {

    private final String ENTITY_ID = "entity-id";
    private final String MAX_SPAWNS = "max-spawns";
    private final String SPAWN_DELAY = "spawn-delay-in-seconds";
    private final String LOCATIONS = "locations";

    private Random random;
    private File spawnZoneFolder;
    private ArrayList<SpawnZone> spawnZones = new ArrayList<>();
    private final NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

    public GameEntityManager(JCore main){
        this.random = main.getRandom();
        spawnZoneFolder = new File(main.getDataFolder(), "spawn zones");
        if(!spawnZoneFolder.exists()){
            spawnZoneFolder.mkdirs();
        }
        loadSpawnZones(main.getServer().getWorld("world"));
        JLogger.log("Entity Manager has been enabled.");
    }

    private void loadSpawnZones(World world){
        for(File f : spawnZoneFolder.listFiles()){
            if(f.getName().endsWith(".yml")){
                FileConfiguration c = YamlConfiguration.loadConfiguration(f);
                ArrayList<Location> locations = new ArrayList<>();
                for(String s : c.getStringList(LOCATIONS)){
                    String[] split = s.split(",");
                    locations.add(new Location(world, Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2])));
                }
                spawnZones.add(new SpawnZone(random, f.getName().replace(".yml", ""), GameEntity.getEntity(c.getString(ENTITY_ID)), c.getInt(MAX_SPAWNS),
                        c.getInt(SPAWN_DELAY), locations));
            }
        }
    }

    public void createNewSpawnZone(Player player, World world, String id, GameEntity entity, int maxSpawns, int spawnDelayInSeconds){
        if(!spawnZoneFolder.exists()) return;
        ArrayList<Location> locations = WorldEditManager.getLocationsFromSelection(player, Material.SPONGE);
        SpawnZone zone = new SpawnZone(random, id, entity, maxSpawns, spawnDelayInSeconds, locations);
        addSpawnZone(zone);
        File f = new File(spawnZoneFolder, zone.getId()+".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        saveSpawnZone(f, c, zone);

        player.sendMessage(ChatColor.GREEN + "Spawn Zone [" + id + "] with " + locations.size() + " spawn locations has been created.");
    }

    private void saveSpawnZone(File f, FileConfiguration c, SpawnZone zone){
        c.set(ENTITY_ID, zone.getEntity().getId());
        c.set(MAX_SPAWNS, zone.getMaxSpawns());
        c.set(SPAWN_DELAY, zone.getSpawnDelay());
        ArrayList<String> locs = new ArrayList<>();
        for(Location loc : zone.getSpawnLocations()){
            locs.add(loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ());
        }
        c.set(LOCATIONS, locs);
        try {
            c.save(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addSpawnZone(SpawnZone zone){
        spawnZones.add(zone);
    }

    /**
     *
     * Spawns an entity based on the JEntity enum template with the specified level at the specified location
     *
     * @param entity JEntity enum
     * @param location Location in the world
     */
    public String spawnEntity(GameEntity entity, Location location){
        NPC npc = registry.createNPC(entity.getEntityType(), entity.getDisplayName());
        npc.addTrait(new JunoTrait(entity));
        if(entity.isSlime()){
            npc.addTrait(new SlimeSplitTrait(entity, 4));
        }
        if(entity == GameEntity.Smasher){
            npc.addTrait(new SmasherTrait());
        }
        npc.setProtected(false);
        npc.spawn(location);
        return npc.getUniqueId().toString();
    }

    public String spawnSplitSlime(GameEntity entity, Location location, int size){
        NPC npc = registry.createNPC(entity.getEntityType(), entity.getDisplayName());
        npc.addTrait(new JunoTrait(entity));
        npc.setProtected(false);
        npc.spawn(location);
        return npc.getUniqueId().toString();
    }

    public ArrayList<SpawnZone> getSpawnZones() {
        return spawnZones;
    }

    public NPCRegistry getRegistry() {
        return registry;
    }

    @Override
    public boolean isDebugging() {
        return false;
    }

    @Override
    public void debug(String s) {
        JLogger.debug(this, s);
    }

    @Override
    public void onDisable() {

    }

}
