package dev.ricecx.frostygamerzone.minigameapi;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.grinderwolf.swm.api.SlimePlugin;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.bukkitapi.modules.scoreboard.ScoreboardModule;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.adapters.LocationAdapter;
import dev.ricecx.frostygamerzone.minigameapi.citizens.Citizens;
import dev.ricecx.frostygamerzone.minigameapi.commands.GameManagerCommand;
import dev.ricecx.frostygamerzone.minigameapi.commands.TestGUICommand;
import dev.ricecx.frostygamerzone.minigameapi.countdown.CountdownManager;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.game.GameManager;
import dev.ricecx.frostygamerzone.minigameapi.game.GameManagerListener;
import dev.ricecx.frostygamerzone.minigameapi.gamestate.GameState;
import dev.ricecx.frostygamerzone.minigameapi.inventory.InventoryClicker;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitRegistry;
import dev.ricecx.frostygamerzone.minigameapi.listeners.MinigamesListener;
import dev.ricecx.frostygamerzone.minigameapi.map.MapManager;
import dev.ricecx.frostygamerzone.minigameapi.mapvoting.MapVoter;
import dev.ricecx.frostygamerzone.minigameapi.modules.chat.ChatAPI;
import dev.ricecx.frostygamerzone.minigameapi.modules.chat.ChatModule;
import dev.ricecx.frostygamerzone.minigameapi.modules.gui.GUIModule;
import dev.ricecx.frostygamerzone.minigameapi.slime.WorldManager;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamListener;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUserStatus;
import dev.ricecx.frostygamerzone.minigameapi.users.UserManager;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import dev.ricecx.frostygamerzone.minigameapi.utils.Reflection;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


@Data
public class MinigamesAPI {


    @Getter
    @Setter
    private static Minigame minigame;

    @Getter
    private static SlimePlugin slimePlugin;

    @Getter
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Location.class, new LocationAdapter())
            .create();

    @Getter
    private static GameManager gameManager = new GameManager();

    @Getter
    @Setter
    private static MapManager<?> mapManager;

    @Getter
    @Setter
    private static CountdownManager countdownManager = new CountdownManager();
    @Getter
    @Setter
    private static MapVoter mapVoterManager;
    @Getter
    @Setter
    private static WorldManager worldManager = new WorldManager();
    @Getter
    @Setter
    private static ChatAPI chatAPI;
    @Getter
    private static UserManager userManager = new UserManager();
    @Getter
    private static ScoreboardModule scoreboardModule;
    private static PlayerVisibilityTask playerVisibilityTask;
    private static InventoryClicker inventoryClicker;
    @Getter
    @Setter
    private static KitRegistry<?, ?> kitRegistry;
    @Getter
    private static Citizens citizens;
    @Getter
    private static ViaAPI<Player> viaVersionAPI;

    @SuppressWarnings("unchecked") /* Unchecked casts suck :P */
    public static void loadAPI() {
        long startLoad = System.currentTimeMillis();

        Plugin plugin = Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        Preconditions.checkNotNull(plugin, "SLIME WORLD MANAGER IS NOT LOADED. PROCEED WITH CAUTION.");
        slimePlugin = (SlimePlugin) plugin;
        playerVisibilityTask = new PlayerVisibilityTask();
        inventoryClicker = InventoryClicker.getInstance();

        playerVisibilityTask.runTaskTimer(MinigamesAPI.getMinigamesPlugin(), 1, (20 * 5));

        countdownManager.startCountdowns();

        GameState.setState(GameState.WAITING);
        scoreboardModule = new ScoreboardModule();
        citizens = new Citizens();

        viaVersionAPI = Via.getAPI();

        getMapManager().loadMapsIntoCache();
        OffloadTask.offloadSync(() -> getWorldManager().loadMap("final-lobby").thenApply((sw) -> {
            getWorldManager().generateMap(sw);
            return sw;
        }).whenComplete((sw, i) -> getWorldManager().setFollowingMapProperties(sw.getName())));
        new ChatModule();
        new GUIModule();

        getMinigamesPlugin().registerListeners(new MinigamesListener(), new GameManagerListener(), new TeamListener(), citizens);
        getMinigamesPlugin().registerCommands(new GameManagerCommand(),
                new TestGUICommand());

        LoggingUtils.info("MinigamesAPI loaded in " + (System.currentTimeMillis() - startLoad) + "ms!");
    }


    public static void broadcastGame(Game<?, ?> game, String... message) {
        broadcastGame(game.getIdentifier(), message);
    }

    public static void broadcastGame(String game, String... message) {
        for (Player player : getGameManager().getAllPlayersInGame(game)) {
            player.sendMessage(Utils.color(message));
        }
    }

    public static void broadcast(String... messages) {
        for (Player player : CorePlugin.getAllPlayers()) {
            player.sendMessage(Utils.color(messages));
        }
    }

    public static MinigamesPlugin getMinigamesPlugin() {
        return MinigamesPlugin.getPlugin(MinigamesPlugin.class);
    }

    public static <T extends MapManager<?>> T getMapManager(Class<T> man) {
        return man.cast(getMapManager());
    }
    public static <T extends KitRegistry<?, ?>> T getKitRegistry(Class<T> man) {
        return man.cast(getKitRegistry());
    }

    public static boolean isIngame(Player player) {
        GameUser user = Users.getUser(player, GameUser.class);
        if(user == null) return false;

        return user.getGameUserStatus() == GameUserStatus.INGAME;
    }
}
