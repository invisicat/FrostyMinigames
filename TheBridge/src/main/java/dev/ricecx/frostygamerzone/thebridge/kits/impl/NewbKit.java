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

public class NewbKit extends TheBridgeKit {
    @Getter
    TheBridgeKits kit = TheBridgeKits.NEWB;

    @Override
    public ItemStack[] setItems(KitUser<BridgeUser, TheBridgeKits> player) {
        return new ItemStack[] {
                new ItemBuilder(Material.STONE_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1).toItemStack(),
                new ItemBuilder(Material.WOODEN_PICKAXE).toItemStack(),
                new ItemBuilder(Material.WOODEN_AXE).toItemStack(),
                new ItemBuilder(Material.WOODEN_SHOVEL).toItemStack(),
                new ItemBuilder(Material.CRAFTING_TABLE).toItemStack(),
        };
    }
}
