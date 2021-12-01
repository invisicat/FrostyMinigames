package dev.ricecx.frostygamerzone.minigameapi.citizens;

import com.google.common.collect.Maps;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Map;
import java.util.UUID;

public class Citizens implements Listener {

    private int ids = 0;
    private final Map<UUID, FrostNPC> npcs = Maps.newConcurrentMap();

    public Citizens() {

    }

    public int addNPC(FrostNPC npc) {

        npcs.put(npc.uuid, npc);
        return ids;
    }

    public FrostNPC getNPC(UUID id) {
        return npcs.get(id);
    }

    @EventHandler
    public void onNPCHit(PlayerInteractEntityEvent evt) {
        LoggingUtils.info("INTERASCTED WITH " + evt.getRightClicked().getType() + " WITH UUID " + evt.getRightClicked().getUniqueId());

        for (UUID uuid : npcs.keySet()) {
            LoggingUtils.info("----- uuid " + uuid);
        }
        if(npcs.containsKey(evt.getRightClicked().getUniqueId())) {
            npcs.get(evt.getRightClicked().getUniqueId()).getOnClick().accept(evt.getPlayer());
        }
    }
}
