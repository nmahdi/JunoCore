package io.github.nmahdi.JunoCore.utils;

public class LocationHelper {

	public static double randomWithRange(double min, double max) {
		double range = max - min + 1.0D;
		return Math.random() * range + min;
	}

}
