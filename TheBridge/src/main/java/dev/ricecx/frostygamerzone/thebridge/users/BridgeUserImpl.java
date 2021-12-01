package dev.ricecx.frostygamerzone.thebridge.users;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.common.database.DatabaseManager;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.map.TeamMapMeta;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUserImpl;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import dev.ricecx.frostygamerzone.minigameapi.utils.Styles;
import dev.ricecx.frostygamerzone.thebridge.map.BridgeMapManager;
import dev.ricecx.frostygamerzone.thebridge.map.BridgeMapMeta;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Timestamp;
import java.util.UUID;


@Getter
@Setter
public class BridgeUserImpl extends GameUserImpl implements BridgeUser {

    private Team<BridgeUser> team;
    private TheBridgeKits kit;

    public BridgeUserImpl(String name, UUID uuid) {
        super(name, uuid);

    }

    @Override
    public void loadUser() {
        LoggingUtils.info("loading presecondary user for " + getId());
        // HACK: this actually doesn't work without setting a timestamp as
        // returning * will only return if a tuple has been updated.
        DatabaseManager.getSQLUtils().executeQuery( "INSERT INTO thebridge_users (id, uuid) VALUES(?, ?) ON CONFLICT (uuid) DO UPDATE SET last_joined = ? RETURNING *", (ps -> {
            ps.setInt(1, getId());
            ps.setString(2, getUUID().toString());
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        }), (rs) -> {
            if(rs.next()) {
                TheBridgeKits kit = TheBridgeKits.from(rs.getString("equipped_kit"));
                if(kit == null) kit = TheBridgeKits.WARRIOR;
                LoggingUtils.info("Loading kit " + kit.getName() + " for " + getName());
                setKit(kit);
            }
            return rs;
        });
    }

    @Override
    public void startRespawning() {
        // 5.5 seconds
        double deathDuration = 5.5 * 1000;
        long whenToRespawn = (long) (System.currentTimeMillis() + deathDuration);

        OffloadTask.offloadDelayedSync(() -> {
            // teleport them to spectator
            Location spectatorLocation = this.getGameObject().getLocation(((BridgeMapMeta) this.getGameObject().getMapMeta()).getSpectatorSpawn());
            getPlayer().teleport(spectatorLocation);
            getPlayer().setGameMode(GameMode.SPECTATOR);
        }, 1);

        getRespawnRunnable(whenToRespawn).runTaskTimer(MinigamesAPI.getMinigamesPlugin(), 2, 2);
    }

    private BukkitRunnable getRespawnRunnable(long whenToRespawn) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                if(System.currentTimeMillis() >= whenToRespawn) {
                    setSpectating(false);
                    getPlayer().teleport(getTeam().getSpawn());
                    getPlayer().setGameMode(GameMode.SURVIVAL);
                    // TODO: spawn protection
                    getPlayer().getInventory().clear();

                    // Set Ingame Player Mechanics
                    getPlayer().setHealth(20.0D);
                    getPlayer().setHealthScale(20.0D);
                    getPlayer().setFoodLevel(20);
                    getPlayer().setExp(0);
                    setLevel(0);

                    provideKit();

                    this.cancel();
                }
                getPlayer().sendTitle(Utils.color("&c&lRESPAWNING"), Utils.color("&fYou will respawn in " + Styles.round((whenToRespawn - System.currentTimeMillis()) / 1000.0, 1) + " seconds"), 0, 5, 0);
            }
        };
    }
}
