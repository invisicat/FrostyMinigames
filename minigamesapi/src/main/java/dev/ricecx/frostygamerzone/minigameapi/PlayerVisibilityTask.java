package dev.ricecx.frostygamerzone.minigameapi;

import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerVisibilityTask extends BukkitRunnable {

    @Override
    public void run() {
        for (GameUser gameUser : Users.getUserList(GameUser.class)) {
            MinigamesAPI.getUserManager().adjustVisibility(gameUser);
        }
    }
}
