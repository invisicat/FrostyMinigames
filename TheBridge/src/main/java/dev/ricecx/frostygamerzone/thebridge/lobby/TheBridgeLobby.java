package dev.ricecx.frostygamerzone.thebridge.lobby;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.lobby.TeamSelect;
import dev.ricecx.frostygamerzone.minigameapi.lobby.core.AbstractLobby;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.thebridge.lobby.boards.BridgeLobbyBoard;
import dev.ricecx.frostygamerzone.thebridge.lobby.boards.BridgeLobbyPreGameBoard;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TheBridgeLobby extends AbstractLobby implements TeamSelect<BridgeTeam>, Listener {

    @Getter Set<Material> cachedItemMaterials = new HashSet<>();
    @Getter Map<ItemStack, BridgeTeam> teamSelector = new HashMap<>();


    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    private void onPlayerItemInteract(PlayerInteractEvent evt) {
        if (evt.hasItem() && (evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            onItemInteract(Users.getUser(evt.getPlayer(), GameUser.class), evt.getItem());
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
            MinigamesAPI.getScoreboardModule().provideScoreboard(user.getPlayer(), new BridgeLobbyBoard(user));

        }
        user.getPlayer().teleport(new Location(Bukkit.getWorld("final-lobby"), 30,83,-51));

        user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.ENTITY_CHICKEN_EGG, 1, 1);
    }

    private void giveLobbyVotingItems(Player player, Game<BridgeTeam, BridgeUser> game) {
        BridgeTeam red = game.getTeamManager().getRegisteredTeams().get(game.getTeamManager().getTeams().get("red"));
        BridgeTeam blue = game.getTeamManager().getRegisteredTeams().get(game.getTeamManager().getTeams().get("blue"));

        player.getInventory().setItem(0, new ItemBuilder(Material.PAPER).setName("&2&lVote for map").toItemStack());
        player.getInventory().setItem(3, addTeamSelector(red.getTeamItem(), red));
        player.getInventory().setItem(4, new ItemBuilder(Material.MAP).setName("&a&lRandom Team").toItemStack());
        player.getInventory().setItem(5, addTeamSelector(blue.getTeamItem(), blue));

        player.getInventory().setItem(8, new ItemBuilder(Material.RED_BED).setName("&c&lLeave").toItemStack());
    }

    @Override
    public void chooseTeam(GameUser user, BridgeTeam team) {

        team.addPlayer(Users.getUser(user, BridgeUser.class));

        MinigamesAPI.broadcastGame(user.getGame(), String.format("%s has joined team %s", user.getName(), team.getDisplayName()));
    }
}
