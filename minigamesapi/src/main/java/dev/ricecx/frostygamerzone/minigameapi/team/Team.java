package dev.ricecx.frostygamerzone.minigameapi.team;

import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public abstract class Team<T extends GameUser> {

    private UUID teamID;
    private List<T> players = new ArrayList<>();
    private TeamColor teamColor = TeamColor.NONE;
    private ItemStack teamItem;
    private int size;

    public abstract Location getSpawn();

    public Team(int size) {
        this.size = size;
        this.teamID = UUID.randomUUID();
    }

    public Team(UUID uuid, int size) {
        this.teamID = uuid;
        this.size = size;
    }


    public void addPlayer(T player) {
        if(!players.contains(player))
            players.add(player);
    }

    @SuppressWarnings("unchecked")
    public void removePlayer(Player player) {
        removePlayer((T) Users.getUser(player, GameUser.class));
    }

    public void removePlayer(T player) {
        players.remove(player);
    }

    public boolean isPartOfTeam(T player) {
        return players.contains(player);
    }

    public boolean isFull() {
        return this.players.size() >= this.getSize();
    }

    public String getDisplayName() {
        return teamColor.getChatColor() + teamColor.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (!(o instanceof Team)) {
            return false;
        }

        Team<?> that = (Team<?>) o;
        return teamID.equals(that.teamID);
    }

}
