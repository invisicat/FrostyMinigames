package dev.ricecx.frostygamerzone.minigameapi.events;

import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AsyncGameChatEvent extends GameUserEvent {
    private static final HandlerList handlers = new HandlerList();

    private final String message;
    private final String game;

    public AsyncGameChatEvent(@NotNull GameUser who, @NotNull String message) {
        super(who, true);
        this.message = message;
        this.game = who.getGame();
    }


    public String getMessage() {
        return message;
    }

    public String getGame() {
        return game;
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
