package io.github.nmahdi.JunoCore.entity;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.entity.spawnzone.SpawnZone;
import io.github.nmahdi.JunoCore.entity.spawnzone.SpawnZoneManager;
import io.github.nmahdi.JunoCore.entity.temp.UndeadMonstrosityTargetStrategy;
import io.github.nmahdi.JunoCore.entity.traits.StatsTrait;
import io.github.nmahdi.JunoCore.utils.JLogger;
import io.github.nmahdi.JunoCore.utils.JunoManager;
import io.github.nmahdi.JunoCore.utils.LocationHelper;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.MemoryNPCDataStore;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

public class GameEntityManager implements JunoManager, Listener {

    private boolean debugMode;
    private JCore main;

    private ArrayList<NPCRegistry> registries = new ArrayList<>();
    public int counter = 0;
    private final NPCRegistry mainRegistry = CitizensAPI.createAnonymousNPCRegistry(new MemoryNPCDataStore());

    private SpawnZoneManager spawnZoneManager;

    public GameEntityManager(JCore main){
        this.debugMode = main.getConfig().getBoolean("debug-mode.entity");
        this.main = main;
        this.spawnZoneManager = new SpawnZoneManager(main, this);
        main.getServer().getPluginManager().registerEvents(this, main);
        JLogger.log("Entity Manager has been enabled.");
    }

    /**
     *
     * Spawns an entity based on the JEntity enum template with the specified level at the specified location
     *
     * @param entity GameEntity template
     * @param location Location in the world
     */
    public NPC spawnEntity(GameEntity entity, Location location){
        return spawnEntityToRegistry(mainRegistry, entity, location);
    }

    public NPC spawnEntityToRegistry(NPCRegistry registry, GameEntity entity, Location location){
        if(!registries.contains(registry)) registries.add(registry);
        NPC npc = registry.createNPC(entity.getEntityType(), "entity " + counter);
        counter++;
        StatsTrait statsTrait = new StatsTrait();
        statsTrait.init(entity);
        npc.addTrait(statsTrait);

        for(Class<? extends GameGoal> goal : entity.getGoals()){

            try {
                GameGoal gamegoal = goal.getDeclaredConstructor(JCore.class, NPC.class).newInstance(main, npc);
                npc.getDefaultGoalController().addBehavior(gamegoal, 1);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        npc.setProtected(false);
        npc.spawn(location.add(LocationHelper.randomWithRange(-1, 1), 0, LocationHelper.randomWithRange(-1, 1)));
        return npc;
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

    public NPCRegistry getMainRegistry() {
        return mainRegistry;
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
        for(NPCRegistry registry : registries){
            for(NPC npc : registry){
                npc.despawn();
            }
        }
    }

}
