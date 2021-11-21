package dev.ricecx.frostygamerzone.thebridge.countdowns;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.LazyLocation;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.countdown.GameCountdown;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import dev.ricecx.frostygamerzone.thebridge.map.BridgeMapManager;
import dev.ricecx.frostygamerzone.thebridge.map.BridgeMapMeta;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class GameStartCountdown extends GameCountdown<BridgeTeam, BridgeUser> {
    public GameStartCountdown(Game<BridgeTeam, BridgeUser> game) {
        super(25, 2, game);
    }

    @Override
    public void start() {
        String topMap = "cliffs";

        MinigamesAPI.getWorldManager().loadGeneratedMapSync(topMap, (c) -> {

            BridgeMapMeta bridgeMapMeta = MinigamesAPI.getMapManager(BridgeMapManager.class).getMap(topMap);
            getGame().applyMapMeta(bridgeMapMeta);
            OffloadTask.offloadSync(() -> {
                LoggingUtils.info("Map '" + topMap + "' has been generated with world name '" + c + "' for match " + getGame().getIdentifier());
                getGame().setWorld(Bukkit.getWorld(c));
                for (BridgeTeam team : getGame().getTeamManager().getRegisteredTeams().values()) {
                    for (BridgeUser player : team.getPlayers()) {
                        getGame().teleport(team.getSpawnLocation(), player);
                    }
                }

                getGame().setGameState(GameState.IN_GAME);
            });
        });

        /*
        CompletableFuture<String> loadFuture = MinigamesAPI.getWorldManager().loadAndGenerateRandomizedMap(topMap);

        loadFuture.thenAccept((c) -> {
      //      if(e != null) e.printStackTrace();

            System.out.println("Map has loaded! with name " + c);

            BridgeMapMeta bridgeMapMeta = MinigamesAPI.getMapManager(BridgeMapManager.class).getMap(topMap);
            getGame().applyMapMeta(bridgeMapMeta);
            OffloadTask.offloadSync(() -> {
                System.out.println("LOADDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                LoggingUtils.info("Map '" + topMap + "' has been generated with world name '" + c + "' for match " + getGame().getIdentifier());
                getGame().setWorld(Bukkit.getWorld(c));
                for (BridgeTeam team : getGame().getTeamManager().getRegisteredTeams().values()) {
                    for (BridgeUser player : team.getPlayers()) {
                        getGame().teleport(team.getSpawnLocation(), player);
                    }
                }

                getGame().setGameState(GameState.IN_GAME);
            });
        });

         **/
    }

    @Override
    public void onEnoughPlayers() {
        MinigamesAPI.broadcastGame(getGame(), "&7Game is starting in 60 seconds!");
        getGame().executePlayer((p) -> p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1));

    }

    @Override
    public void onCount(int current) {
        if(current % 10 == 0 || current >= getTimer() - 10) {
            MinigamesAPI.broadcastGame(getGame(), String.format("&AGame is starting in &e%d &aseconds", getTimer() - current));
            getGame().executePlayer((p) -> p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1));
            getGame().executePlayer((p) -> p.setTotalExperience(current));
        }
    }
}
