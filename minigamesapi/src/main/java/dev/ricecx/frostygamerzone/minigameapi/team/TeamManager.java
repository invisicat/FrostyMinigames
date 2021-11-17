package dev.ricecx.frostygamerzone.minigameapi.team;

import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.Location;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeamManager<U extends GameUser, T extends Team<U>> {

    private final Map<UUID, T> registeredTeams = new ConcurrentHashMap<>();
    private Location spectatorSpawn;
    private int minPlayers, maxPlayers, teamAmount, teamSize;
}
