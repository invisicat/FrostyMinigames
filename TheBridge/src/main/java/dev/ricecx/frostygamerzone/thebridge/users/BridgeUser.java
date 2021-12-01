package dev.ricecx.frostygamerzone.thebridge.users;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitUser;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.thebridge.kits.BridgeKitRegistry;

public interface BridgeUser extends GameUser, KitUser<BridgeUser, TheBridgeKits> {
    Team<BridgeUser> getTeam();


    void setTeam(Team<BridgeUser> team);

    default void provideKit() {

        MinigamesAPI.getKitRegistry(BridgeKitRegistry.class).getKit(getKit()).giveKit(this);
    }
}
