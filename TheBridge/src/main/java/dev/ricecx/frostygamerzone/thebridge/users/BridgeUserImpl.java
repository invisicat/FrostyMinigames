package dev.ricecx.frostygamerzone.thebridge.users;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.common.database.DatabaseManager;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUserImpl;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;


@Getter
@Setter
public class BridgeUserImpl extends GameUserImpl implements BridgeUser {

    private Team<BridgeUser> team;
    private TheBridgeKits kit;

    public BridgeUserImpl(String name, UUID uuid) {
        super(name, uuid);
        System.out.println("MAKING USER " + name);
    }

    @Override
    public void loadUser() {

    }

}
