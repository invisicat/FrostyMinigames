package dev.ricecx.frostygamerzone.minigameapi.team;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

import java.util.Arrays;
import java.util.List;

@Getter
public enum TeamColor {

    NONE(-1, "", Color.GRAY, ChatColor.GRAY, DyeColor.GRAY, 7),

    WHITE(1, "White", Color.WHITE, ChatColor.WHITE, DyeColor.WHITE, 0),
    YELLOW(2, "Yellow", Color.YELLOW, ChatColor.YELLOW, DyeColor.YELLOW, 4),
    BLUE(3, "Blue", Color.BLUE, ChatColor.BLUE, DyeColor.BLUE, 11),
    AQUA(4, "Aqua", Color.AQUA, ChatColor.AQUA, DyeColor.BLACK, 3),
    GREEN(5, "Green", Color.LIME, ChatColor.GREEN, DyeColor.GREEN, 13),
    PINK(6, "Pink", Color.FUCHSIA, ChatColor.LIGHT_PURPLE, DyeColor.PINK, 6),
    RED(7, "Red", Color.RED, ChatColor.RED, DyeColor.RED, 14),
    GRAY(8, "Gray", Color.GRAY, ChatColor.GRAY, DyeColor.GRAY, 7),
    ORANGE(9, "Orange", Color.ORANGE, ChatColor.GOLD, DyeColor.ORANGE, 1);

    public static final TeamColor[] CACHE = values();
    private final int id;
    private final String name;
    private final Color color;
    private final ChatColor chatColor;
    private final DyeColor dyeColor;
    private final byte data;

    private String shortName;

    TeamColor(int id, String name, Color color, ChatColor chatColor, DyeColor dyeColor, int data) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.chatColor = chatColor;
        this.dyeColor = dyeColor;
        this.data = (byte) data;

        if (!name.isEmpty()) {
            this.shortName = name.substring(0, 1).toUpperCase();
        }
    }

    //Non modifyable copy of the values
    public static List<TeamColor> getTeamColors() {
        return Arrays.asList(CACHE);
    }

    public static TeamColor getTeamColorByName(String name) {
        for (TeamColor color : CACHE) {
            if (color.getName().equalsIgnoreCase(name)) {
                return color;
            }
        }
        throw new IllegalArgumentException("TeamColor with the name " + name + " was not found");
    }

    public static TeamColor getByDataValue(byte data) {
        for (TeamColor color : CACHE) {
            if (color.getData() == data) {
                return color;
            }
        }

        // Return null because its used for wool drops
        return null;
    }

    public static TeamColor getTeamColorById(int id) {
        for (TeamColor color : CACHE) {
            if (color.getId() == id) {
                return color;
            }
        }
        throw new IllegalArgumentException("TeamColor with the ID " + id + " was not found");
    }

}

