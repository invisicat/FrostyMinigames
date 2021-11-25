package dev.ricecx.frostygamerzone.minigameapi.gameevents;

import dev.ricecx.frostygamerzone.common.task.GlobalTimer;
import dev.ricecx.frostygamerzone.minigameapi.game.Minigame;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public abstract class GameEventManager {

    private final Minigame game;
    private final Queue<GameEvent> eventQueue;

    private GlobalTimer timer;
    private boolean running;
    private long secondsPassed;

    public GameEventManager(Minigame game) {
        this.game = game;
        this.eventQueue = new LinkedList<>();
    }

    public void registerEvent(GameEvent ...events) {
        this.eventQueue.addAll(List.of(events));
    }

    public void start() {
        setRunning(true);
        timer = new GlobalTimer(1, 1, TimeUnit.SECONDS, this::loop);
        this.secondsPassed = 0;
        timer.start();
    }

    private void loop() {
        GameEvent evt = eventQueue.peek();

        if (evt == null) {
            timer.terminate();
            return;
        }

        if (evt.getUnit().toSeconds(evt.getTime()) <= secondsPassed) {
            evt.onFinish(getGame());
            eventQueue.remove();
            setSecondsPassed(0);
        }

        secondsPassed++;
        evt.onTick(secondsPassed, getGame());
    }

    public GameEvent getCurrentEvent() {
        return eventQueue.element();
    }

    public GameEvent skip() {
        GameEvent skippedEvent = eventQueue.remove();

        onSkip(skippedEvent);
        return skippedEvent;
    }

    public int getRemainingEvents() {
        return eventQueue.size();
    }

    public abstract void onSkip(GameEvent evt);

}
