package io.github.nmahdi.JunoCore.entity.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface DoesKnockBack {

	default void knockBack(Entity target, Location source, double strength){
		target.setVelocity(source.getDirection().multiply(strength).setY(strength/5));
	}

}
