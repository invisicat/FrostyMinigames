package dev.ricecx.frostygamerzone.minigameapi.lobby.core;

import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.events.GameJoinEvent;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUserStatus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class AbstractLobby implements Listener, Lobby {

    public AbstractLobby() {
        MinigamesAPI.getMinigamesPlugin().registerListeners(this);
    }

    @EventHandler
    public void onPlayerJoin(GameJoinEvent evt) {
        onJoin(evt.getPlayer());

        giveItems(evt.getPlayer().getPlayer());
    }

    public abstract void onJoin(GameUser user);

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent evt) {
        GameUser user = Users.getUser(evt.getPlayer(), GameUser.class);
        if(user == null) return;

        if(user.getGameUserStatus() == GameUserStatus.LOBBY) {
            evt.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent evt) {
        GameUser user = Users.getUser(evt.getPlayer(), GameUser.class);
        if(user == null) return;
        if(user.getGameUserStatus() == GameUserStatus.LOBBY && evt.getAction().equals(Action.PHYSICAL)) evt.setCancelled(true);
    }
}
