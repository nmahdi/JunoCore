package io.github.nmahdi.JunoCore.generation;

import io.github.nmahdi.JunoCore.JCore;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Random;

public abstract class ResourceGenerator {

    protected String name;
    protected ResourceType resourceType;
    protected GeneratorType generatorType;
    protected int tickDelay;
    protected World world;
    protected Random random;

    public ResourceGenerator(String name, ResourceType resourceType, GeneratorType generatorType, int tickDelay, World world, Random random){
        this.name = name;
        this.resourceType = resourceType;
        this.generatorType = generatorType;
        this.tickDelay = tickDelay;
        this.world = world;
        this.random = random;
    }

    protected Location pickRandomLocation(ArrayList<Location> locations){
        return locations.get(random.nextInt(locations.size()));
    }

    /**
     * Start method. Ran once when the Resource Manager is initialized.
     */
    public abstract void start(JCore main);

    /**
     * Tick method. Schedules an ASync Runnable that ticks every {@link #tickDelay} ticks.
     */
    public abstract void run(JCore main);

    /**
     * End method. Ran when the plugin shuts down.
     */
    public abstract void end(JCore main);

    public String getName() {
        return name;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    public int getTickDelay() {
        return tickDelay;
    }

    public World getWorld() {
        return world;
    }
}
