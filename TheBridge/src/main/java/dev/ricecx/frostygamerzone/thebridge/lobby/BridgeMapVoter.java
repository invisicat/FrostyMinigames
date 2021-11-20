package dev.ricecx.frostygamerzone.thebridge.lobby;

import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.mapvoting.MapVoterManager;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;

public class BridgeMapVoter extends MapVoterManager<BridgeTeam, BridgeUser> {

    public BridgeMapVoter(Game<BridgeTeam, BridgeUser> game) {
        super(game);
    }

    @Override
    public void onVote(GameUser user, String map) {
        user.getPlayer().sendMessage("You have voted for map " + map);
    }
}
