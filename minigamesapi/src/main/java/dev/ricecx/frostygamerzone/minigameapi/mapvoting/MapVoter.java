package dev.ricecx.frostygamerzone.minigameapi.mapvoting;

import java.util.List;

public interface MapVoter {
    String getTopMap();

    void vote(String map);

    List<String> getAllVotableMaps();
}
