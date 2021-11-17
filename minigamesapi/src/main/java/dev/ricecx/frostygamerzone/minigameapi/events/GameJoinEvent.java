package dev.ricecx.frostygamerzone.minigameapi.events;

import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GameJoinEvent extends GameUserEvent {
    private static final HandlerList handlers = new HandlerList();

    private final String gameServer;

    public GameJoinEvent(@NotNull GameUser who, @NotNull String gameServer) {
        super(who);
        this.gameServer = gameServer;
    }


    public String getGameServer() {
        return gameServer;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
