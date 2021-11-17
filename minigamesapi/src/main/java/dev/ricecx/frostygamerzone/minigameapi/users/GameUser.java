package dev.ricecx.frostygamerzone.minigameapi.users;

import dev.ricecx.frostygamerzone.bukkitapi.user.core.FrostUser;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;

public interface GameUser extends FrostUser {
    GameUserStatus getGameUserStatus();

    default String getGame() {
        return MinigamesAPI.getUserManager().belongsTo(this);
    }
}
