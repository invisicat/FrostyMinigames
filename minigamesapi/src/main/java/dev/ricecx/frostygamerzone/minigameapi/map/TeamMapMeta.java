package dev.ricecx.frostygamerzone.minigameapi.map;

import dev.ricecx.frostygamerzone.minigameapi.team.TeamColor;

import java.util.Map;

public interface TeamMapMeta extends MapMeta {

    /**
     * Get the amount of teams allowed
     * on this map
     */
    int getAmountOfTeams();


    /**
     * Gets all of the teams on this map
     * mapped by the team color -> their color
     */
    Map<String, TeamColor> getTeams();

}
