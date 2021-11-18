package dev.ricecx.frostygamerzone.minigameapi.modules.chat;

import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.bukkitapi.module.ModuleInfo;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.events.AsyncGameChatEvent;
import dev.ricecx.frostygamerzone.minigameapi.events.GameJoinEvent;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@ModuleInfo
public class ChatModule extends Module {

    private final ChatAPI chatAPI;

    public ChatModule() {
        ChatAPI chatAPI = MinigamesAPI.getChatAPI();
        if(chatAPI == null) chatAPI = new ChatAPIImpl();

        this.chatAPI = chatAPI;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent evt) {
        GameUser user = Users.getUser(evt.getPlayer(), GameUser.class);
        if(user == null) return;
        chatAPI.onChat(user, evt.getMessage());
        evt.setCancelled(true);
    }

    @EventHandler
    public void onJoin(GameJoinEvent evt) {
        chatAPI.onJoinMessage(evt.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent evt) {
        chatAPI.onLeaveMessage(Users.getUser(evt.getPlayer(), GameUser.class));
    }

    @EventHandler
    public void onChatMessage(AsyncGameChatEvent evt) {
        MinigamesAPI.broadcastGame(evt.getGame(), evt.getMessage());
    }
}
