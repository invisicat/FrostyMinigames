package dev.ricecx.frostygamerzone.thebridge.modules;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.minigameapi.modules.chat.ChatAPI;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.entity.Player;

public class ChatImpl implements ChatAPI {

    @Override
    public void onChat(GameUser user, String message) {
        for (Player player : CorePlugin.getAllPlayers()) {
            player.sendMessage(user + " : " + message);
        }
    }

    @Override
    public void onJoinMessage(GameUser user) {
        sendAll(user.getName() + " has joined the game!");
    }

    @Override
    public void onDeathMessage(GameUser user) {

    }

    @Override
    public void onLeaveMessage(GameUser user) {
        sendAll(user.getName() + " has left the game!");
    }
}
