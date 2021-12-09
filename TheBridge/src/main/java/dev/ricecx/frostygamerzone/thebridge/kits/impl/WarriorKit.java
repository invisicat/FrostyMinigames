package dev.ricecx.frostygamerzone.thebridge.kits.impl;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitUser;
import dev.ricecx.frostygamerzone.thebridge.kits.TheBridgeKit;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WarriorKit extends TheBridgeKit {

    @Getter private final TheBridgeKits kit = TheBridgeKits.WARRIOR;

    @Override
    public List<ItemStack> setItems(KitUser<BridgeUser, TheBridgeKits> player) {
        return List.of(
                new ItemBuilder(Material.STONE_SWORD).toItemStack(),
                new ItemBuilder(Material.WOODEN_PICKAXE).toItemStack(),
                new ItemBuilder(Material.WOODEN_AXE).toItemStack(),
                new ItemBuilder(Material.WOODEN_SHOVEL).toItemStack(),
                new ItemBuilder(Material.CRAFTING_TABLE).toItemStack()
        );
    }

}
