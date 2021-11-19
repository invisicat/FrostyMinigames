package dev.ricecx.frostygamerzone.minigameapi.game;


import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.countdown.GameCountdown;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.mapvoting.MapVoter;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamManager;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

/**
 * Implemented by AbstractGame {@link AbstractGame}
 * @param <T> team
 * @param <U> user
 */
public interface Game<T extends Team<U>, U extends GameUser> {

    String getPrefix();
    String getIdentifier();
    void setIdentifier(String identifier);
    GameState getGameState();
    void setGameState(GameState newGameState);
    MapVoter getMapVoter();

    int getPlayersIngame();
    int getMaxPlayers();
    void setMaxPlayers(int maxPlayers);

    int getAllPlayersInTeams();

    GameCountdown<T, U> getGameCountdown();
    TeamManager<U, T> getTeamManager();


    void endGame();

    default void executePlayer(Consumer<Player> consumer) {
        for (Player player : getAllPlayersInGame()) {
            consumer.accept(player);
        }
    }

    default List<Player> getAllPlayersInGame() {
        return MinigamesAPI.getGameManager().getAllPlayersInGame(getIdentifier());
    }


}
