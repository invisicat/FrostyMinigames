package dev.ricecx.frostygamerzone.thebridge.map;

import dev.ricecx.frostygamerzone.minigameapi.regions.Region;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.List;

@Getter
@Setter
public class BridgeMap {

    private Location spectatorSpawn;

    private Location teamBlueSpawn;
    private Location teamRedSpawn;

    private List<Location> shops;

    private List<Location> nexusLocations;

    private List<Region> regions;

}
