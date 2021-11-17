package dev.ricecx.frostygamerzone.thebridge.users;

import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import org.bukkit.Location;

public class BridgeTeam extends Team<BridgeUser> {
    public BridgeTeam() {
        super(16);
    }

    @Override
    public Location getSpawn() {
        return null;
    }
}
