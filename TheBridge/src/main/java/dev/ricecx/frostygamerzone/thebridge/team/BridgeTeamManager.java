package dev.ricecx.frostygamerzone.thebridge.team;

import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamManager;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;

public class BridgeTeamManager extends TeamManager<BridgeUser, BridgeTeam> {


    public BridgeTeamManager(Game<BridgeTeam, BridgeUser> game) {
        super(game);

    }
}
