package dev.ricecx.frostygamerzone.minigameapi.citizens;

import lombok.Getter;
import lombok.Setter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class FrostNPC {

    NPC npc;
    @Getter
    @Setter Runnable onClick;

    FrostNPC(NPC npc) {
        this.npc = npc;
    }



    public static FrostNPC createNPC(String name) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "MassiveLag");
        npc.setProtected(true);
        npc.setName(name);
        return new FrostNPC(npc);
    }

    public void spawn(Location location) {
        npc.spawn(location);
    }

}
