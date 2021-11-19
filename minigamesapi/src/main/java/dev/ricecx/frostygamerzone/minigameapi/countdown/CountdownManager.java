package dev.ricecx.frostygamerzone.minigameapi.countdown;

import com.google.common.collect.Maps;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.common.task.GlobalTimer;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CountdownManager {

    private final Map<UUID, GameCountdown<?,?>> countdowns = Maps.newConcurrentMap();
    private GlobalTimer timer;
    @Setter private Map<UUID, BukkitRunnable> bukkitRunnables = Maps.newConcurrentMap();


    public CountdownManager(GameCountdown<?, ?> ...countdown) {
        addCountdown(countdown);
    }

    public void addCountdown(GameCountdown<?, ?> ...countdown) {
        for (GameCountdown<?, ?> gameCountdown : countdown) {
            countdowns.put(UUID.randomUUID(), gameCountdown);
        }
    }

    public void startCountdowns() {
        timer = new GlobalTimer(1, 1, TimeUnit.SECONDS, this::check);
        timer.start();
    }

    public void check() {

        for (Map.Entry<UUID, GameCountdown<?, ?>> countdown : countdowns.entrySet()) {
            if(!preconditions(countdown.getValue())) {
                cancelRunnable(countdown.getKey());
                return;
            }

            if(bukkitRunnables.get(countdown.getKey()) == null) {
                BukkitRunnable runnable = generateRunnable(countdown.getKey());
                bukkitRunnables.put(countdown.getKey(), runnable);
                    runnable.runTaskTimer(MinigamesAPI.getMinigamesPlugin(), 1, 20);
            }
        }

    }

    private void cancelRunnable(UUID uuid) {
        BukkitRunnable runnable = bukkitRunnables.get(uuid);
        if(runnable != null) {
            runnable.cancel();
            OffloadTask.offloadSync(() -> countdowns.get(uuid).onCancel());
        }
    }
    private BukkitRunnable generateRunnable(UUID countdownUUID) {
        GameCountdown<?, ?> countdown = countdowns.get(countdownUUID);
        return new BukkitRunnable() {
            int secondsPassed = 0;
            @Override
            public void run() {
                if(secondsPassed >= countdown.getTimer()) {
                    countdown.start();
                    cancel();
                    return;
                }
                countdown.onCount(secondsPassed);

                secondsPassed++;
            }
        };
    }


    public boolean preconditions(GameCountdown<?, ?> countdown) {
        return countdown.getGame().getAllPlayersInGame().size() >= countdown.getMinPlayers();
    }
}
