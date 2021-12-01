package dev.ricecx.frostygamerzone.thebridge.map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.common.database.DatabaseManager;
import dev.ricecx.frostygamerzone.minigameapi.adapters.LocationAdapter;
import dev.ricecx.frostygamerzone.minigameapi.map.AbstractMapManager;
import dev.ricecx.frostygamerzone.minigameapi.map.MapMeta;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class BridgeMapManager extends AbstractMapManager<BridgeMapMeta> {

    private final Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationAdapter()).registerTypeAdapter(BridgeMapMeta.class, new BridgeMapSerializer()).create();

    @Override
    public Map<String, BridgeMapMeta> loadMaps() {
        Map<String, BridgeMapMeta> maps = new HashMap<>();
        DatabaseManager.getSQLUtils().executeQuery("SELECT * FROM mapmeta WHERE game = ?", (ps) -> ps.setString(1, "thebridge"),(rs) -> {
            while(rs.next()) {
                if(rs.getString("meta") == null) continue;

                BridgeMapMetaImpl map = gson.fromJson(rs.getString("meta"), BridgeMapMetaImpl.class);

                map.setName(rs.getString("name"));
                map.setLastModified(rs.getTimestamp("last_modified").getTime());
                map.setVersion(rs.getInt("version"));

                LoggingUtils.info("Found map meta for " + map.getName());
                maps.put(map.getName(), map);
            }
            return rs;
        });

        return maps;
    }

    @Override
    public void saveMap(BridgeMapMeta mapMeta) {
        LoggingUtils.info("Saving map data " + mapMeta.toString());
        DatabaseManager.getSQLUtils().executeUpdate("UPDATE mapmeta SET meta = ? WHERE name = ?", (ps) -> {
            ps.setString(1, gson.toJson(mapMeta));
            ps.setString(2, mapMeta.getName());
        });
    }
}
