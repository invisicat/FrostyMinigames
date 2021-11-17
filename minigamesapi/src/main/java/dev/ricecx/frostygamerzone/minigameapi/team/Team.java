package dev.ricecx.frostygamerzone.minigameapi.team;

import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public abstract class Team<T extends GameUser> {
    private UUID teamID;
    private List<T> players = new ArrayList<>();
    private TeamColor teamColor = TeamColor.NONE;
    private int size;


    public abstract Location getSpawn();

    public Team(int size) {
        this.size = size;
    }

    public Team(UUID uuid, int size) {
        this.teamID = uuid;
        this.size = size;
    }


    public void addPlayer(T player) {
        if(!players.contains(player))
            players.add(player);
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
