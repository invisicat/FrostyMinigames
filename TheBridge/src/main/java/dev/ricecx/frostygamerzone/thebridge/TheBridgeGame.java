package dev.ricecx.frostygamerzone.thebridge;

import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamManager;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;

public class TheBridgeGame implements Game {

    @Override
    public String getPrefix() {
        return "tb-";
    }

    @Override
    public void getTeamManager(TeamManager teamManager) {
        //
    }

}
