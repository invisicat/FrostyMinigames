package dev.ricecx.frostygamerzone.thebridge.map;

import dev.ricecx.frostygamerzone.minigameapi.map.TeamMapMeta;

public interface BridgeMapMeta extends TeamMapMeta {

    BridgeMap getMapMeta();
    void setMapMeta(BridgeMap map);
}
