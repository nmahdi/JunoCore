package io.github.nmahdi.JunoCore.player.listeners.resource;

import io.github.nmahdi.JunoCore.JCore;
import io.github.nmahdi.JunoCore.effects.PacketManager;
import io.github.nmahdi.JunoCore.generation.ResourceManager;
import io.github.nmahdi.JunoCore.generation.ResourceType;
import io.github.nmahdi.JunoCore.generation.BlockBreakHelper;
import io.github.nmahdi.JunoCore.item.ability.item.BlockBreakItemAbility;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;

public class BreakingBlock implements BlockBreakHelper {

	private JCore main;
	private GamePlayer player;
	private Location location;
	private ResourceType resourceType;
	private int breakingTime;
	private int ticks = 0;

	public BreakingBlock(JCore main, GamePlayer player, Location location, ResourceType resourceType){
		this.main = main;
		this.player = player;
		this.location = location;
		this.resourceType = resourceType;
		this.breakingTime = resourceType.getBreakingTime();
	}

	public GamePlayer getPlayer() {
		return player;
	}

	public Location getLocation() {
		return location;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType){
		this.resourceType = resourceType;
	}

	public void minusBreakingTime(double harvestingSpeed){
		if(breakingTime-harvestingSpeed > 0){
			breakingTime-=harvestingSpeed;
			return;
		}
		breakingTime=0;
	}

	public void resetBreakingTime(){
		this.breakingTime = resourceType.getBreakingTime();
	}

	public int getBreakingTime() {
		return breakingTime;
	}

	public BreakingBlock tick(PacketManager packetManager, ResourceManager resourceManager){

		ResourceType currentBlock = null;
		if(location.getBlock().hasMetadata("resource")) {
			currentBlock = ResourceType.getType(location.getBlock().getMetadata("resource").get(0).asString());
		}else{
			currentBlock = ResourceType.getType(location.getBlock());
		}

			if (currentBlock != null) {
				if (breakingTime > 0) {

					if (resourceType != currentBlock) {

						setResourceType(currentBlock);
						resetBreakingTime();

					}

					try {
						int percentage = (int) ((((float) resourceType.getBreakingTime() - (float) breakingTime) / (float) resourceType.getBreakingTime()) * 10);

						packetManager.showBlockBreak(player.getPlayerObject(), location.getBlock(), percentage);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e);
					}
					if (ticks == 10) {

						ticks = 0;
					}

					minusBreakingTime(player.getStats().getHarvestingSpeed());

				} else {
					setResourceType(currentBlock);
					resetBreakingTime();
					BlockBreakItemAbility ability = null;
					if (player.getHeldItem() != null && player.getHeldItem().getItemAbility() != null) {
						if (player.getHeldItem().getItemAbility() instanceof BlockBreakItemAbility)
							ability = (BlockBreakItemAbility) player.getHeldItem().getItemAbility();
					}
					breakBlock(main, player, resourceType, getLocation().getBlock(), ability);

					try {
						packetManager.hideBlockBreak(player.getPlayerObject(), location.getBlock());
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				}
			}
			ticks++;
		return this;
	}

}
