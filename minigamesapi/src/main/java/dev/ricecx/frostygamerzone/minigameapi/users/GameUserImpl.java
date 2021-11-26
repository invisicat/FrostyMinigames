package dev.ricecx.frostygamerzone.minigameapi.users;

import dev.ricecx.frostygamerzone.api.Rank;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.user.utils.WaitLoader;
import dev.ricecx.frostygamerzone.common.database.DatabaseManager;
import dev.ricecx.frostygamerzone.common.database.SQLUtils;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.events.GameJoinEvent;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Setter
public abstract class GameUserImpl implements GameUser {

    @Getter
    private final String name;
    @Getter private final UUID UUID;
    @Getter private Rank rank;
    @Getter private int coins;
    @Getter private int level;
    @Getter private int id;

    @Getter @Setter private GameUserStatus gameUserStatus;
    @Getter @Setter private boolean spectating;


    public GameUserImpl(String name, UUID uuid) {
        this.name = name;
        this.UUID = uuid;
        this.gameUserStatus = GameUserStatus.LOBBY;
    }

    @Override
    public void load(WaitLoader waitLoader) {
        if(!MinigamesAPI.getGameManager().playerBelongsToAny(getUUID())) {
            waitLoader.disallow("You are not allowed to be here.");
            return;
        }

        DatabaseManager.getSQLUtils().executeQuery("SELECT primary_group FROM luckperms_players WHERE uuid = ?", (ps) -> ps.setString(1, getUUID().toString()), (rs) -> {
            if(rs.next()) {

                setRank(Rank.fromName(rs.getString("primary_group")));
                if(getRank() == null)
                    setRank(Rank.MEMBER);
            }
            return rs;
        });
        
        DatabaseManager.getSQLUtils().executeQuery("SELECT * FROM global WHERE uuid = ?", (ps) -> ps.setString(1, getUUID().toString()), (rs) -> {
            if(rs.next()) {
                setCoins(rs.getInt("coins"));
                setLevel(rs.getInt("level"));
                setId(rs.getInt("id"));
            }
            
            return rs;
        });

        loadUser();
        waitLoader.verifyResponse(true);
    }

    @Override
    public void loadSecondary(Player player) {
        String gameServer = MinigamesAPI.getUserManager().belongsTo(this);
        player.sendMessage(Utils.color("&cYour data has been loaded!"));
        gameUserStatus = GameUserStatus.LOBBY;

        // hide from everyone
        OffloadTask.offloadSync(() -> {
            List<Player> playersInTheirGame = MinigamesAPI.getGameManager().getAllPlayersInGame(gameServer);
            for (Player allPlayers : CorePlugin.getAllPlayers()) {
                if(allPlayers.getUniqueId() == getUUID()) continue;
                if (!playersInTheirGame.contains(allPlayers)) {
                    allPlayers.hidePlayer(MinigamesAPI.getMinigamesPlugin(), player);
                } else {
                    allPlayers.showPlayer(MinigamesAPI.getMinigamesPlugin(), player);
                }
            }

            MinigamesAPI.getGameManager().addPlayerToGame(player.getUniqueId(), gameServer);
            Bukkit.getPluginManager().callEvent(new GameJoinEvent(this, gameServer));
        });
    }


    public abstract void loadUser();

    @Override
    public void save() {

    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(UUID);
    }

    @Override
    public void cleanup() {

    }
}
