package dev.ricecx.frostygamerzone.minigameapi.game;

import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameManagerListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent evt) {
        MinigamesAPI.getGameManager().removePlayer(evt.getPlayer());
    }
}
