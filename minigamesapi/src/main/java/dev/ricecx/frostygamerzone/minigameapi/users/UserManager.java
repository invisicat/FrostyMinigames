package dev.ricecx.frostygamerzone.minigameapi.users;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.user.core.User;

public class UserManager {

    public synchronized boolean belongsTo(User user, String gameServer) {
        String server = CorePlugin.getInstance().getRedis().getSync().get(user.getUUID().toString());
        if(server == null) return false;
        return server.equalsIgnoreCase(gameServer);
    }

    public synchronized String belongsTo(User user) {
        return CorePlugin.getInstance().getRedis().getSync().get(user.getUUID().toString());
    }


}