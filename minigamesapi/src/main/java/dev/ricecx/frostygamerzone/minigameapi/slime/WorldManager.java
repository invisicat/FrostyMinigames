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
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

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
                SlimeWorld world = loadWorld(name);

                future.complete(world);
            } catch (CorruptedWorldException | NewerFormatException | WorldInUseException | UnknownWorldException | IOException e) {
                e.printStackTrace();
            }
        });
    }



    public void loadGeneratedMapSync(String name, Consumer<String> mapName) {
        AtomicReference<String> ranName = new AtomicReference<>();

        loadMap(name).thenAccept((s) -> OffloadTask.offloadSync(() -> {
            generateRandomMap(s, ranName::set);
            mapName.accept(ranName.get());
        }));
    }

    public CompletableFuture<String> loadAndGenerateRandomizedMap(String name) {
        CompletableFuture<String> doneTask = new CompletableFuture<>();
        CompletableFuture<SlimeWorld> futureWorld = loadMap(name);

        futureWorld.thenApply((world) -> {
            AtomicReference<String> wrld = new AtomicReference<>();
            OffloadTask.offloadSync(() -> MinigamesAPI.getWorldManager().generateRandomMap(world, wrld::set));

            return wrld.get();
        }).thenAccept(doneTask::complete);

        return doneTask;
    }

    /**
     * Loads and generates a map and returns the map's name
     * @param name name of the template world
     * @return the temporary world's name
     */
    @Override
    public CompletableFuture<String> loadAndGenerateMap(String name) {
        CompletableFuture<String> doneTask = new CompletableFuture<>();
        CompletableFuture<SlimeWorld> futureWorld = loadMap(name);
        futureWorld.thenAccept((world) -> OffloadTask.offloadSync(() -> MinigamesAPI.getWorldManager().generateMap(world))).complete(null);
        futureWorld.whenComplete(((slimeWorld, throwable) -> doneTask.complete(slimeWorld.getName())));

        return doneTask;
    }

    @Override
    public void generateMap(SlimeWorld world) {
        MinigamesAPI.getSlimePlugin().generateWorld(world);
    }

    @Override
    public void generateRandomMap(SlimeWorld world, Consumer<String> generatedNameCallback) {
        SlimeWorld clonedWorld = world.clone(generateMapName());

        generateMap(clonedWorld);
        generatedNameCallback.accept(clonedWorld.getName());
    }

    @Override
    public SlimeWorld loadWorld(String name) throws CorruptedWorldException, NewerFormatException, WorldInUseException, UnknownWorldException, IOException {
        SlimeLoader fileLoader = MinigamesAPI.getSlimePlugin().getLoader("file");

        return MinigamesAPI.getSlimePlugin().loadWorld(fileLoader, name, true, generateMapProperties());
    }

    private String generateMapName() {
        return "mini-tb-" + ThreadLocalRandom.current().nextInt(3,500);
    }

    @Override
    public SlimePropertyMap generateMapProperties() {
        // fetch from database of sorts.
        SlimePropertyMap properties = new SlimePropertyMap();

        properties.setValue(SlimeProperties.DIFFICULTY, "normal");
        properties.setValue(SlimeProperties.SPAWN_X, 0);
        properties.setValue(SlimeProperties.SPAWN_Y, 0);
        properties.setValue(SlimeProperties.SPAWN_Z, 0);
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
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
        world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
        world.setTime(1600);
    }
}
