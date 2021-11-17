package dev.ricecx.frostygamerzone.minigameapi.slime;

import com.grinderwolf.swm.api.exceptions.CorruptedWorldException;
import com.grinderwolf.swm.api.exceptions.NewerFormatException;
import com.grinderwolf.swm.api.exceptions.UnknownWorldException;
import com.grinderwolf.swm.api.exceptions.WorldInUseException;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class WorldManager implements SlimeAPI {

    @Override
    public void teleport(SlimeWorld world, Player player) {
        for (Player players : CorePlugin.getAllPlayers()) {
            players.teleport(new Location(Bukkit.getWorld(world.getName()), -57, 41, -164));
        }
    }

    public CompletableFuture<SlimeWorld> loadMap(String name) {
        return OffloadTask.offload((future) -> {
            try {
                future.complete(loadWorld(name));
            } catch (CorruptedWorldException | NewerFormatException | WorldInUseException | UnknownWorldException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public CompletableFuture<Boolean> loadAndGenerateMap(String name) {
        CompletableFuture<Boolean> doneTask = new CompletableFuture<>();
        loadMap(name).thenAccept((world) -> OffloadTask.offloadSync(() -> MinigamesAPI.getWorldManager().generateMap(world))).thenAccept((i) -> doneTask.complete(true)).complete(null);

        return doneTask;
    }

    @Override
    public void generateMap(SlimeWorld world) {
        MinigamesAPI.getSlimePlugin().generateWorld(world);
    }

    @Override
    public SlimeWorld loadWorld(String name) throws CorruptedWorldException, NewerFormatException, WorldInUseException, UnknownWorldException, IOException {
        SlimeLoader fileLoader = MinigamesAPI.getSlimePlugin().getLoader("file");

        return MinigamesAPI.getSlimePlugin().loadWorld(fileLoader, name, true, generateMapProperties());
    }

    @Override
    public SlimePropertyMap generateMapProperties() {
        // fetch from database of sorts.
        SlimePropertyMap properties = new SlimePropertyMap();

        properties.setValue(SlimeProperties.DIFFICULTY, "normal");
        properties.setValue(SlimeProperties.SPAWN_X, -57);
        properties.setValue(SlimeProperties.SPAWN_Y, 41);
        properties.setValue(SlimeProperties.SPAWN_Z, -164);
        properties.setValue(SlimeProperties.ALLOW_ANIMALS, false);
        properties.setValue(SlimeProperties.ALLOW_MONSTERS, false);
        properties.setValue(SlimeProperties.DRAGON_BATTLE, false);

        return properties;
    }

    @Override
    public void setFollowingMapProperties(String name) {
        World world = Bukkit.getWorld(name);
        if(world == null) throw new RuntimeException("Failed to set following map properties due to world being null");
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DO_INSOMNIA, false);
        world.setGameRule(GameRule.DISABLE_RAIDS, true);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        world.setTime(1600);
    }
}
