package dev.ricecx.frostygamerzone.minigameapi.countdown;

import dev.ricecx.frostygamerzone.common.task.GlobalTimer;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class CountdownManager {

    private final GameCountdown countdown;
    private GlobalTimer timer;
    @Setter private BukkitRunnable runnable;


    public CountdownManager(GameCountdown countdown) {
        this.countdown = countdown;

    }

    public void startCountdowns() {
        timer = new GlobalTimer(1, 1, TimeUnit.SECONDS, this::check);
        timer.start();
    }

    public void check() {
        if(!preconditions()) {
            cancelRunnable();
            return;
        }

        if(runnable == null) {
            setRunnable(generateRunnable());
            runnable.runTaskTimer(MinigamesAPI.getMinigamesPlugin(), 1, 20);
        }
    }

    private void cancelRunnable() {
        if(runnable != null) {
            runnable.cancel();
            OffloadTask.offloadSync(countdown::onCancel);
        }
    }
    private BukkitRunnable generateRunnable() {
        return new BukkitRunnable() {
            int secondsPassed = 0;
            @Override
            public void run() {
                if(secondsPassed >= countdown.getTimer()) {
                    countdown.start();
                    cancel();
                    timer.terminate();
                    return;
                }
                countdown.onCount(secondsPassed);

                secondsPassed++;
            }
        };
    }


    public boolean preconditions() {
        return Bukkit.getOnlinePlayers().size() >= countdown.getMinPlayers();
    }
}
