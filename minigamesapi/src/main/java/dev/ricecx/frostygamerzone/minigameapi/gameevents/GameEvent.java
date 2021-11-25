package dev.ricecx.frostygamerzone.minigameapi.gameevents;

import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Minigame;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public abstract class GameEvent {

    private final long time;
    private final TimeUnit unit;
    private final String name;

    public GameEvent(long time, TimeUnit unit, String name) {
        this.time = time;
        this.unit = unit;
        this.name = name;
    }

    public abstract void onTick(long second, Minigame minigame);

    public abstract void onFinish(Minigame minigame);

    protected long getDifferenceOf(long second) {
        long maxSeconds = getUnit().toSeconds(getTime());

        return maxSeconds - second;
    }

    protected <T> T getGame(Minigame minigame, Class<T> clazz) {
        return clazz.cast(MinigamesAPI.getGameManager().getGame(minigame.getIdentifier()));
    }
}
