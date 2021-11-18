package dev.ricecx.frostygamerzone.minigameapi.game;

import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.countdown.GameCountdown;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.mapvoting.MapVoter;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamManager;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import lombok.Data;

@Data
public abstract class AbstractGame<U extends GameUser, T extends Team<U>> implements Game<T, U> {

    private Game<T, U> game;
    private GameState gameState;
    private TeamManager<U, T> teamManager;
    private GameCountdown<T, U> gameCountdown;
    private MapVoter mapVoter;
    private int maxPlayers;

    private String identifier;

    public AbstractGame() {
        gameState = GameState.WAITING;
    }

    @Override
    public abstract String getPrefix();

    @Override
    public int getPlayersIngame() {
        return MinigamesAPI.getGameManager().getAllPlayersInGame(game.getIdentifier()).size();
    }

    @Override
    public int getAllPlayersInTeams() {
        int size = 0;
        for (T team : teamManager.getRegisteredTeams().values()) {
            size += team.getPlayers().size();
        }

        return size;
    }


}
