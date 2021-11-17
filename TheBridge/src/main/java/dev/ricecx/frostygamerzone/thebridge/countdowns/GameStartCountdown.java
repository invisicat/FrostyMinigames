package dev.ricecx.frostygamerzone.thebridge.countdowns;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.countdown.CountdownType;
import dev.ricecx.frostygamerzone.minigameapi.countdown.GameCountdown;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class GameStartCountdown extends GameCountdown {
    public GameStartCountdown() {
        super(10, 10, CountdownType.STARTING);
    }

    @Override
    public void start() {
        String topMap = "tb-space";

        CompletableFuture<Boolean> loadFuture = MinigamesAPI.getWorldManager().loadAndGenerateMap(topMap);

        loadFuture.whenComplete((c, e) -> {
            if(e != null) e.printStackTrace();

            System.out.println("Map has loaded!");

            OffloadTask.offloadSync(() -> {
                for (Player players : CorePlugin.getAllPlayers()) {
                    players.teleport(new Location(Bukkit.getWorld(topMap), 0,0,0));
                }
            });
        });
    }

    @Override
    public void onEnoughPlayers() {
        for (Player player : CorePlugin.getAllPlayers()) {
            player.sendMessage("Game is now starting");
        }
    }

    @Override
    public void onCount(int current) {
        if(current % 10 == 0) {
            //
            System.out.println("almost done.");
        }
    }
}
