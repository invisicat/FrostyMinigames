package dev.ricecx.frostygamerzone.minigameapi.countdown;

import lombok.Getter;

@Getter
public abstract class GameCountdown {

    private final int timer;
    private final int minPlayers;

    public GameCountdown(int time, int minPlayers, CountdownType countdownType) {
        this.timer = time;
        this.minPlayers = minPlayers;
    }


    public void onCancel() {}

    public void onEnoughPlayers() {}

    public void onCount(int current) {}

    public abstract void start();
}
