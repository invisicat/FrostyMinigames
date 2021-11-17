package dev.ricecx.frostygamerzone.thebridge.handler;

import dev.ricecx.frostygamerzone.minigameapi.utils.Styles;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Tag;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AntiCraft implements Listener {

    public Set<Material> materialSet = new HashSet<>();

    public AntiCraft() {
        materialSet.addAll(Tag.ITEMS_BOATS.getValues());
        materialSet.addAll(Arrays.asList(Material.BUCKET, Material.TNT, Material.HOPPER,
                Material.HOPPER_MINECART, Material.PISTON, Material.STICKY_PISTON, Material.MELON, Material.MELON_SEEDS, Material.ARMOR_STAND,
                Material.MINECART, Material.CHEST_MINECART ));


    }

    @EventHandler
    public void onItemCraft(PrepareItemCraftEvent e) {
        ItemStack i = e.getInventory().getResult();
        if (shouldBlock(i)) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
            for (HumanEntity he : e.getViewers()) {
                if (he instanceof Player) {
                    he.sendMessage(Styles.color("&c&l(!)&c You can not craft this item"));
                }
            }
        }
    }

    private boolean shouldBlock(ItemStack i) {
        return i != null && materialSet.contains(i.getType());
    }

}