package dev.ricecx.frostygamerzone.minigameapi.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface InventoryClick {
    void run(Player player, ItemStack itemStack);
}
