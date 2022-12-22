package io.github.nmahdi.JunoCore.entity.spawnzone;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.dependencies.WorldEditManager;
import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SpawnZoneManager implements JunoManager {

	private boolean debugMode;

	private final String ENTITY_ID = "entity-id";
	private final String MAX_SPAWNS = "max-spawns";
	private final String SPAWN_DELAY = "spawn-delay-in-seconds";
	private final String LOCATIONS = "locations";

	private JCore main;
	private GameEntityManager entityManager;
	private Random random;
	private File spawnZoneFolder;

	private ArrayList<SpawnZone> spawnZones = new ArrayList<>();

	public SpawnZoneManager(JCore main, GameEntityManager entityManager){
		this.main = main;
		this.entityManager = entityManager;
		this.debugMode = main.getConfig().getBoolean("debug-mode.spawn-zone");
		this.random = main.getRandom();
		spawnZoneFolder = new File(main.getDataFolder(), "spawn zones");
		if(!spawnZoneFolder.exists()){
			spawnZoneFolder.mkdirs();
		}
		loadSpawnZones(main.getServer().getWorld("world"));
		JLogger.log("Spawn Zone Manager has been enabled.");
	}

	public void tick(){
		for(SpawnZone spawnZone : spawnZones){
			new BukkitRunnable(){

				@Override
				public void run() {
					spawnZone.spawn(entityManager);
				}

			}.runTaskTimer(main, 1, spawnZone.getSpawnDelay()* 20L);
		}
	}

	private void loadSpawnZones(World world){
		for(File f : spawnZoneFolder.listFiles()){
			if(f.getName().endsWith(".yml")) {
				FileConfiguration c = YamlConfiguration.loadConfiguration(f);
				ArrayList<Location> locations = new ArrayList<>();
				for (String s : c.getStringList(LOCATIONS)) {
					String[] split = s.split(",");
					locations.add(new Location(world, Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2])));
				}
				spawnZones.add(new SpawnZone(random, f.getName().replace(".yml", ""), GameEntity.getEntity(c.getString(ENTITY_ID)), c.getInt(MAX_SPAWNS),
						c.getInt(SPAWN_DELAY), locations));
			}
		}
	}

	public void createNewSpawnZone(Player player, String id, GameEntity entity, int maxSpawns, int spawnDelayInSeconds){
		if(!spawnZoneFolder.exists()) return;
		ArrayList<Location> locations = WorldEditManager.getLocationsFromSelection(player, Material.SPONGE);
		SpawnZone spawnZone = new SpawnZone(random, id, entity, maxSpawns, spawnDelayInSeconds, locations);
		File f = new File(spawnZoneFolder, spawnZone.getId()+".yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		saveSpawnZone(f, c, spawnZone);

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

	public ArrayList<SpawnZone> getSpawnZones() {
		return spawnZones;
	}

	@Override
	public void setDebugMode(boolean mode) {
		debugMode = mode;
	}

	@Override
	public boolean isDebugging() {
		return debugMode;
	}

	@Override
	public void onDisable() {

	}
}
