package dev.ricecx.frostygamerzone.minigameapi.mechanics;

import com.google.common.cache.Cache;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.events.fight.GameUserPreDeathEvent;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public abstract class AbstractFightManager implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawnSpectate(PlayerRespawnEvent evt) {
        GameUser user = Users.getUser(evt.getPlayer(), GameUser.class);
        if(user == null || !user.isSpectating()) return;

        user.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, true));

        OffloadTask.offloadSync(() -> {
            user.getPlayer().setAllowFlight(true);
            user.getPlayer().setFlying(true);
        });
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent evt) {
        final GameUser user = Users.getUser(evt.getEntity(), GameUser.class);
        if(user == null || user.isSpectating()) return;

        Bukkit.getServer().getPluginManager().callEvent(new GameUserPreDeathEvent(user));
        evt.setDeathMessage(null);
        evt.setDroppedExp(0);
        evt.getDrops().clear();

        OffloadTask.offloadDelayedSync(() -> {
            evt.getEntity().spigot().respawn();
            user.setSpectating(true);
            user.startRespawning();
        }, 1);
    }
}
