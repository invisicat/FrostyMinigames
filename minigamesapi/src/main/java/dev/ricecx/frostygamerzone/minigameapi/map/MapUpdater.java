package dev.ricecx.frostygamerzone.minigameapi.map;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;

public class MapUpdater implements RemovalListener<String, MapMeta> {

    private final MapManager<?> mapManager;

    public MapUpdater(MapManager<?> mapManager) {
        this.mapManager = mapManager;
    }

    @Override
    public void onRemoval(RemovalNotification<String, MapMeta> notification) {
        // Fetch from the database again.
        OffloadTask.offloadAsync(mapManager::loadMaps);
        LoggingUtils.info("Maps have been removed from the cache, time to fetch them again!");
    }
}
