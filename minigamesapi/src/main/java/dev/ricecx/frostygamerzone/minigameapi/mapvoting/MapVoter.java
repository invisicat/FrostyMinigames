package dev.ricecx.frostygamerzone.minigameapi.mapvoting;

import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;

import java.util.List;

public interface MapVoter {
    String getTopMap();

    void vote(GameUser user, String map);

    void onVote(GameUser user, String map);

    List<String> getAllVotableMaps();
}
