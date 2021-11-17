package dev.ricecx.frostygamerzone.thebridge.handler;

import dev.ricecx.frostygamerzone.minigameapi.events.GameUserMoveEvent;
import dev.ricecx.frostygamerzone.minigameapi.utils.Styles;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonWaters implements Listener {

    @EventHandler
    public void onPlayerMove(GameUserMoveEvent event) {

        Player player = event.getPlayer();
        if(event.getTo() == null) return;

        if(event.getTo().getBlock().getType().equals(Material.WATER)) {
            if(!player.hasPotionEffect(PotionEffectType.POISON)) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 1));
                player.sendMessage(Styles.color("You were poisoned by water!"));
            }

            if(player.getHealth() <= 2.0D) {
                player.damage(1.0D);
            }
        }
    }
}
