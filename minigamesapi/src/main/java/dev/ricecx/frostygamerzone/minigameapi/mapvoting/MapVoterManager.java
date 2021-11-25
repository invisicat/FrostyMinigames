package dev.ricecx.frostygamerzone.minigameapi.mapvoting;

import com.google.common.collect.Maps;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.Minigame;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class MapVoterManager<T extends Team<U>, U extends GameUser> implements MapVoter {

    protected Set<String> maps;

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
        if(votes.values().size() <= 0) {
            return chooseRandomMap();
        } else
            return getCount(votes.values().parallelStream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
       // return getMax();
    }

    private String chooseRandomMap() {
        // TODO: bruh wtf fix this why is this copied like 20 times
        if(maps.size() <= 0) maps.addAll(MinigamesAPI.getMapManager().getAllMaps().keySet());
        LoggingUtils.info("Map pool size: " + maps.size());
        int idx = ThreadLocalRandom.current().nextInt(maps.size());
        int i = 0;
        for (String map : maps) {
            if(i == idx)
                return map;
            i++;
        }

        return null;
    }

    /**
     * @implNote this is garbage, refactor this hack.
     * @deprecated method is garbage
     */
    private String getMax() {
        String max = null;
        Map<String, Integer> highestMaps = new HashMap<>();
        for (String value : votes.values()) {
            int prevVal = highestMaps.getOrDefault(value, 0);
            highestMaps.put(value, prevVal);
        }
        int maxValueInMap = (Collections.max(highestMaps.values()));  // This will return max value in the HashMap
        for (Map.Entry<String, Integer> entry : highestMaps.entrySet()) {  // Iterate through HashMap
            if (entry.getValue() == maxValueInMap) {
                max = entry.getKey();
                break;
            }
        }
        return max;
    }

    private String getCount(Map<String, Long> map) {
        for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {
            LoggingUtils.info(stringLongEntry.getKey() + " " + stringLongEntry.getValue());
        }

        return Collections.max(map.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
    }

    @Override
    public Set<String> getAllVotableMaps() {
        return maps;
    }
}
