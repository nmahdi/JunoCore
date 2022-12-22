package io.github.nmahdi.JunoCore.generation.mining;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.ResourceHarvestEvent;
import io.github.nmahdi.JunoCore.generation.GeneratorType;
import io.github.nmahdi.JunoCore.generation.ResourceGenerator;
import io.github.nmahdi.JunoCore.generation.ResourceType;
import io.github.nmahdi.JunoCore.utils.JLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Random;

public class OreGenerator extends ResourceGenerator implements Listener {

    private JCore main;
    private ResourceType fillerType;
    private ArrayList<Location> locations;

    public OreGenerator(JCore main, String name, ResourceType oreType, ResourceType fillerType, World world, ArrayList<Location> locations, Random random){
        super(name, oreType, GeneratorType.Ore, 20, world, random);
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
        this.fillerType = fillerType;
        this.locations = locations;
    }

    @Override
    public void start(JCore main) {
        for(Location location : locations){
            if(main.getPrimaryWorld().getBlockAt(location).getType() != fillerType.getMaterial()){
                main.getPrimaryWorld().getBlockAt(location).setType(fillerType.getMaterial());
            }
        }
    }

    @Override
    public void run(JCore main) {
        Block block = world.getBlockAt(pickRandomLocation(locations));
        if (block.getType() != resourceType.getMaterial() && block.getType() != Material.BEDROCK) {
            block.setType(resourceType.getMaterial());
            if(resourceType.usesTag()){
                block.setMetadata("resource", new FixedMetadataValue(main, resourceType.getId()));
            }
        }
    }

    @Override
    public void end(JCore main) {

    }

    public ResourceType getFillerType() {
        return fillerType;
    }

    @EventHandler
    public void onHarvest(ResourceHarvestEvent e){

    }

}
