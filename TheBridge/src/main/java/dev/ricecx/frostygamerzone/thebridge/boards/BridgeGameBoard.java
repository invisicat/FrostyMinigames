package dev.ricecx.frostygamerzone.thebridge.boards;

import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.scoreboard.FrostBoard;
import dev.ricecx.frostygamerzone.minigameapi.board.FrostCrossCompatBoard;
import dev.ricecx.frostygamerzone.thebridge.TheBridgeGame;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeamManager;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BridgeGameBoard extends FrostCrossCompatBoard {

    private final BridgeUser player;
    private final long startTime;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BridgeGameBoard(BridgeUser player) {
        super(player.getPlayer(), "&e&lT&6&lh&e&le &6&lB&e&lr&6&li&e&ld&6&lg&e&le");

        this.player = player;
        this.startTime = player.getGameObject().getStartTime();
    }

    @Override
    public void updateBoard() {
        BridgeTeamManager teamManager = (BridgeTeamManager) player.getGameObject(TheBridgeGame.class).getTeamManager();
        String[] lines = new String[]{
                "       &7" + dtf.format(LocalDateTime.now()),
                " ",
                "&c&lRED&f ",
                " &7Players:&f " + Utils.color("&a" + teamManager.getTeam("red").getPlayers().size()),
                " &7Nexus:&f " + teamManager.getTeam("red").getNexusHealth(),
                " ",
                "&9&lBLUE&f ",
                " &7Players:&f " + Utils.color("&a" + teamManager.getTeam("blue").getPlayers().size()),
                " &7Nexus:&f " + teamManager.getTeam("blue").getNexusHealth(),
                " ",
                "&7Elapsed time: &6" + DurationFormatUtils.formatDuration(System.currentTimeMillis() - startTime, "mm:ss"),
                "&7Map: " + "&6" + player.getGameObject().getTemplateMap(),
                "&7Kit: " + "&6" + player.getKit().getName(),
                "",
                "&bplay.frostynetwork.net"
        };
        updateLines(lines);
    }
}
