package dev.ricecx.frostygamerzone.thebridge;

import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.citizens.FrostNPC;
import dev.ricecx.frostygamerzone.minigameapi.citizens.NPC;
import dev.ricecx.frostygamerzone.minigameapi.game.AbstractGame;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.map.MapMeta;

import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import dev.ricecx.frostygamerzone.thebridge.countdowns.GameStartCountdown;
import dev.ricecx.frostygamerzone.thebridge.gameevents.GracePeriod;
import dev.ricecx.frostygamerzone.thebridge.lobby.BridgeMapVoter;
import dev.ricecx.frostygamerzone.thebridge.boards.BridgeGameBoard;
import dev.ricecx.frostygamerzone.thebridge.map.BridgeMapMeta;
import dev.ricecx.frostygamerzone.thebridge.modules.BridgeEventManager;
import dev.ricecx.frostygamerzone.thebridge.shop.TestShop;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeamManager;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class TheBridgeGame extends AbstractGame<BridgeUser, BridgeTeam> implements Game<BridgeTeam, BridgeUser> {


    private boolean grace = true;
    private List<UUID> shopNPCIds = new ArrayList<>();
    private MapMeta mapMeta;
    private TestShop testShop = new TestShop();

    public TheBridgeGame() {
        setTeamManager(new BridgeTeamManager(this));
        setGameCountdown(new GameStartCountdown(this));
        setMapVoter(new BridgeMapVoter(this));
        setGameEventManager(new BridgeEventManager(this));
        setMaxPlayers(32);
        //
        getGameEventManager().registerEvent(
                new GracePeriod()
        );
    }

    @Override
    public String getPrefix() {
        return "tb-";
    }

    @Override
    public void applyMapMeta(MapMeta meta) {
        LoggingUtils.info("Applying map data to " + getIdentifier() + " " + meta.getName());
        BridgeMapMeta bridgeMapMeta = (BridgeMapMeta) meta;
        for (BridgeTeam value : getTeamManager().getRegisteredTeams().values()) {
            value.applyConfigMapper(bridgeMapMeta);
        }
        this.mapMeta = meta;
    }

    @Override
    public void onStartGame() {
        setStartTime(System.currentTimeMillis());
        getGameEventManager().start();

        for (Location shop : ((BridgeMapMeta) getMapMeta()).getShops()) {
            FrostNPC npc = FrostNPC.createNPC("MassiveLag", shop);
            npc.setOnClick(testShop::onClick);
            MinigamesAPI.getCitizens().addNPC(npc);
            shopNPCIds.add(npc.uuid);
        }
    }

    @Override
    public void onPlayerStartGame(BridgeUser player) {

        if(player.getTeam() == null) {
            BridgeTeam randomTeam = getTeamManager().getAvailableTeam();
            randomTeam.addPlayer(player);
            player.setTeam(randomTeam);
        }

        teleport(player.getTeam().getSpawn(), player);

        MinigamesAPI.getScoreboardModule().provideScoreboard(player.getPlayer(), new BridgeGameBoard(player));

        player.clearInventory();
        player.provideKit();

        for (UUID shopNPC : shopNPCIds) {
            NPC npc = MinigamesAPI.getCitizens().getNPC(shopNPC).getNPC();
            npc.spawnNPC(player.getPlayer());
            OffloadTask.offloadAsync(() -> npc.setASyncSkinByUsername(MinigamesAPI.getMinigamesPlugin(), player.getPlayer(), "MassiveLag"));
            npc.setNameTagVisibility(player.getPlayer(), false);
            npc.lookAtPlayer(player.getPlayer(), player.getPlayer());
        }
    }

    @Override
    public void endGame() {

    }
}
