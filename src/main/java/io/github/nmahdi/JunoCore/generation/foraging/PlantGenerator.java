package io.github.nmahdi.JunoCore.generation.foraging;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.generation.GeneratorType;
import io.github.nmahdi.JunoCore.generation.ResourceGenerator;
import io.github.nmahdi.JunoCore.generation.ResourceType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Random;

public class PlantGenerator extends ResourceGenerator {

	private ArrayList<Location> locations;

	public PlantGenerator(String name, ResourceType resourceType, World world, ArrayList<Location> locations, Random random) {
		super(name, resourceType, GeneratorType.Plant, 20, world, random);
		this.locations = locations;
	}

	@Override
	public void start(JCore main) {

	}

	@Override
	public void run(JCore main) {
		Block block = world.getBlockAt(pickRandomLocation(locations));
		if(block.getType().equals(Material.AIR) && isDirt(world.getBlockAt(block.getLocation().add(0, -1, 0)))){
			block.setType(resourceType.getMaterial());
		}
	}

	@Override
	public void end(JCore main) {

	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	private boolean isDirt(Block block){
		return block.getType() == Material.DIRT || block.getType() == Material.GRASS_BLOCK || block.getType() == Material.COARSE_DIRT;
	}

}
