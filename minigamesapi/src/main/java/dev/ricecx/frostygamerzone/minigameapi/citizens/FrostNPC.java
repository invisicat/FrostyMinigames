package dev.ricecx.frostygamerzone.minigameapi.citizens;

import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
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
    @Setter @Getter private NPC.SkinTextures skinTextures;

    FrostNPC(NPC npc, UUID uuid) {
        this.npc = npc;
        this.uuid = uuid;

        // OffloadTask.offloadAsync(() -> NPC.SkinTextures.getByUsername("MassiveLag").thenAccept(this::setSkinTextures));
    }



    public static FrostNPC createNPC(String name, Location location) {
        UUID uuid = UUID.randomUUID();
        NPC npc = new NPC(uuid, location, name);
        return new FrostNPC(npc, uuid);
    }

    public NPC getNPC() {
        return npc;
    }

}
