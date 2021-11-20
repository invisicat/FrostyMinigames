package dev.ricecx.frostygamerzone.minigameapi.map;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.ricecx.frostygamerzone.common.LoggingUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractMapManager<T extends MapMeta> implements MapManager<T> {
    private final Cache<String, MapMeta> mapsCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).removalListener(new MapUpdater(this)).build();


    @Override
    public void loadMapsIntoCache() {
        mapsCache.putAll(loadMaps());
        LoggingUtils.info("Maps have been put into the cache!");
    }

    @Override
    public Map<String, MapMeta> getAllMaps() {
        return mapsCache.asMap();
    }

    @Override
    public MapMeta getMapMeta(String name) {
        return mapsCache.getIfPresent(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends MapMeta> T getMap(String name) {
        return ((T) mapsCache.getIfPresent(name));
    }
}
