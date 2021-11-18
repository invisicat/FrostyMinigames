package dev.ricecx.frostygamerzone.minigameapi.modules.chat;

import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.events.AsyncGameChatEvent;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.Bukkit;

public class ChatAPIImpl implements ChatAPI {
    public static String SEPARATOR = "§8 » §7";

    @Override
    public void onChat(GameUser user, String message) {
        String format = user.getRank().getDisplayName() + " &7" + user.getName() + SEPARATOR + message;

        Bukkit.getPluginManager().callEvent(new AsyncGameChatEvent(user, format));
    }

    @Override
    public void onJoinMessage(GameUser user) {
        Game<?,?> game = user.getGameObject();
        Bukkit.getPluginManager().callEvent(new AsyncGameChatEvent(user,  String.format("&8&l → &r&7%s &bhas joined the game &7(%d&7/%d&7)", user.getName(), game.getPlayersIngame(), game.getMaxPlayers())));
    }

    @Override
    public void onDeathMessage(GameUser user) {

    }

    @Override
    public void onLeaveMessage(GameUser user) {
        Game<?,?> game = user.getGameObject();
        Bukkit.getPluginManager().callEvent(new AsyncGameChatEvent(user,  String.format("&8&l → &r&7%s &bhas left the game &7(%d&7/%d&7)", user.getName(), game.getPlayersIngame(), game.getMaxPlayers())));

    }
}
