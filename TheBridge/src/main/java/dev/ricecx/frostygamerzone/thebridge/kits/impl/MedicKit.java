package dev.ricecx.frostygamerzone.thebridge.kits.impl;

import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitUser;
import dev.ricecx.frostygamerzone.thebridge.kits.TheBridgeKit;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class MedicKit extends TheBridgeKit {

    @Getter
    TheBridgeKits kit = TheBridgeKits.HERO;

    @Override
    public ItemStack[] setItems(KitUser<BridgeUser, TheBridgeKits> player) {
        return new ItemStack[] {
                new ItemStack(Material.WOODEN_SWORD),
                new ItemStack(Material.BOW),
                new ItemStack(Material.ARROW, 32),
                generatePotionMeta(Material.POTION, new PotionData(PotionType.INSTANT_HEAL)),
                generatePotionMeta(Material.SPLASH_POTION, new PotionData(PotionType.REGEN)),
        };
    }

    private ItemStack generatePotionMeta(Material mat, PotionData data) {
        ItemStack s = new ItemStack(mat);
        PotionMeta meta = ((PotionMeta) s.getItemMeta());
        if(meta == null) return null;
        meta.setBasePotionData(data);

        s.setItemMeta(meta);
        return s;
    }
}
