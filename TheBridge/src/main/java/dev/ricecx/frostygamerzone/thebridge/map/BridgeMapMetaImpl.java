package dev.ricecx.frostygamerzone.thebridge.map;

import dev.ricecx.frostygamerzone.minigameapi.buildtools.annotations.LookAt;
import dev.ricecx.frostygamerzone.minigameapi.map.TeamMapMetaImpl;
import dev.ricecx.frostygamerzone.minigameapi.regions.Region;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.List;

@Getter
@Setter
public class BridgeMapMetaImpl extends TeamMapMetaImpl implements BridgeMapMeta {

    private Location spectatorSpawn;

    private Location teamBlueSpawn;
    private Location teamRedSpawn;

    private List<Location> shops;

    @LookAt
    private Location teamRedNexus;

    @LookAt
    private Location teamBlueNexus;

    private List<Region<?>> regions;


    @Override
    public String toString() {
        return "BridgeMapMetaImpl{" +
                "spectatorSpawn=" + spectatorSpawn +
                ", teamBlueSpawn=" + teamBlueSpawn +
                ", teamRedSpawn=" + teamRedSpawn +
                ", shops=" + shops +
                ", regions=" + regions +
                '}';
    }

}
