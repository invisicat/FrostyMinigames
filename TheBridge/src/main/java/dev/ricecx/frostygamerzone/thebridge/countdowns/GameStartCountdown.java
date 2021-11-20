package dev.ricecx.frostygamerzone.thebridge.countdowns;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.countdown.GameCountdown;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class GameStartCountdown extends GameCountdown<BridgeTeam, BridgeUser> {
    public GameStartCountdown(Game<BridgeTeam, BridgeUser> game) {
        super(60, 3, game);
    }

    @Override
    public void start() {
        String topMap = "tb-space";

        CompletableFuture<String> loadFuture = MinigamesAPI.getWorldManager().loadAndGenerateMap(topMap);

        loadFuture.whenComplete((c, e) -> {
            if(e != null) e.printStackTrace();

            System.out.println("Map has loaded!");

            OffloadTask.offloadSync(() -> {
                for (Player players : CorePlugin.getAllPlayers()) {
                    players.teleport(new Location(Bukkit.getWorld(c), 0,0,0));
                }

                getGame().setGameState(GameState.IN_GAME);
            });
        });
    }

    @Override
    public void onEnoughPlayers() {
        MinigamesAPI.broadcastGame(getGame(), "&7Game is starting in 60 seconds!");
        getGame().executePlayer((p) -> p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1));

    }

    @Override
    public void onCount(int current) {
        if(current % 10 == 0) {
            MinigamesAPI.broadcastGame(getGame(), String.format("&AGame is starting in &e%d &aseconds", getTimer() - current));
            getGame().executePlayer((p) -> p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1));
            getGame().executePlayer((p) -> p.setTotalExperience(current));
        }
    }
}
