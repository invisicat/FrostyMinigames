package dev.ricecx.frostygamerzone.thebridge.team;

import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import org.bukkit.Location;

public class BridgeTeam extends Team<BridgeUser> {

    public BridgeTeam(int size) {
        super(size);
    }

    @Override
    public Location getSpawn() {
        return null;
    }
}
