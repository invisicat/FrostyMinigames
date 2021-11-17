package dev.ricecx.frostygamerzone.thebridge.users;

import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUserImpl;
import lombok.Getter;

import java.util.UUID;


@Getter
public class BridgeUserImpl extends GameUserImpl implements BridgeUser {

    public BridgeUserImpl(String name, UUID uuid) {
        super(name, uuid);
        System.out.println("MAKING USER " + name);
    }

    @Override
    public Team<BridgeUser> getTeam() {
        return null;
    }
}
