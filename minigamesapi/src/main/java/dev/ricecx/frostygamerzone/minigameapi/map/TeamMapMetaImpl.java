package dev.ricecx.frostygamerzone.minigameapi.map;

import dev.ricecx.frostygamerzone.minigameapi.team.TeamColor;
import lombok.Data;
import lombok.Setter;

import java.util.Map;


@Data
public class TeamMapMetaImpl implements TeamMapMeta {

    private int version;
    private long lastModified;
    private String name;
    private String worldTemplateName;
    private int amountOfTeams;
    private Map<String, TeamColor> teams;

}
