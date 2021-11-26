package dev.ricecx.frostygamerzone.minigameapi.events.fight;

import dev.ricecx.frostygamerzone.minigameapi.events.GameUserEvent;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class GameUserRespawnEvent extends GameUserEvent {
    private static final HandlerList handlers = new HandlerList();

    public GameUserRespawnEvent(@NotNull GameUser who) {
        super(who);
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return handlers;
    }
}
