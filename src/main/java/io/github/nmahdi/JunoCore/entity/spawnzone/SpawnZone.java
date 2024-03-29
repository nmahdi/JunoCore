package io.github.nmahdi.JunoCore.entity.spawnzone;

import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.entity.GameEntityManager;
import org.bukkit.Location;

import java.util.*;

public class SpawnZone {

    private Random random;
    private HashMap<Location, ArrayList<String>> spawnedEntities = new HashMap<>();
    private ArrayList<Location> spawnLocations = new ArrayList<>();
    private String id;
    private GameEntity entity;
    private int maxSpawns;
    private int spawnDelay;

    public SpawnZone(Random random, String id, GameEntity entity, int maxSpawns, int spawnDelay, Location... spawnLocations){
        this.random = random;
        this.id = id;
        this.entity = entity;
        this.maxSpawns = maxSpawns;
        this.spawnDelay = spawnDelay;
        this.spawnLocations.addAll(Arrays.asList(spawnLocations));
        init();
    }

    public SpawnZone(Random random, String id, GameEntity entity, int maxSpawns, int spawnDelay, ArrayList<Location> spawnLocations){
        this.random = random;
        this.id = id;
        this.entity = entity;
        this.maxSpawns = maxSpawns;
        this.spawnDelay = spawnDelay;
        this.spawnLocations.addAll(spawnLocations);
        init();
    }

    private void init(){
        for(Location loc : spawnLocations){
            spawnedEntities.put(loc, new ArrayList<>());
        }
    }

    public void spawn(GameEntityManager entityManager){
        for(Map.Entry<Location, ArrayList<String>> map : spawnedEntities.entrySet()){
            if(map.getValue().size() < maxSpawns){
                map.getValue().add(entityManager.spawnEntity(entity, map.getKey()).getUniqueId().toString());
            }
        }
    }

    public void despawn(GameEntityManager entityManager, String entityUUID){
        for(Map.Entry<Location, ArrayList<String>> map : spawnedEntities.entrySet()){
            if(map.getValue().contains(entityUUID)) map.getValue().remove(entityUUID);
        }
    }

    public HashMap<Location, ArrayList<String>> getSpawnedEntities() {
        return spawnedEntities;
    }

    public ArrayList<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public String getId() {
        return id;
    }

    public GameEntity getEntity() {
        return entity;
    }

    public int getMaxSpawns() {
        return maxSpawns;
    }

    public int getSpawnDelay() {
        return spawnDelay;
    }
}
