package dev.ricecx.frostygamerzone.minigameapi.game;

import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.countdown.GameCountdown;
import dev.ricecx.frostygamerzone.minigameapi.gameevents.GameEventManager;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.map.MapMeta;
import dev.ricecx.frostygamerzone.minigameapi.mapvoting.MapVoter;
import dev.ricecx.frostygamerzone.minigameapi.spectating.GameSpectator;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamManager;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import lombok.Data;
import org.bukkit.World;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

@Data
public abstract class AbstractGame<U extends GameUser, T extends Team<U>> implements Game<T, U> {

    private GameState gameState;
    private TeamManager<U, T> teamManager;
    private GameCountdown<T, U> gameCountdown;
    private MapVoter mapVoter;
    private int maxPlayers;
    private long startTime;
    private Scoreboard scoreboard;

    private String identifier;
    private MapMeta mapMeta;
    private World world;
    private String templateMap;

    private GameEventManager gameEventManager;

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
    
    public void setInternalScoreboardTeams() {
        for (T team : getTeamManager().getRegisteredTeams().values()) {
            org.bukkit.scoreboard.Team bukkitTeam = scoreboard.registerNewTeam(team.getDisplayName());
            bukkitTeam.setColor(team.getTeamColor().getChatColor());
            bukkitTeam.setDisplayName(team.getDisplayName());
            applyTeamSettings(bukkitTeam);
            LoggingUtils.debug("Created team " + team.getDisplayName() + " for game " + getIdentifier());
        }

        scoreboard.registerNewTeam("999_SPEC");

        scoreboard.registerNewObjective("hp", "health", "♥", RenderType.INTEGER);

    }

    public void broadcast(String ...message) {
        MinigamesAPI.broadcastGame(this, message);
    }
}
