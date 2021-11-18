package dev.ricecx.frostygamerzone.thebridge.lobby.boards;

import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.scoreboard.FrostBoard;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BridgeLobbyBoard extends FrostBoard {

    private final GameUser player;
    private final long startTime;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BridgeLobbyBoard(GameUser player) {
        super(player.getPlayer(), "&e&lT&6&lh&e&le &6&lB&e&lr&6&li&e&ld&6&lg&e&le");

        this.player = player;
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void updateBoard() {
        String[] lines = new String[]{
                "       &7" + dtf.format(LocalDateTime.now()),
                " ",
                "&c&lRED&f ",
                " &7Players:&f " + Utils.color("&a12"),
                " &7Nexus:&f " + 96,
                " ",
                "&9&lBLUE&f ",
                " &7Players:&f " + Utils.color("&a13"),
                " &7Nexus:&f " + 85,
                " ",
                "Map: " + "&6Space",
                "Kit: " + "&6Traitor",
                "&6" + DurationFormatUtils.formatDuration(System.currentTimeMillis() - startTime, "mm:ss"),
                "&bplay.frostynetwork.net"
        };
        updateLines(lines);
    }
}
