package dev.ricecx.frostygamerzone.minigameapi.citizens;

import dev.ricecx.frostygamerzone.common.LoggingUtils;
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
        this.skinTextures = new NPC.SkinTextures(
                "ewogICJ0aW1lc3RhbXAiIDogMTU5MzQ3MTgyMzAyOCwKICAicHJvZmlsZUlkIiA6ICIwMWI2MWI1NWM2YWI0N2IxODI2M2JiNjgxNWMzNzY3NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzYWdhbWVycHJvIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2Q0NjA1MjY2OGZkNjlkZTNlMjZhZDE1YWJlOTZmNzhjMjhjMjc1ZTAyYTM0ZjA0NjliZTczODgxNzRmNGQ5MDciCiAgICB9CiAgfQp9",
                "HibMpXS/6LLoaK2+btqc7FO5TldXnkt/oSwWiGbaesgWw5WL/ruiKfGgrqPfn244OGp8qdsPnbPFeBSgL8os9SdwwkuWvjsrljRXoXpRlE0tBdQRCdYD8lECsKQuH6HjheOFuOJWrvM4cOueGRLFM+2XZTvIi7bpv8q+T8m4bTVeA+NzxN7OAbIOLGYPDQfzvBPetsNcTpyaaWuyI/x+qVo5Z7WmrvG/EpilextR06K4mZ0XsjIlSMSQQofXlSsvWGjSDYDRM/EozOSVIQiL+70NI2HlheOTOYrHgoJDFSuuv/UnDZ4JUF8rKCWlvbMAt4IwEhYffciAtRA164a1ZhuS+rnADKdleiWYIuYFX+y/f0WxzZUomNz4cpTg5pWkEwG4zJylALV7+4QAb2VS8WkY6iS21q+svicFPdk0JZ6xGXj7v7bZTOw/XNF/JsFlSt7glgtN6Rd/moSCgAC7t+/9XCY7dA6SmHDlaTauQ2t+qDB7UFXPsJW3NSvyQ9BINFvY2Ja2aE2QbtsWhhU4esWG/0wh8doDDFqEi9djnncx6exX5T4vlmVf+1F3sN52ErSAvIUBCyiGqzPlqCIpIjKodwDfwailz4MiAxNx+KDTM4GKeWY0d/87oI5yafrBqHPxijS8WehiJflTofeMiU2sVy78eOixfSmhRyaM6xU="
        );
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
