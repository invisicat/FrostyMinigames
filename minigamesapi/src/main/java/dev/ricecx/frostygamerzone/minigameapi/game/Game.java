package dev.ricecx.frostygamerzone.minigameapi.game;


import dev.ricecx.frostygamerzone.bukkitapi.LazyLocation;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.countdown.GameCountdown;
import dev.ricecx.frostygamerzone.minigameapi.gameevents.GameEventManager;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.map.MapMeta;
import dev.ricecx.frostygamerzone.minigameapi.mapvoting.MapVoter;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamManager;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

/**
 * Implemented by AbstractGame {@link AbstractGame}
 * @param <T> team
 * @param <U> user
 */
public interface Game<T extends Team<U>, U extends GameUser> extends Minigame {


    long getStartTime();
    void setStartTime(long time);

    String getPrefix();

    GameState getGameState();
    void setGameState(GameState newGameState);

    MapVoter getMapVoter();

    String getTemplateMap();
    void setTemplateMap(String map);

    World getWorld();
    void setWorld(World world);

    MapMeta getMapMeta();
    void setMapMeta(MapMeta meta);

    int getPlayersIngame();

    int getMaxPlayers();
    void setMaxPlayers(int maxPlayers);

    List<U> getPlayers();
    int getAllPlayersInTeams();

    GameCountdown<T, U> getGameCountdown();
    TeamManager<U, T> getTeamManager();

    GameEventManager getGameEventManager();
    void setGameEventManager(GameEventManager eventManager);


    /**
     * This method allows games to map configs to itself
     * as some games can have different maps and different coords and such.
     * @param meta map meta
     */
    void applyMapMeta(MapMeta meta);

    void onStartGame();
    void onPlayerStartGame(U player);
    void endGame();


    @SuppressWarnings("unchecked")
    default <b extends GameUser> b getPlayer(GameUser user) {
        return (b) user;
    }


    default Location getLocation(Location location) {
        location.setWorld(getWorld());

        return location;
    }

    default void teleport(Location location, GameUser ...players) {
        for (GameUser player : players) {
            teleport(location, player.getPlayer());
        }
    }

    /**
     * Teleport players within the game world
     * @param location the location to teleport to
     * @param player the player(s) to teleport
     */
    default void teleport(LazyLocation location, Player ...player) {
        for (Player player1 : player) {
            Location loc = location.getLocation();
            loc.setWorld(getWorld());
            player1.teleport(loc);
        }
    }

    default void teleport(Location location, Player ...player) {
        for (Player player1 : player) {
            location.setWorld(getWorld());

            player1.teleport(location);
        }
    }

    default void executePlayer(Consumer<Player> consumer) {
        for (Player player : getAllPlayersInGame()) {
            consumer.accept(player);
        }
    }

    default List<Player> getAllPlayersInGame() {
        return MinigamesAPI.getGameManager().getAllPlayersInGame(getIdentifier());
    }


}
