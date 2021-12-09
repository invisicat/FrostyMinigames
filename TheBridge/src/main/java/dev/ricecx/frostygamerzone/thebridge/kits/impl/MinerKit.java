package dev.ricecx.frostygamerzone.thebridge.kits.impl;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitUser;
import dev.ricecx.frostygamerzone.thebridge.kits.TheBridgeKit;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MinerKit extends TheBridgeKit {

    @Getter
    TheBridgeKits kit = TheBridgeKits.MINER;

    @Override
    public List<ItemStack> setItems(KitUser<BridgeUser, TheBridgeKits> player) {
        return List.of(new ItemStack[]{
                new ItemStack(Material.WOODEN_SWORD),
                new ItemBuilder(Material.STONE_PICKAXE).addEnchant(Enchantment.DIG_SPEED, 1).toItemStack(),
                new ItemStack(Material.WOODEN_AXE),
                new ItemStack(Material.WOODEN_SHOVEL),
                new ItemStack(Material.FURNACE),
                new ItemStack(Material.COAL, 4),
        });
    }
}
