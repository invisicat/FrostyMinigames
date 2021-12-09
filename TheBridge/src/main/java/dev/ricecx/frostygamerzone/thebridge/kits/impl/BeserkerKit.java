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

public class BeserkerKit extends TheBridgeKit {

    @Getter
    TheBridgeKits kit = TheBridgeKits.BESERKER;


    @Override
    public List<ItemStack> setItems(KitUser<BridgeUser, TheBridgeKits> player) {
        return List.of(new ItemStack[]{
                new ItemBuilder(Material.STONE_AXE).setName("&eBeserker's Axe").addEnchant(Enchantment.DURABILITY, 1).hideAttributes().toItemStack(),
                new ItemStack(Material.WOODEN_PICKAXE),
                new ItemStack(Material.WOODEN_SHOVEL),
                new ItemStack(Material.CRAFTING_TABLE)
        });
    }
}
