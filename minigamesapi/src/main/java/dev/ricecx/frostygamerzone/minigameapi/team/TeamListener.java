package dev.ricecx.frostygamerzone.minigameapi.team;

import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class TeamListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent evt) {
        for (Game<?, ?> game : MinigamesAPI.getGameManager().getGames().values()) {
            if(game.getTeamManager() != null) {
                for (Team<? extends GameUser> team : game.getTeamManager().getRegisteredTeams().values()) {
                    team.removePlayer(evt.getPlayer());
                }
            }
        }
    }
}
