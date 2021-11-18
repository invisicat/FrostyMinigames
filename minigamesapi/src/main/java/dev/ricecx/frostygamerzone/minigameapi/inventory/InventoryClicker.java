package dev.ricecx.frostygamerzone.minigameapi.inventory;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import lombok.Data;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.WeakHashMap;

@Data
public class InventoryClicker implements Listener {


    private static InventoryClicker INSTANCE;

    public InventoryClicker() {
        CorePlugin.getInstance().registerListeners(this);
    }

    private WeakHashMap<ItemStack, InventoryClick> clickableItems = new WeakHashMap<>();

    public ItemStack addItemClick(ItemStack itemStack, InventoryClick clicker) {
        clickableItems.put(itemStack, clicker);
        return itemStack;
    }

    public void removeItem(ItemStack itemStack) {
        clickableItems.remove(itemStack);


    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    private void onPlayerItemInteract(PlayerInteractEvent evt) {
        if (evt.hasItem()) {
            InventoryClick inventoryClick = clickableItems.get(evt.getItem());
            if (inventoryClick != null)
                inventoryClick.run(evt.getPlayer(), evt.getItem());
        }
    }

    public static InventoryClicker getInstance() {
        if(INSTANCE == null) INSTANCE = new InventoryClicker();

        return INSTANCE;
    }
}
