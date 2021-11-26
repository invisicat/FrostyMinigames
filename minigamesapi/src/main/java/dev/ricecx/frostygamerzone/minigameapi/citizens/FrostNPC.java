package dev.ricecx.frostygamerzone.minigameapi.citizens;

import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Consumer;

public class FrostNPC {

    NPC npc;
    public UUID uuid;
    @Getter
    @Setter
    Consumer<Player> onClick;

    FrostNPC(NPC npc, UUID uuid) {
        this.npc = npc;
        this.uuid = uuid;
        NPC.SkinTextures.getByUsername(MinigamesAPI.getMinigamesPlugin(), "MassiveLag", (s, sk) -> {
            if(s) {
                npc.setSkin(sk);
            }
        });
    }



    public static FrostNPC createNPC(String name, Location location) {
        UUID uuid = UUID.randomUUID();
        NPC npc = new NPC(uuid, location, name);
        return new FrostNPC(npc, uuid);
    }

    public void spawn(Location location) {
    }

    public NPC getNPC() {
        return npc;
    }

}
