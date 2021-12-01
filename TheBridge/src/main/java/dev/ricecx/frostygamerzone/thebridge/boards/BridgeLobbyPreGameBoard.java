package dev.ricecx.frostygamerzone.thebridge.boards;

import dev.ricecx.frostygamerzone.bukkitapi.scoreboard.FrostBoard;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BridgeLobbyPreGameBoard extends FrostBoard {

    private final Game<BridgeTeam, BridgeUser> game;
    private final BridgeUser user;


    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // TODO: remove game as you can get the game from the game user itself.
    public BridgeLobbyPreGameBoard(GameUser player) {
        super(player.getPlayer(), "&e&lT&6h&ee &6B&er&6i&ed&6g&ee");

        game = player.getGameObject();
        user = Users.getUser(player, BridgeUser.class);
    }

    @Override
    public void updateBoard() {

        String[] lines = new String[]{
                "       &7" + dtf.format(LocalDateTime.now()),
                " ",
                "&7Status: &eVoting",
                "&7Selected map: &6" + "Deep Jungle",
                " ",
                "&7Selected kit: " + "&a" + user.getKit().getName(),
                " ",
                String.format("&7Players: (%d/%d)", game.getPlayersIngame(), game.getMaxPlayers()),
                " ",
                "&bplay.frostynetwork.net"
        };
        updateLines(lines);
    }
}