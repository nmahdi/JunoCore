package io.github.nmahdi.JunoCore.generation.woodcutting;

import org.bukkit.Location;
import org.bukkit.Material;

public class RegeneratingBlock {

	private Location location;
	private Material type;
	private long timeBroken;
	private int regenTime;

	/**
	 * @param location
	 * @param type
	 * @param timeBroken
	 * @param regenTime In seconds
	 */
	public RegeneratingBlock(Location location, Material type, long timeBroken, int regenTime){
		this.location = location;
		this.type = type;
		this.timeBroken = timeBroken;
		this.regenTime = regenTime*1000;
	}

	public Location getLocation() {
		return location;
	}

	public Material getType() {
		return type;
	}

	public long getTimeBroken() {
		return timeBroken;
	}

	public int getRegenTime() {
		return regenTime;
	}
}
