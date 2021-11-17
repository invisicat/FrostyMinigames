package dev.ricecx.frostygamerzone.minigameapi.mapvoting;

import java.util.ArrayList;
import java.util.List;

public abstract class MapVoterManager implements MapVoter {

    private final static List<String> maps = new ArrayList<>();

    static {
        maps.add("Space");
        maps.add("Cliffs");
        maps.add("Pirates");
        maps.add("Nigger");
    }


    @Override
    public void vote(String map) {
        // stuff
    }


    @Override
    public String getTopMap() {
        return "tb-space";
    }

    @Override
    public List<String> getAllVotableMaps() {
        return maps;
    }
}
