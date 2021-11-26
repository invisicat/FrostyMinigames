package dev.ricecx.frostygamerzone.minigameapi.map;

import java.util.Map;

public interface MapManager<T extends MapMeta> {
    Map<String, MapMeta> getAllMaps();

    Map<String, T> loadMaps();

    void saveMap(T mapMeta);

    @SuppressWarnings("unchecked")
    default void saveMap(Object meta) {
        saveMap((T) meta);
    }

    MapMeta getMapMeta(String name);

    <V extends MapMeta> V getMap(String name);


    void loadMapsIntoCache();
}
