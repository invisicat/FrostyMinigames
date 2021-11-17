package dev.ricecx.frostygamerzone.minigameapi.gamestate;

import dev.ricecx.frostygamerzone.common.LoggingUtils;

public enum GameState {
    WAITING,
    IN_GAME,
    END,
    ;

    private static GameState state;

    public static GameState getState() {
        return state;
    }

    public static void setState(GameState state) {
        LoggingUtils.info("Game state has been set to " + state + " from " + getState());
        GameState.state = state;
    }
}
