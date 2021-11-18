package dev.ricecx.frostygamerzone.minigameapi.team;

import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import lombok.Getter;
import org.bukkit.Location;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeamManager<U extends GameUser, T extends Team<U>> {

    @Getter private final Map<UUID, T> registeredTeams = new ConcurrentHashMap<>();
    private final Game<T, U> game;
    private Location spectatorSpawn;
    private int minPlayers, maxPlayers, teamAmount, teamSize;

    public TeamManager(Game<T, U> game) {
        this.game = game;
    }

}
