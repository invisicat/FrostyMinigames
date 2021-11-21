package dev.ricecx.frostygamerzone.thebridge.map;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import org.bukkit.Location;

import java.lang.reflect.Type;
import java.util.List;

public class BridgeMapSerializer implements JsonSerializer<BridgeMapMeta>, JsonDeserializer<BridgeMapMeta> {

    @SuppressWarnings("UnstableApiUsage")
    private final Type locationType = new TypeToken<List<Location>>() {}.getType();
    @Override
    public JsonElement serialize(BridgeMapMeta src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject mapObject = new JsonObject();

        mapObject.add("spawn_spectator", context.serialize(src.getSpectatorSpawn()));
        mapObject.add("spawn_blue", context.serialize(src.getTeamBlueSpawn()));
        mapObject.add("spawn_red", context.serialize(src.getTeamRedSpawn()));
        mapObject.add("shops", context.serialize(src.getTeamRedSpawn()));
        mapObject.add("regions", context.serialize(src.getRegions()));

        return mapObject;
    }

    @Override
    public BridgeMapMeta deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject mapObject = json.getAsJsonObject();
        BridgeMapMetaImpl map = new BridgeMapMetaImpl();
        LoggingUtils.info("Deserialzing MAP");


        map.setSpectatorSpawn(context.deserialize(mapObject.get("spawn_spectator"), Location.class));
        map.setTeamBlueSpawn(context.deserialize(mapObject.get("teamBlueSpawn"), Location.class));
        map.setTeamRedSpawn(context.deserialize(mapObject.get("teamRedSpawn"), Location.class));
        map.setShops(context.deserialize(mapObject.get("shops"), locationType));
        map.setRegions(context.deserialize(mapObject.get("regions"), locationType));

        LoggingUtils.info(map.toString());
        return map;
    }
}
