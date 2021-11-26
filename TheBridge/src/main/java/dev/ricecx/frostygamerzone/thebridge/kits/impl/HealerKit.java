package dev.ricecx.frostygamerzone.thebridge.kits.impl;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitUser;
import dev.ricecx.frostygamerzone.thebridge.kits.TheBridgeKit;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class HealerKit extends TheBridgeKit {
    @Getter
    TheBridgeKits kit = TheBridgeKits.HEALER;

    @Override
    public ItemStack[] setItems(KitUser<BridgeUser, TheBridgeKits> player) {
        return new ItemStack[] {
                new ItemBuilder(Material.BOW).setName("&dHealer's &7bow").toItemStack(),
                new ItemStack(Material.WOODEN_PICKAXE),
                new ItemStack(Material.WOODEN_SHOVEL),
                new ItemStack(Material.CRAFTING_TABLE),
                new ItemStack(Material.ARROW, 10)
        };
    }
}
