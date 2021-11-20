package dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.buttons;

import org.bukkit.inventory.ItemStack;

public class FrostButton {
    private ItemStack icon;
    private FrostButtonListener listener;

    /**
     * Creates an SGButton with the specified {@link ItemStack} as it's 'icon' in the inventory.
     *
     * @param icon The desired 'icon' for the SGButton.
     */
    public FrostButton(ItemStack icon){
        this.icon = icon;
    }

    /**
     * Sets the {@link FrostButtonListener} to be called when the button is clicked.
     * @param listener The listener to be called when the button is clicked.
     */
    public void setListener(FrostButtonListener listener) {
        this.listener = listener;
    }

    /**
     * A chainable alias of {@link #setListener(FrostButtonListener)}.
     *
     * @param listener The listener to be called when the button is clicked.
     * @return The {@link FrostButton} the listener was applied to.
     */
    public FrostButton withListener(FrostButtonListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Returns the {@link FrostButtonListener} that is to be executed when the button
     * is clicked.<br>
     * This is typically intended for use by the API.
     *
     * @return The listener to be called when the button is clicked.
     */
    public FrostButtonListener getListener() {
        return listener;
    }

    /**
     * Returns the {@link ItemStack} that will be used as the SGButton's icon in the
     * SGMenu (GUI).
     *
     * @return The icon ({@link ItemStack}) that will be used to represent the button.
     */
    public ItemStack getIcon() {
        return icon;
    }

    /**
     * Changes the SGButton's icon.
     *
     * @param icon The icon ({@link ItemStack}) that will be used to represent the button.
     */
    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

}
