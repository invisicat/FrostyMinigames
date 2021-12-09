package dev.ricecx.frostygamerzone.thebridge.handler;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author RiceCX
 */
public class SoulBound implements Listener {

    public static String SOULBOUND_KEY = "soulbound";

    private final Cache<UUID, ItemStack> dropTimerCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();

    @EventHandler
    private void onDrop(PlayerDropItemEvent evt) {
        ItemStack itemStack = evt.getItemDrop().getItemStack();
        UUID playerUUID = evt.getPlayer().getUniqueId();

        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey(SOULBOUND_KEY)) return;

        SoulboundTypes type = SoulboundTypes.parseNBT(nbtItem);
        if (type == null) return; // Soulbound was probably not set correctly

        if (type == SoulboundTypes.CANNOT_DROP) {
            evt.getPlayer().sendMessage("(!) Sorry, you cannot drop this item as it's bound to you forever!");
            evt.setCancelled(true);
        } else if (type == SoulboundTypes.LENIENT) {
            ItemStack stack = dropTimerCache.getIfPresent(playerUUID);
            if (stack == null) {
                evt.getPlayer().sendMessage("(!) Are you sure you want to drop this item? Drop again within 10 seconds to drop");
                dropTimerCache.put(playerUUID, itemStack);
                evt.setCancelled(true);
            } else {
                evt.getItemDrop().remove();
            }
        }
    }

    public enum SoulboundTypes {
        LENIENT("sb-lenient"),
        CANNOT_DROP("sb-cannotdrop");

        private static final SoulboundTypes[] CACHE = values();

        @Getter
        private final String nbtValue;

        SoulboundTypes(String nbtValue) {
            this.nbtValue = nbtValue;
        }

        public static SoulboundTypes from(String str) {
            for (SoulboundTypes soulboundTypes : CACHE) {
                if (soulboundTypes.getNbtValue().equals(str)) return soulboundTypes;
            }
            return null;
        }

        public static SoulboundTypes parseNBT(NBTItem item) {
            String type = item.getString(SOULBOUND_KEY);

            return from(type);
        }

        public ItemStack setNBT(ItemStack item, boolean lore) {
            NBTItem nbt = lore ? new NBTItem(setLore(item)) : new NBTItem(item);

            nbt.setString(SOULBOUND_KEY, getNbtValue());
            nbt.mergeNBT(item);

            return nbt.getItem();
        }

        private static ItemStack setLore(ItemStack item) {
            List<String> previousLore = item.getItemMeta() != null && item.getItemMeta().getLore() != null ? item.getItemMeta().getLore() : new ArrayList<>();

            ItemStack newStack = item.clone();
            ItemMeta meta = newStack.getItemMeta();
            previousLore.add(" ");
            previousLore.add(Utils.color("&5Soulbound"));

            if (meta != null) meta.setLore(previousLore);

            newStack.setItemMeta(meta);
            LoggingUtils.debug("Providing soul bound lore for " + item.getType());
            return newStack;
        }

        public ItemStack wrap(ItemStack item) {
            return setNBT(item, true);
        }

    }

}
