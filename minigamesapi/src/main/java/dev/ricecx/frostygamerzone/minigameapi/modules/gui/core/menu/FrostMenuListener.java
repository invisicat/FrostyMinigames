package dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.menu;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.minigameapi.modules.gui.GUIModule;
import dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.FrostMenu;
import dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.buttons.FrostButton;
import dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.pagination.FrostPaginationButtonBuilder;
import dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.pagination.FrostPaginationButtonType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class FrostMenuListener implements Listener {
    private final GUIModule spiGUI;

    public FrostMenuListener() {

        this.spiGUI = Module.getModule(GUIModule.class).get();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        // Determine if the inventory was a SpiGUI.
        if (event.getInventory().getHolder() != null
                && event.getInventory().getHolder() instanceof FrostMenu) {

            // Get the instance of the SpiGUI that was clicked.
            FrostMenu clickedGui = (FrostMenu) event.getInventory().getHolder();

            // Check if the GUI is owner by the current plugin
            // (if not, it'll be deferred to the SGMenuListener registered
            // by that plugin that does own the GUI.)
            if (!clickedGui.getOwner().equals(CorePlugin.getInstance())) return;

            // If the default action is to cancel the event (block default interactions)
            // we'll do that now.
            // The inventory's value is checked first, so it can be overridden on a
            // per-inventory basis. If the inventory's value is null, the plugin's
            // default value is checked.
            if (clickedGui.areDefaultInteractionsBlocked() != null) {
                event.setCancelled(clickedGui.areDefaultInteractionsBlocked());
            } else {
                // Note that this can be overridden by a call to #setCancelled(false) in
                // the button's event handler.
                if (spiGUI.areDefaultInteractionsBlocked())
                    event.setCancelled(true);
            }

            // If the slot is on the pagination row, get the appropriate pagination handler.
            if (event.getSlot() > clickedGui.getPageSize()) {
                int offset = event.getSlot() - clickedGui.getPageSize();
                FrostPaginationButtonBuilder paginationButtonBuilder = spiGUI.getDefaultPaginationButtonBuilder();

                if (clickedGui.getPaginationButtonBuilder() != null) {
                    paginationButtonBuilder = clickedGui.getPaginationButtonBuilder();
                }

                FrostPaginationButtonType buttonType = FrostPaginationButtonType.forSlot(offset);
                FrostButton paginationButton = paginationButtonBuilder.buildPaginationButton(buttonType, clickedGui);
                if (paginationButton != null) paginationButton.getListener().onClick(event);
                return;
            }

            // If the slot is a stickied slot, get the button from page 0.
            if (clickedGui.isStickiedSlot(event.getSlot())) {
                FrostButton button = clickedGui.getButton(0, event.getSlot());
                if (button != null && button.getListener() != null) button.getListener().onClick(event);
                return;
            }

            // Otherwise, get the button normally.
            FrostButton button = clickedGui.getButton(clickedGui.getCurrentPage(), event.getSlot());
            if (button != null && button.getListener() != null) {
                button.getListener().onClick(event);
            }

        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        // Determine if the inventory was a SpiGUI.
        if (event.getInventory().getHolder() != null
                && event.getInventory().getHolder() instanceof FrostMenu) {

            // Get the instance of the SpiGUI that was clicked.
            FrostMenu clickedGui = (FrostMenu) event.getInventory().getHolder();

            // Check if the GUI is owner by the current plugin
            // (if not, it'll be deferred to the SGMenuListener registered
            // by that plugin that does own the GUI.)
            if (!clickedGui.getOwner().equals(CorePlugin.getInstance())) return;

            // If all the above is true and the inventory's onClose is not null,
            // call it.
            if (clickedGui.getOnClose() != null)
                clickedGui.getOnClose().accept(clickedGui);

        }

    }

}
