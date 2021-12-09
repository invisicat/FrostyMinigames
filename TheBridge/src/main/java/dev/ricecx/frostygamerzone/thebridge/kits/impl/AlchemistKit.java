package dev.ricecx.frostygamerzone.thebridge.kits.impl;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitUser;
import dev.ricecx.frostygamerzone.thebridge.kits.TheBridgeKit;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AlchemistKit extends TheBridgeKit {

    @Getter
    TheBridgeKits kit = TheBridgeKits.ALCHEMIST;


    @Override
    public List<ItemStack> setItems(KitUser<BridgeUser, TheBridgeKits> player) {
        return List.of(new ItemStack[]{
                new ItemStack(Material.STONE_SWORD),
                new ItemStack(Material.WOODEN_PICKAXE),
                new ItemStack(Material.WOODEN_SHOVEL),
                new ItemStack(Material.NETHER_WART),
                new ItemStack(Material.POTION),
                new ItemStack(Material.POTION),
                new ItemStack(Material.CRAFTING_TABLE),
        });
    }
}
