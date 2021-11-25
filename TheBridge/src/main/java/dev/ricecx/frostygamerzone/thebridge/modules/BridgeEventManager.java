package dev.ricecx.frostygamerzone.thebridge.modules;

import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Minigame;
import dev.ricecx.frostygamerzone.minigameapi.gameevents.GameEvent;
import dev.ricecx.frostygamerzone.minigameapi.gameevents.GameEventManager;

public class BridgeEventManager extends GameEventManager {
    public BridgeEventManager(Minigame game) {
        super(game);
    }

    @Override
    public void onSkip(GameEvent evt) {
        MinigamesAPI.broadcastGame(getGame().getIdentifier(), "Event " + evt.getName() + " has been skipped.");
    }
}
