package dev.ricecx.frostygamerzone.minigameapi.countdown;

import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import lombok.Getter;

@Getter
public abstract class GameCountdown<T extends Team<U>, U extends GameUser> {

    private final int timer;
    private final int minPlayers;
    private final Game<T, U> game;

    public GameCountdown(int time, int minPlayers, Game<T, U> game) {
        this.timer = time;
        this.minPlayers = minPlayers;
        this.game = game;
    }


    public void onCancel() {}

    public void onEnoughPlayers() {}

    public void onCount(int current) {}

    public abstract void start();
}
