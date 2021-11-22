package dev.ricecx.frostygamerzone.minigameapi.users;

import dev.ricecx.frostygamerzone.bukkitapi.user.core.FrostUser;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;

public interface GameUser extends FrostUser {
    GameUserStatus getGameUserStatus();
    void setId(int id);
    int getId();
    void setGameUserStatus(GameUserStatus newStatus);

    @SuppressWarnings("unchecked")
    default <T extends Team<U>, U extends GameUser> Game<T, U> getGameObject() {
        return (Game<T,U>) MinigamesAPI.getGameManager().getGame(getGame());
    }


    default <T extends Game<?,?>> T getGameObject(Class<T> clazz) {
        return clazz.cast(getGameObject());
    }

    default String getGame() {
        return MinigamesAPI.getUserManager().belongsTo(this);
    }

}
