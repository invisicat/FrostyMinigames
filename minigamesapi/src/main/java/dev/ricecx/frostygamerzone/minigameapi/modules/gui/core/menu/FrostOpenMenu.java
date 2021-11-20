package dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.menu;

import dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.FrostMenu;
import org.bukkit.entity.Player;

public class FrostOpenMenu {
    private final FrostMenu gui;
    private final Player player;

    public FrostOpenMenu(FrostMenu gui, Player player) {
        this.gui = gui;
        this.player = player;
    }

    public FrostMenu getGUI() {
        return this.gui;
    }

    public Player getPlayer() {
        return this.player;
    }

}
