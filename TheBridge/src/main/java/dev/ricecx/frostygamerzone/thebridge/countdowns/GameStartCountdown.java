package dev.ricecx.frostygamerzone.thebridge.countdowns;

import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.countdown.GameCountdown;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUserStatus;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import dev.ricecx.frostygamerzone.thebridge.map.BridgeMapManager;
import dev.ricecx.frostygamerzone.thebridge.map.BridgeMapMeta;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class GameStartCountdown extends GameCountdown<BridgeTeam, BridgeUser> {

    private final int timeToStart = 25;

    public GameStartCountdown(Game<BridgeTeam, BridgeUser> game) {
        super(25, 2, game);
    }

    @Override
    public void start() {
        String topMap = getGame().getMapVoter().getTopMap();

        MinigamesAPI.getWorldManager().loadGeneratedMapSync(topMap, (c) -> {

            BridgeMapMeta bridgeMapMeta = MinigamesAPI.getMapManager(BridgeMapManager.class).getMap(topMap);
            MinigamesAPI.getWorldManager().setFollowingMapProperties(c);
            getGame().applyMapMeta(bridgeMapMeta);
            OffloadTask.offloadSync(() -> {
                LoggingUtils.info("Map '" + topMap + "' has been generated with world name '" + c + "' for match " + getGame().getIdentifier());
                getGame().setTemplateMap(topMap);
                getGame().setWorld(Bukkit.getWorld(c));
                getGame().onStartGame();

                for (Player player : getGame().getAllPlayersInGame()) {
                    BridgeUser user = Users.getUser(player, BridgeUser.class);
                    if(user == null) continue;

                    user.setGameUserStatus(GameUserStatus.INGAME);
                    user.setTeam(getGame().getTeamManager().getPlayerTeam(user));
                    getGame().onPlayerStartGame(user);
                }

                getGame().setGameState(GameState.IN_GAME);
            });
        });
    }

    @Override
    public void onEnoughPlayers() {
        MinigamesAPI.broadcastGame(getGame(), String.format("&7Game is starting in %d seconds!", timeToStart));
        getGame().executePlayer((p) -> p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1));

    }

    @Override
    public void onCount(int current) {
        getGame().executePlayer((p) -> p.setLevel(getTimer() - current));
        if(current % 10 == 0 || current >= getTimer() - 10) {
            MinigamesAPI.broadcastGame(getGame(), String.format("&AGame is starting in &e%d &aseconds", getTimer() - current));
            getGame().executePlayer((p) -> p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1));
            getGame().executePlayer((p) -> p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Utils.color("&eGame is starting in &e" + (getTimer() - current) + " &aseconds"))));
        }
    }
}
