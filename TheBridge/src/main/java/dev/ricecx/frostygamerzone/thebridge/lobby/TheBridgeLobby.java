package dev.ricecx.frostygamerzone.thebridge.lobby;

import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.inventory.InventoryClicker;
import dev.ricecx.frostygamerzone.minigameapi.lobby.core.AbstractLobby;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.thebridge.lobby.boards.BridgeLobbyBoard;
import dev.ricecx.frostygamerzone.thebridge.lobby.boards.BridgeLobbyPreGameBoard;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class TheBridgeLobby extends AbstractLobby {

    @Override
    public void giveItems(Player player) {
        Game<BridgeTeam, BridgeUser> game = MinigamesAPI.getGameManager().getPlayerGame(player);

        if(game.getGameState() == GameState.WAITING) {
            giveLobbyVotingItems(player);
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

    private void giveLobbyVotingItems(Player player) {
        InventoryClicker ic = InventoryClicker.getInstance();
        ItemStack redHelmet = new ItemBuilder(Material.LEATHER_HELMET).setName("&cTeam Red").toItemStack();
        LeatherArmorMeta meta = ((LeatherArmorMeta) redHelmet.getItemMeta());
        if(meta != null) meta.setColor(Color.RED);
        redHelmet.setItemMeta(meta);

        ItemStack blueHelmet = new ItemBuilder(Material.LEATHER_HELMET).setName("&9Team Blue").toItemStack();
        LeatherArmorMeta blueMeta = ((LeatherArmorMeta) blueHelmet.getItemMeta());
        if(blueMeta != null) blueMeta.setColor(Color.BLUE);
        blueHelmet.setItemMeta(blueMeta);

        player.getInventory().setItem(0, ic.addItemClick(new ItemBuilder(Material.PAPER).setName("&2&lVote for map").toItemStack(), this::handleMapVoting));
        player.getInventory().setItem(3, ic.addItemClick(redHelmet, this::chooseTeam));
        player.getInventory().setItem(4, ic.addItemClick(new ItemBuilder(Material.MAP).setName("&a&lRandom Team").toItemStack(), this::chooseTeam));
        player.getInventory().setItem(5, ic.addItemClick(blueHelmet, this::chooseTeam));

        player.getInventory().setItem(8, new ItemBuilder(Material.RED_BED).setName("&c&lLeave").toItemStack());
    }

    private void handleMapVoting(Player player, ItemStack itemStack) {
        player.sendMessage("OPENING GUI FOR VOTING.");
    }

    private void chooseTeam(Player player, ItemStack itemStack) {
        player.sendMessage("CHOOSING TEAM " + itemStack.getItemMeta().getDisplayName());
    }
}
