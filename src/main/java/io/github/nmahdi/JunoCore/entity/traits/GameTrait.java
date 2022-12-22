package io.github.nmahdi.JunoCore.entity.traits;

import io.github.nmahdi.JunoCore.entity.GameEntity;
import io.github.nmahdi.JunoCore.entity.GameEntityContainer;
import net.citizensnpcs.api.ai.EntityTarget;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class GameTrait extends Trait{

	protected GameTrait(String name) {
		super(name);
	}

	public void setTarget(Player target, boolean hostile){
		getNPC().getNavigator().setTarget(target, hostile);
	}

	public abstract void init(GameEntity entity);

	public Creature getEntity(){
		return (Creature) getNPC().getEntity();
	}

	public Location getLocation(){
		return getNPC().getStoredLocation();
	}

	public Location getEyeLocation(){
		return getEntity().getEyeLocation();
	}

	public World getWorld(){
		return getNPC().getEntity().getWorld();
	}

}
