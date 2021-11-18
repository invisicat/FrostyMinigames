package dev.ricecx.frostygamerzone.thebridge;

import dev.ricecx.frostygamerzone.minigameapi.game.AbstractGame;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.thebridge.countdowns.GameStartCountdown;
import dev.ricecx.frostygamerzone.thebridge.lobby.BridgeMapVoter;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeamManager;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;


@Getter
public class TheBridgeGame extends AbstractGame<BridgeUser, BridgeTeam> implements Game<BridgeTeam, BridgeUser> {

    public TheBridgeGame() {
        setTeamManager(new BridgeTeamManager(this));
        setGameCountdown(new GameStartCountdown(this));
        setMapVoter(new BridgeMapVoter(this));
        setMaxPlayers(32);
    }

    @Override
    public String getPrefix() {
        return "tb-";
    }
}
