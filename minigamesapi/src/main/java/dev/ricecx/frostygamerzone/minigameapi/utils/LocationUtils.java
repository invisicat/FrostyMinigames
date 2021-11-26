package dev.ricecx.frostygamerzone.minigameapi.utils;

import org.bukkit.Location;

public class LocationUtils {
    public static boolean areSimilar(Location loc1, Location loc2) {
        return loc1.getX() == loc2.getX() && loc1.getY() == loc2.getY() && loc1.getZ() == loc2.getZ();
    }
}
