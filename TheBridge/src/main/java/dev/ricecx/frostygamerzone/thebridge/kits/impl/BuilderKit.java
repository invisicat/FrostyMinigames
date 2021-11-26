package dev.ricecx.frostygamerzone.thebridge.kits.impl;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitUser;
import dev.ricecx.frostygamerzone.thebridge.kits.TheBridgeKit;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BuilderKit extends TheBridgeKit {


    @Getter
    TheBridgeKits kit = TheBridgeKits.BUILDER;

    @Override
    public ItemStack[] setItems(KitUser<BridgeUser, TheBridgeKits> player) {
        return new ItemStack[] {
                new ItemStack(Material.WOODEN_SWORD),
                new ItemStack(Material.STONE_AXE),
                new ItemStack(Material.STONE_PICKAXE),
                new ItemStack(Material.WOODEN_SHOVEL),
                new ItemStack(Material.DIRT, 32),
                new ItemStack(Material.COBBLESTONE, 64),
                new ItemStack(Material.OAK_PLANKS, 64),
        };
    }
}
