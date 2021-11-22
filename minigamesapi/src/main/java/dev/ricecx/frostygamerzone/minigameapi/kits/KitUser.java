package dev.ricecx.frostygamerzone.minigameapi.kits;

import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.inventory.ItemStack;

public interface KitUser<U, T> extends GameUser {

     T getKit();

    default void setHelmet(ItemStack helmet) {
        getPlayer().getInventory().setHelmet(helmet);
    }

    default void setChestplate(ItemStack chestplate) {
        getPlayer().getInventory().setChestplate(chestplate);
    }

    default void setLeggings(ItemStack leggings) {
        getPlayer().getInventory().setLeggings(leggings);
    }

    default void setBoots(ItemStack boots) {
        getPlayer().getInventory().setHelmet(boots);
    }

    default void clearInventory() {
        getPlayer().getInventory().clear();
    }
}
