package dev.ricecx.frostygamerzone.thebridge.kits;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitRegistry;
import dev.ricecx.frostygamerzone.thebridge.kits.impl.WarriorKit;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;

public class BridgeKitRegistry extends KitRegistry<TheBridgeKits, BridgeUser> {
    public BridgeKitRegistry() {
        register(new WarriorKit());
    }
}
