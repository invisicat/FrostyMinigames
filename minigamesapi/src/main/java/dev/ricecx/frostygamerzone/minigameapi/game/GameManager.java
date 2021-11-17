package dev.ricecx.frostygamerzone.minigameapi.game;

import com.google.common.collect.Maps;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.minigameapi.lobby.core.AbstractLobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameManager {

    private final Map<String, Game<?,?>> games = Maps.newConcurrentMap();
    private final Map<UUID, String> playersInGames = Maps.newConcurrentMap();

    private AbstractLobby lobby;

    public String createGame(Game<?,?> game) {
        long existingGames = games.keySet().stream().filter((str) -> str.contains(game.getPrefix())).count();
        String prefix = game.getPrefix() + (existingGames + 1);

        games.put(prefix, game);

        return prefix;
    }

    public boolean playerBelongsToAny(UUID player) {
        String correctServer = CorePlugin.getInstance().getRedis().getSync().get(player.toString());

        return games.containsKey(correctServer);
    }

    public List<Player> getAllPlayersInGame(String gameServer) {
        List<Player> players = new ArrayList<>();
        for (Map.Entry<UUID, String> uuidStringEntry : playersInGames.entrySet()) {
            if(!uuidStringEntry.getValue().equalsIgnoreCase(gameServer)) continue;
            players.add(Bukkit.getPlayer(uuidStringEntry.getKey()));
        }

        return players;
    }

    public synchronized void addPlayerToGame(UUID uuid, String gameServer) {
        playersInGames.put(uuid, gameServer);
    }

    public AbstractLobby getLobby() {
        return lobby;
    }

    public void setLobby(AbstractLobby lobby) {
        this.lobby = lobby;
    }

    public Map<UUID, String> getPlayersInGames() {
        return playersInGames;
    }

    public Map<String, Game<?,?>> getGames() {
        return games;
    }
}
