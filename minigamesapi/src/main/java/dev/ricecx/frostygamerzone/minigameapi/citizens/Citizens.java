package dev.ricecx.frostygamerzone.minigameapi.citizens;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
    public void onNPCHit(EntityDamageByEntityEvent evt) {
        if(npcs.containsKey(evt.getEntity().getUniqueId())) {
            npcs.get(evt.getEntity().getUniqueId()).getOnClick().accept(((Player) evt.getDamager()));
        }
    }
}
