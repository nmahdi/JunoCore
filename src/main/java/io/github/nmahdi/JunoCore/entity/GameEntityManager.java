package io.github.nmahdi.JunoCore.entity;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.spawnzone.SpawnZone;
import io.github.nmahdi.JunoCore.entity.spawnzone.SpawnZoneManager;
import io.github.nmahdi.JunoCore.entity.traits.GameTrait;
import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import io.github.nmahdi.JunoCore.utils.LocationHelper;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Map;

public class GameEntityManager implements JunoManager, Listener {

    private boolean debugMode;

    public int counter = 0;

    private final NPCRegistry registry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

    private SpawnZoneManager spawnZoneManager;

    public GameEntityManager(JCore main){
        main.getServer().getPluginManager().registerEvents(this, main);
        this.debugMode = main.getConfig().getBoolean("debug-mode.entity");
        this.spawnZoneManager = new SpawnZoneManager(main, this);
        JLogger.log("Entity Manager has been enabled.");
    }

    /**
     *
     * Spawns an entity based on the JEntity enum template with the specified level at the specified location
     *
     * @param entity GameEntity template
     * @param location Location in the world
     */
    public String spawnEntity(GameEntity entity, Location location){
        NPC npc = registry.createNPC(entity.getEntityType(), "entity " + counter);
        counter++;

        for(Class<? extends GameTrait> trait : entity.getTraits()){

            try {
                GameTrait gameTrait = trait.newInstance();
                gameTrait.init(entity);
                npc.addTrait(gameTrait);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        npc.setProtected(false);
        npc.spawn(location.add(LocationHelper.randomWithRange(-1, 1), 0, LocationHelper.randomWithRange(-1, 1)));
        return npc.getUniqueId().toString();
    }

    @EventHandler
    public void onGameEntityDeath(NPCDeathEvent e){
        for(SpawnZone spawnZone : spawnZoneManager.getSpawnZones()){
            for(Map.Entry<Location, ArrayList<String>> spawned : spawnZone.getSpawnedEntities().entrySet()){
                if(spawned.getValue().contains(e.getNPC().getUniqueId().toString()))
                    spawnZone.despawn(this, e.getNPC().getUniqueId().toString());
            }
        }
    }

    public void postInit(){
        spawnZoneManager.tick();
    }

    public NPCRegistry getRegistry() {
        return registry;
    }

    public SpawnZoneManager getSpawnZoneManager() {
        return spawnZoneManager;
    }

    @Override
    public boolean isDebugging() {
        return debugMode;
    }

    @Override
    public void debug(String s) {
        JLogger.debug(this, s);
    }

    @Override
    public void setDebugMode(boolean mode) {
        debugMode = mode;
    }

    @Override
    public void onDisable() {
        for(NPC npc : registry){
            npc.despawn();
        }
    }

}
