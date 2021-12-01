package dev.ricecx.frostygamerzone.minigameapi.utils;

import org.jetbrains.annotations.NotNull;

public class Reflection {

    /**
     * Determines if the specified class is loaded
     * @param className class name to check
     * @return if it's alive.
     */
    public static boolean isClassLoaded(@NotNull String className) {
        boolean isAvailable = false;
        try {
            Class.forName(className);
            isAvailable = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return isAvailable;
    }
}
