package dev.ricecx.frostygamerzone.minigameapi.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;

public class Styles {

    public static String color(String ...message) {
        StringBuilder builder = new StringBuilder();

        for (String s : message) {
            builder.append(ChatColor.translateAlternateColorCodes('&', s));
        }
        return builder.toString();
    }

    public static String location(Location location) {
        return location.getWorld() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
    }
}
