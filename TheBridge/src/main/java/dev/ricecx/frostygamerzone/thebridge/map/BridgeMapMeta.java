package dev.ricecx.frostygamerzone.thebridge.map;

import dev.ricecx.frostygamerzone.minigameapi.map.TeamMapMeta;
import dev.ricecx.frostygamerzone.minigameapi.regions.Region;
import org.bukkit.Location;

import java.util.List;

public interface BridgeMapMeta extends TeamMapMeta {

    Location getSpectatorSpawn();

    Location getTeamBlueSpawn();
    Location getTeamRedSpawn();

    List<Location> getShops();

    List<Location> getNexusLocations();

    void setRegions(List<Region> regions);
    List<Region> getRegions();

    void setSpectatorSpawn(Location location);

    void setTeamBlueSpawn(Location location);
    void setTeamRedSpawn(Location location);

    void setShops(List<Location> locations);

    void setNexusLocations(List<Location> locations);

}
