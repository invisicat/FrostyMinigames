package dev.ricecx.frostygamerzone.minigameapi.slime;

import com.grinderwolf.swm.api.exceptions.CorruptedWorldException;
import com.grinderwolf.swm.api.exceptions.NewerFormatException;
import com.grinderwolf.swm.api.exceptions.UnknownWorldException;
import com.grinderwolf.swm.api.exceptions.WorldInUseException;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface SlimeAPI {

    void teleport(SlimeWorld world, Player player);

    CompletableFuture<String> loadAndGenerateMap(String name);


    void generateMap(SlimeWorld world);

    SlimeWorld loadWorld(String name) throws CorruptedWorldException, NewerFormatException, WorldInUseException, UnknownWorldException, IOException;

    SlimePropertyMap generateMapProperties();

    void setFollowingMapProperties(String name);
}
