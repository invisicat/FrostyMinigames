package dev.ricecx.frostygamerzone.thebridge.users;

import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;

public interface BridgeUser extends GameUser {
    Team<BridgeUser> getTeam();
}
