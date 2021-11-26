package dev.ricecx.frostygamerzone.minigameapi.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Styles {

    public static String color(String ...message) {
        StringBuilder builder = new StringBuilder();

        for (String s : message) {
            builder.append(ChatColor.translateAlternateColorCodes('&', s));
        }
        return builder.toString();
    }

    public static String location(Location location) {
        return location.getWorld() + ":" + color("&a" + round(location.getX(), 1)) + ":" + color("&c" + round(location.getY(), 1)) + ":" + color("&d" + round(location.getZ(), 1)) + ":" + round(location.getYaw(), 2) + ":" + round(location.getPitch(), 3);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
