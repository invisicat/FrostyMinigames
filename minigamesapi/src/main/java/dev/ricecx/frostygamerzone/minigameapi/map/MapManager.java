package dev.ricecx.frostygamerzone.minigameapi.map;

import java.util.Map;

public interface MapManager<T extends MapMeta> {
    Map<String, MapMeta> getAllMaps();

    Map<String, T> loadMaps();

    MapMeta getMapMeta(String name);

    <T extends MapMeta> T getMap(String name);


    void loadMapsIntoCache();
}
