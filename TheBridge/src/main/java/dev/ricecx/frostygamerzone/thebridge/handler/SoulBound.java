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

public class SoulBound implements Listener {

    public static String SOULBOUND_KEY = "soulbound";

    private final Cache<UUID, ItemStack> dropTimerCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();

    @EventHandler
    private void onDrop(PlayerDropItemEvent evt) {
        ItemStack itemStack = evt.getItemDrop().getItemStack();
        UUID playerUUID = evt.getPlayer().getUniqueId();

        NBTItem nbtItem = new NBTItem(itemStack);
        if(!nbtItem.hasKey(SOULBOUND_KEY)) return;

        SoulboundTypes type = SoulboundTypes.parseNBT(nbtItem);
        if(type == null) return; // Soulbound was probably not set correctly

        if(type == SoulboundTypes.CANNOT_DROP) {
            evt.getPlayer().sendMessage("(!) Sorry, you cannot drop this item as it's bounded to you forever!");
            evt.setCancelled(true);
        } else if(type == SoulboundTypes.LENIENT) {
            ItemStack stack = dropTimerCache.getIfPresent(playerUUID);
            if(stack == null) {
                evt.getPlayer().sendMessage("(!) Are you sure you want to drop this item? Drop again within 10 seconds to drop");
                dropTimerCache.put(playerUUID, itemStack);
                evt.setCancelled(true);
            }
        }
    }

    public enum SoulboundTypes {
        LENIENT("sb-lenient"),
        CANNOT_DROP("sb-cannotdrop");

        private static final SoulboundTypes[] CACHE = values();

        @Getter private final String nbtValue;

        SoulboundTypes(String nbtValue) {
            this.nbtValue = nbtValue;
        }

        public static SoulboundTypes from(String str) {
            for (SoulboundTypes soulboundTypes : CACHE) {
                if(soulboundTypes.getNbtValue().equals(str)) return soulboundTypes;
            }
            return null;
        }

        public static SoulboundTypes parseNBT(NBTItem item) {
            String type = item.getString(SOULBOUND_KEY);

            return from(type);
        }

        public ItemStack setNBT(ItemStack item, NBTItem nbtItem, boolean lore) {
            nbtItem.setString(SOULBOUND_KEY, getNbtValue());
            ItemStack stack = item;
            if(lore)
                stack = setLore(item);

            nbtItem.applyNBT(item);

            return stack;
        }

        private static ItemStack setLore(ItemStack item) {
            ItemStack stack = new ItemBuilder(item).lore("Soulbound").toItemStack();

            LoggingUtils.info("Providing soul bound lore for " + item.getType());

            return stack;
        }

        public void setNBT(ItemStack item, NBTItem nbtItem) {
            setNBT(item, nbtItem, true);
        }

        public ItemStack wrap(ItemStack item) {
            NBTItem nbt = new NBTItem(item);

            return setNBT(item, nbt, true);
        }
    }

}
