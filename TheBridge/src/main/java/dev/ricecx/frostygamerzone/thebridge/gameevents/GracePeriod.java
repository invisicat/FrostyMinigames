package dev.ricecx.frostygamerzone.thebridge.gameevents;

import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.game.Minigame;
import dev.ricecx.frostygamerzone.minigameapi.gameevents.GameEvent;
import dev.ricecx.frostygamerzone.minigameapi.utils.DurationFormatter;
import dev.ricecx.frostygamerzone.thebridge.TheBridgeGame;
import org.bukkit.Sound;

import java.util.concurrent.TimeUnit;

public class GracePeriod extends GameEvent {

    public GracePeriod() {
        super(5, TimeUnit.MINUTES, "Grace Period");
    }

    @Override
    public void onTick(long second, Minigame minigame) {
        TheBridgeGame game = getGame(minigame, TheBridgeGame.class);

        if((getDifferenceOf(second) % 60) == 0) {
            game.broadcast(String.format("&7The &6Grace period &7is ending in &e%s", DurationFormatter.LONG.format(getDifferenceOf(second))));
        } else if(getDifferenceOf(second) <= 5 && getDifferenceOf(second) != 0) {
            game.broadcast(String.format("&7The &6Grace period &7is ending in &e%s", DurationFormatter.LONG.format(getDifferenceOf(second))));
            game.executePlayer((p) -> p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1));
        }
    }

    @Override
    public void onFinish(Minigame minigame) {
        TheBridgeGame game = getGame(minigame, TheBridgeGame.class);
        game.setGrace(false);
        game.broadcast("&7The &6Grace period &7has ended");
        game.executePlayer((p) -> p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1));
    }
}