package dev.ricecx.frostygamerzone.thebridge.map;

import dev.ricecx.frostygamerzone.minigameapi.map.TeamMapMetaImpl;
import lombok.Data;

@Data
public class BridgeMapMetaImpl extends TeamMapMetaImpl implements BridgeMapMeta {

    private BridgeMap mapMeta;
}
