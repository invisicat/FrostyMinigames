package dev.ricecx.frostygamerzone.thebridge.lobby;

import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.lobby.TeamSelect;
import dev.ricecx.frostygamerzone.minigameapi.lobby.core.AbstractLobby;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import dev.ricecx.frostygamerzone.thebridge.TheBridgeGame;
import dev.ricecx.frostygamerzone.thebridge.boards.BridgeGameBoard;
import dev.ricecx.frostygamerzone.thebridge.boards.BridgeLobbyPreGameBoard;
import dev.ricecx.frostygamerzone.thebridge.kits.BridgeKitRegistry;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TheBridgeLobby extends AbstractLobby implements TeamSelect<BridgeTeam>, Listener {

    @Getter Set<Material> cachedItemMaterials = new HashSet<>();
    @Getter Map<ItemStack, BridgeTeam> teamSelector = new HashMap<>();


    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerItemInteract(PlayerInteractEvent evt) {
        if (evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(evt.getItem() == null) return;
            onItemInteract(Users.getUser(evt.getPlayer(), GameUser.class), evt.getItem());
            onItemITT(Users.getUser(evt.getPlayer(), GameUser.class), evt.getItem());
        }
    }

    @Override
    public void giveItems(Player player) {
        Game<BridgeTeam, BridgeUser> game = MinigamesAPI.getGameManager().getPlayerGame(player);

        if(game.getGameState() == GameState.WAITING) {
            giveLobbyVotingItems(player, game);
        } else if(game.getGameState() == GameState.IN_GAME) {
            // do something else
        }
    }

    @Override
    public void onJoin(GameUser user) {
        Game<BridgeTeam, BridgeUser> game = MinigamesAPI.getGameManager().getPlayerGame(user);

        if(game.getGameState() == GameState.WAITING) {
            MinigamesAPI.getScoreboardModule().provideScoreboard(user.getPlayer(), new BridgeLobbyPreGameBoard(user));
        } else {
            MinigamesAPI.getScoreboardModule().provideScoreboard(user.getPlayer(), new BridgeGameBoard(game.getPlayer(user)));

        }

        OffloadTask.offloadSync(() -> {
            user.getPlayer().teleport(new Location(Bukkit.getWorld("final-lobby"), 30,83,-51));
            user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
        });
    }

    private void giveLobbyVotingItems(Player player, Game<BridgeTeam, BridgeUser> game) {
        BridgeTeam red = game.getTeamManager().getRegisteredTeams().get(game.getTeamManager().getTeams().get("red"));
        BridgeTeam blue = game.getTeamManager().getRegisteredTeams().get(game.getTeamManager().getTeams().get("blue"));

        player.getInventory().setItem(0, new ItemBuilder(Material.PAPER).setName("&2&lVote for map").toItemStack());
        player.getInventory().setItem(1, new ItemBuilder(Material.GOLDEN_AXE).setName("&6&lChange Kit").toItemStack());
        player.getInventory().setItem(3, addTeamSelector(red.getTeamItem(), red));
        player.getInventory().setItem(4, new ItemBuilder(Material.MAP).setName("&a&lRandom Team").toItemStack());
        player.getInventory().setItem(5, addTeamSelector(blue.getTeamItem(), blue));

        player.getInventory().setItem(8, new ItemBuilder(Material.RED_BED).setName("&c&lLeave").toItemStack());
    }

    @Override
    public void chooseTeam(GameUser user, BridgeTeam team) {

        user.getGameObject(TheBridgeGame.class).getTeamManager().addPlayerToTeam((BridgeUser) user, team);

        MinigamesAPI.broadcastGame(user.getGame(), String.format("&a%s &7has joined team %s", user.getName(), team.getDisplayName()));
    }

    private void onItemITT(GameUser user, ItemStack itemStack) {
        if(itemStack.getType().equals(Material.PAPER)) {
            BridgeMapVoter voter = (BridgeMapVoter) user.getGameObject().getMapVoter();
            voter.openVoteGUI(user);
        } else if(itemStack.getType().equals(Material.MAP)) {

            Optional<BridgeTeam> randomTeam = user.getGameObject(TheBridgeGame.class).getTeamManager().getRandomTeam();

            if(randomTeam.isPresent()) {
                chooseTeam(user, randomTeam.get());
            } else {
                user.getPlayer().sendMessage("Teams are full.");
            }
        } else if(itemStack.getType().equals(Material.GOLDEN_AXE)) {
            onKitSelect(user);
        }
    }

    private void onKitSelect(GameUser user) {
        BridgeUser bridgeUser = Users.getUser(user, BridgeUser.class);
        if(bridgeUser == null) return;
        MinigamesAPI.getKitRegistry(BridgeKitRegistry.class).openKitGUI(bridgeUser);
    }
}
