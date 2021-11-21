package dev.ricecx.frostygamerzone.minigameapi.game;

import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.countdown.GameCountdown;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.map.MapMeta;
import dev.ricecx.frostygamerzone.minigameapi.mapvoting.MapVoter;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamManager;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import lombok.Data;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class AbstractGame<U extends GameUser, T extends Team<U>> implements Game<T, U> {

    private GameState gameState;
    private TeamManager<U, T> teamManager;
    private GameCountdown<T, U> gameCountdown;
    private MapVoter mapVoter;
    private int maxPlayers;

    private String identifier;
    private MapMeta mapMeta;
    private World world;

    public AbstractGame() {
        gameState = GameState.WAITING;
    }

    @Override
    public abstract String getPrefix();

    @Override
    public int getPlayersIngame() {
        return MinigamesAPI.getGameManager().getAllPlayersInGame(getIdentifier()).size();
    }


    /**
     * @deprecated please fix this
     * @return players
     */
    @Override
    public List<U> getPlayers() {
        return null;
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
