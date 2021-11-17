package dev.ricecx.frostygamerzone.minigameapi.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class MinigamesListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
     //   if (e.getFrom().getBlockX() == Objects.requireNonNull(e.getTo()).getBlockX() && e.getFrom().getBlockZ() == e.getTo().getBlockZ() && e.getFrom().getBlockY() == e.getTo().getBlockY()) {
     //   }

       // MinigamesAPI.getMinigamesPlugin().getServer().getPluginManager().callEvent(new GameUserMoveEvent(e.getPlayer(), e.getFrom(), e.getTo()));
    }

}
