package io.github.nmahdi.JunoCore.utils;


import org.bukkit.Location;
import org.bukkit.util.Vector;


public class LocationHelper {

	public static double randomWithRange(double min, double max) {
		double range = max - min + 1.0D;
		return Math.random() * range + min;
	}

	public static Location circleAround(Location baseLocation, double radius, double angleInRadian){
		double x = baseLocation.getX() + radius * Math.sin(angleInRadian);
		double z = baseLocation.getZ() + radius * Math.cos(angleInRadian);

		Location loc = new Location(baseLocation.getWorld(), x, baseLocation.getY(), z);
		Vector difference = baseLocation.toVector().clone().subtract(loc.toVector());
		loc.setDirection(difference);

		return loc;
	}

}
