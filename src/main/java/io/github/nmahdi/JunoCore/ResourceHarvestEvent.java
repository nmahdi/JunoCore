package io.github.nmahdi.JunoCore;

import io.github.nmahdi.JunoCore.generation.ResourceType;
import io.github.nmahdi.JunoCore.player.GamePlayer;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.jetbrains.annotations.NotNull;

public class ResourceHarvestEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private GamePlayer player;
	private Block block;
	private ResourceType resourceType;
	private boolean cancelled = false;

	public ResourceHarvestEvent(@NotNull GamePlayer player, @NotNull Block block, @NotNull ResourceType resourceType) {
		this.player = player;
		this.block = block;
		this.resourceType = resourceType;
	}

	/**
	 * @return GamePlayer who harvested the resource
	 */
	public GamePlayer getPlayer() {
		return player;
	}

	/**
	 * @return Block that was harvested.
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * @return ResourceType of the block that was harvested
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setCancelled(boolean cancelled){
		this.cancelled = cancelled;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public static HandlerList getHandlerList(){
		return HANDLERS;
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return HANDLERS;
	}
}
