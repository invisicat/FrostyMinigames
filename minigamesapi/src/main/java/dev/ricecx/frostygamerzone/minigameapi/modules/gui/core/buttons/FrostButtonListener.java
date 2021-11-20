package dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.buttons;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface FrostButtonListener {
    /**
     * The event handler that should be executed when an SGButton is clicked.
     * This is intended to implemented by lambda when you create an SGButton.
     *
     * @param event The Bukkit/Spigot API's {@link InventoryClickEvent}.
     */
    void onClick(InventoryClickEvent event);

}
