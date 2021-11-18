package dev.ricecx.frostygamerzone.minigameapi.users;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.User;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.AbstractGame;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import org.bukkit.entity.Player;

public class UserManager {

    public synchronized boolean belongsTo(User user, String gameServer) {
        String server = CorePlugin.getInstance().getRedis().getSync().get(user.getUUID().toString());
        if(server == null) return false;
        return server.equalsIgnoreCase(gameServer);
    }

    public synchronized boolean isInGameWith(GameUser origin, GameUser target) {
        Game<?,?> originGame = MinigamesAPI.getGameManager().getPlayerGame(origin.getPlayer());
        Game<?,?> targetGame = MinigamesAPI.getGameManager().getPlayerGame(target.getPlayer());

        return originGame.equals(targetGame);
    }

    public synchronized void adjustVisibility(GameUser originUser) {
        for (GameUser gameUser : Users.getUserList(GameUser.class)) {
            if(isInGameWith(originUser, gameUser)) {
                originUser.getPlayer().showPlayer(MinigamesAPI.getMinigamesPlugin(), gameUser.getPlayer());
            } else
                originUser.getPlayer().hidePlayer(MinigamesAPI.getMinigamesPlugin(), gameUser.getPlayer());
        }
    }

    public synchronized String belongsTo(User user) {
        return CorePlugin.getInstance().getRedis().getSync().get(user.getUUID().toString());
    }


}