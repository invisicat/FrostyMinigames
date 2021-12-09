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

    /**
     * Abstract Countdown class suited for the starting of games
     * @param time time in seconds
     * @param minPlayers minimum amount of players needed to start the game
     * @param game game to start
     */
    public GameCountdown(int time, int minPlayers, Game<T, U> game) {
        this.timer = time;
        this.minPlayers = minPlayers;
        this.game = game;
    }


    /**
     * Called when the countdown is cancelled
     */
    public void onCancel() {}

    /**
     * Called when there is enough players in game and the countdown is starting
     */
    public void onEnoughPlayers() {}

    /**
     * Called every second
     * @param current seconds that have passed
     */
    public void onCount(int current) {}


    /**
     * Called when the countdown is finished
     */
    public abstract void start();
}
