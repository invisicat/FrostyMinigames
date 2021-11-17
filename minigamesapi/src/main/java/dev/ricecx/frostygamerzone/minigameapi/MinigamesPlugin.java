package dev.ricecx.frostygamerzone.minigameapi;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;

public abstract class MinigamesPlugin extends CorePlugin {


    public static MinigamesPlugin getInstance() {
        return MinigamesPlugin.getPlugin(MinigamesPlugin.class);
    }
}
