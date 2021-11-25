package dev.ricecx.frostygamerzone.minigameapi.citizens;

import com.google.common.collect.Maps;
import net.citizensnpcs.api.event.NPCClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

public class Citizens implements Listener {

    private Map<Integer, FrostNPC> npcs = Maps.newConcurrentMap();

    public Citizens() {

    }

    @EventHandler
    public void onNPCHit(NPCClickEvent evt) {
        if(npcs.get(evt.getNPC().getId()).getOnClick() != null) {
            npcs.get(evt.getNPC().getId()).getOnClick().run();
        }
    }
}
