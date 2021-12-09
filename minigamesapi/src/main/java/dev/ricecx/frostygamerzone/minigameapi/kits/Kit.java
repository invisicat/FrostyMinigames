package dev.ricecx.frostygamerzone.minigameapi.kits;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Kit<U, T extends KitUser<T, U>> extends Listener {
    U getKit();

    List<ItemStack> setItems(KitUser<T, U> player);

    void giveKit(T player);
}
