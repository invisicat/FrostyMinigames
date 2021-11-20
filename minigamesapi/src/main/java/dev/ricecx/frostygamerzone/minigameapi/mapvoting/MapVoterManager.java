package dev.ricecx.frostygamerzone.minigameapi.mapvoting;

import com.google.common.collect.Maps;
import dev.ricecx.frostygamerzone.minigameapi.Minigame;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class MapVoterManager<T extends Team<U>, U extends GameUser> implements MapVoter {

    private final Set<String> maps;

    private final Map<GameUser, String> votes = Maps.newConcurrentMap();
    private final Game<T, U> game;


    public MapVoterManager(Game<T, U> game) {
        this.game = game;

        this.maps = new HashSet<>();
    }

    @Override
    public void vote(GameUser user, String map) {
        // stuff
        if(game.getGameState() != GameState.WAITING) return;
        votes.put(user, map);

        onVote(user, map);
    }


    @Override
    public String getTopMap() {
        return getCount(votes.values().parallelStream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
    }

    private String getCount(Map<String, Long> map) {
        return Collections.max(map.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
    }

    @Override
    public Set<String> getAllVotableMaps() {
        return maps;
    }
}
