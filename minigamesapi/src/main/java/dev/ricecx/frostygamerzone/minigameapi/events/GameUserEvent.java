package dev.ricecx.frostygamerzone.minigameapi.events;

import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract class GameUserEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    protected GameUser player;

    public GameUserEvent(@NotNull final GameUser who) {
        player = who;
    }

    GameUserEvent(@NotNull final GameUser who, boolean async) {
        super(async);
        player = who;

    }

    /**
     * Returns the player involved in this event
     *
     * @return Player who is involved in this event
     */
    @NotNull
    public final GameUser getPlayer() {
        return player;
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