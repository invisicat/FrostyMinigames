package dev.ricecx.frostygamerzone.thebridge.kits.impl;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitUser;
import dev.ricecx.frostygamerzone.thebridge.kits.TheBridgeKit;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WarriorKit extends TheBridgeKit {

    @Getter private final TheBridgeKits kit = TheBridgeKits.WARRIOR;

    @Override
    public ItemStack[] setItems(KitUser<BridgeUser, TheBridgeKits> player) {
        return new ItemStack[] {
                new ItemBuilder(Material.STONE_SWORD).toItemStack(),
                null,
                new ItemBuilder(Material.MELON).toItemStack()
        };
    }

}
