package dev.ricecx.frostygamerzone.minigameapi.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class GameUserMoveEvent extends PlayerMoveEvent {
    public GameUserMoveEvent(Player player, Location from, Location to) {
        super(player, from, to);
    }
}
