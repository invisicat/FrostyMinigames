package dev.ricecx.frostygamerzone.thebridge.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.ricecx.frostygamerzone.common.database.DatabaseManager;
import dev.ricecx.frostygamerzone.minigameapi.adapters.LocationAdapter;
import dev.ricecx.frostygamerzone.minigameapi.map.AbstractMapManager;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class BridgeMapManager extends AbstractMapManager<BridgeMapMeta> {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationAdapter()).registerTypeAdapter(BridgeMapMeta.class, new BridgeMapSerializer()).create();

    @Override
    public Map<String, BridgeMapMeta> loadMaps() {
        Map<String, BridgeMapMeta> maps = new HashMap<>();
        DatabaseManager.getSQLUtils().executeQuery("SELECT * FROM mapmeta WHERE game = ?", (ps) -> ps.setString(1, "thebridge"),(rs) -> {
            if(rs.next()) {
                final BridgeMapMeta map = new BridgeMapMetaImpl();

                map.setName(rs.getString("name"));
                map.setLastModified(rs.getLong("last_modified"));
                map.setVersion(rs.getInt("version"));
                map.setMapMeta(gson.fromJson(rs.getString("meta"), BridgeMap.class));
                maps.put(map.getName(), map);
            }
            return rs;
        });

        return maps;
    }
}
