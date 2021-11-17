package dev.ricecx.frostygamerzone.minigameapi.modules.chat;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.entity.Player;

public interface ChatAPI {

    void onChat(GameUser user, String message);

    void onJoinMessage(GameUser user);

    void onDeathMessage(GameUser user);

    void onLeaveMessage(GameUser user);


    default void sendAll(String message) {
        for (Player player : CorePlugin.getAllPlayers()) {
            player.sendMessage(Utils.color(message));
        }
    }
}
