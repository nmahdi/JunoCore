package io.github.nmahdi.JunoCore.effects;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public interface ParticleHelper {

	default void drawBeam(Particle particle, Location location, Vector direction, double range, double offSet){
		for(double i = 0.1; i < range; i += offSet){
			direction.multiply(i);
			location.add(direction);
			location.getWorld().spawnParticle(particle, location, 1);
			location.subtract(direction);
			direction.normalize();
		}
	}

	default void drawCircle(int radius, Location location, double yLevel){
		double angle = 0f;
		while(angle < Math.PI*2) {
			double x = (radius * Math.sin(angle));
			double z = (radius * Math.cos(angle));
			angle+=0.1;
			location.getWorld().spawnParticle(Particle.DRIP_LAVA,  new Location(location.getWorld(), location.getX() + x, yLevel, location.getZ() + z), 1);
		}
	}

}
