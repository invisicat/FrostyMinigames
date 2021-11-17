package dev.ricecx.frostygamerzone.minigameapi.game;


import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamManager;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.entity.Player;

import java.util.List;

public interface Game<T extends Team<U>, U extends GameUser> {

    String getPrefix();

    void getTeamManager(TeamManager<U, T> teamManager);


    default List<Player> getAllPlayers() {
        return MinigamesAPI.getGameManager().getAllPlayersInGame(getPrefix());
    }
}
