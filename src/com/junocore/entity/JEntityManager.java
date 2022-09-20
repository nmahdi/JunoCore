package com.junocore.entity;

import com.junocore.JCore;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.formatting.text.TextComponent;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JEntityManager {

    private final String ENTITY_ID = "entity-id";
    private final String MAX_SPAWNS = "max-spawns";
    private final String SPAWN_DELAY = "spawn-delay-in-seconds";
    private final String LOCATIONS = "locations";

    private Random random;
    private File spawnZoneFolder;
    private ArrayList<SpawnZone> spawnZones = new ArrayList<>();

    public JEntityManager(JCore main, Random random){
        this.random = random;
        spawnZoneFolder = new File(main.getDataFolder(), "spawn zones");
        if(!spawnZoneFolder.exists()){
            spawnZoneFolder.mkdirs();
        }
        loadSpawnZones(main.getServer().getWorld("world"));
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
                spawnZones.add(new SpawnZone(random, f.getName().replace(".yml", ""), getEntityByID(c.getString(ENTITY_ID)), c.getInt(MAX_SPAWNS),
                        c.getInt(SPAWN_DELAY), locations));
            }
        }
    }

    public void createNewSpawnZone(Player player, World world, String id, JEntity entity, int maxSpawns, int spawnDelayInSeconds){
        if(!spawnZoneFolder.exists()) return;
        ArrayList<Location> locations = new ArrayList<>();
        com.sk89q.worldedit.entity.Player actor = BukkitAdapter.adapt(player);
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(actor);
        com.sk89q.worldedit.world.World selectionWorld = session.getSelectionWorld();
        try {
            if (selectionWorld == null) throw new IncompleteRegionException();
            Region region = session.getSelection(selectionWorld);
            for(BlockVector3 point : region){
                if(world.getBlockAt(point.getBlockX(), point.getBlockY(), point.getBlockZ()).getType() == Material.SPONGE){
                    locations.add(new Location(world, point.getBlockX(), point.getBlockY(), point.getBlockZ()));
                }
            }
        } catch (IncompleteRegionException ex) {
            actor.printError(TextComponent.of("Please make a region selection first."));
        }
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
     * @param level Level
     * @param location Location in the world
     */
    public String spawnEntity(JEntity entity, int level, Location location){
        Entity ent = location.getWorld().spawnEntity(location, entity.getEntityType());
        if(entity.getEntityType() == EntityType.ZOMBIE){
            ((Zombie)ent).getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        }
        ent.setCustomNameVisible(true);
        NBTEntity nbtE = new NBTEntity(ent);
        NBTCompound juno = nbtE.getPersistentDataContainer().addCompound("juno");
        double health = entity.getBaseHealth()+((0.1*(level-1))*entity.getBaseHealth());
        juno.setString(EntityStatID.ID.getId(), entity.getId());
        juno.setString(EntityStatID.Name.getId(), entity.getDisplayName());
        juno.setInteger(EntityStatID.Level.getId(), level);
        juno.setInteger(EntityStatID.XP.getId(), entity.getXP());
        juno.setInteger(EntityStatID.MaxHealth.getId(), (int)health);
        juno.setInteger(EntityStatID.Health.getId(), (int)health);
        setName(nbtE);
        return ent.getUniqueId().toString();
    }

    public void setName(NBTEntity entity){
        entity.setString("CustomName", getName(entity));
    }

    public String getName(NBTEntity nbtEntity){
        NBTCompound juno = nbtEntity.getPersistentDataContainer().getCompound("juno");
        int health = juno.getInteger(EntityStatID.Health.getId());
        int maxHealth = juno.getInteger(EntityStatID.MaxHealth.getId());
        return "\"" + ChatColor.translateAlternateColorCodes('&',
                "&7[Lvl." + juno.getInteger(EntityStatID.Level.getId()) + "] &c" + juno.getString(EntityStatID.Name.getId()) + " &" +
                        getHealthColor(health, maxHealth) + health + "&7/&a" + maxHealth
                ) + "\"";
    }

    private char getHealthColor(double health, double maxHealth){
        double percentage = (health/maxHealth)*100;
        if(percentage >= 70) return 'a';
        if(percentage >= 20) return 'e';
        return '4';
    }

    public JEntity getEntityByID(String id){
        for(int i = 0; i < JEntity.values().length; i++){
            if(JEntity.values()[i].getId().equalsIgnoreCase(id)){
                return JEntity.values()[i];
            }
        }
        return null;
    }

    public ArrayList<SpawnZone> getSpawnZones() {
        return spawnZones;
    }
}
