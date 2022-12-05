package io.github.nmahdi.JunoCore.generation.woodcutting;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.dependencies.WorldEditManager;
import io.github.nmahdi.JunoCore.generation.GeneratorType;
import io.github.nmahdi.JunoCore.generation.ResourceGenerator;
import io.github.nmahdi.JunoCore.generation.ResourceType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.Random;

public class TreeGenerator extends ResourceGenerator implements Listener {

	private ArrayList<RegeneratingBlock> blocks = new ArrayList<>();
	private ProtectedRegion region;

	public TreeGenerator(JCore main, String name, ResourceType resourceType, World world, Random random){
		super(name, resourceType, GeneratorType.Tree, 20, world, random);
		main.getServer().getPluginManager().registerEvents(this, main);
		region = main.getRegionManager().getRegion(name);
	}

	@Override
	public void start(JCore main) {

	}

	@Override
	public void run(JCore main) {
		if(!blocks.isEmpty()){
			for(RegeneratingBlock block : blocks){
				if(System.currentTimeMillis() >= block.getTimeBroken()+block.getRegenTime()){
					world.getBlockAt(block.getLocation()).setType(block.getType());
				}
			}
		}
	}

	@Override
	public void end(JCore main) {
		for(RegeneratingBlock block : blocks){
			world.getBlockAt(block.getLocation()).setType(block.getType());
		}
		blocks.clear();
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e){
		if(e.getPlayer().hasPermission("juno.generators.override")) return;
		Location location = e.getBlock().getLocation();
		if(region.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())){
			blocks.add(new RegeneratingBlock(location, e.getBlock().getType(), System.currentTimeMillis(), 30));
		}
	}


}
