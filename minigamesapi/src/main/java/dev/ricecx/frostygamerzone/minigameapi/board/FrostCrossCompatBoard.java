package dev.ricecx.frostygamerzone.minigameapi.board;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import dev.ricecx.frostygamerzone.bukkitapi.scoreboard.FrostBoard;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import org.bukkit.entity.Player;

/**
 * ViaVersion compatible board so 1.8 players don't have their scoreboard cut off.
 * @implNote <bold>No</bold> code needs to be <bold>refactored</bold> as this only overrides {@link FrostBoard#hasLinesMaxLength()}
 */
public abstract class FrostCrossCompatBoard extends FrostBoard {

    public FrostCrossCompatBoard(Player player, String objectiveName) {
        super(player, objectiveName);
    }

    @Override
    protected boolean hasLinesMaxLength() {
        return MinigamesAPI.getViaVersionAPI().getPlayerVersion(getPlayer()) < ProtocolVersion.v1_13.getVersion(); // or just return true;
    }
}
