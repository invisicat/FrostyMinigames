package dev.ricecx.frostygamerzone.minigameapi.kits;


import com.google.common.collect.Maps;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;

import java.util.Map;

public abstract class KitRegistry<K, U extends KitUser<U, K>> {

    private final Map<K, Kit<K, U>> kits = Maps.newConcurrentMap();

    public KitRegistry() {

    }

    public abstract void openKitGUI(U user);

    protected void register(Kit<K, U> ...kitss) {
        for (Kit<K, U> kit : kitss) {
            kits.put(kit.getKit(), kit);
        }
    }

    public Kit<K, U> getKit(K kitEnum) {
        return kits.get(kitEnum);
    }

    public Map<K, Kit<K, U>> getKits() {
        return kits;
    }
}
