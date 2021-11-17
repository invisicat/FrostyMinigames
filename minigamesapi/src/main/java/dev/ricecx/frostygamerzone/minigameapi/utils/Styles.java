package dev.ricecx.frostygamerzone.minigameapi.utils;

import net.md_5.bungee.api.ChatColor;

public class Styles {

    public static String color(String ...message) {
        StringBuilder builder = new StringBuilder();

        for (String s : message) {
            builder.append(ChatColor.translateAlternateColorCodes('&', s));
        }
        return builder.toString();
    }
}
