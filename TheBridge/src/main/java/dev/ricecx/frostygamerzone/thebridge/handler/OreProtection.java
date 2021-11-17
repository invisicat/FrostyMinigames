package dev.ricecx.frostygamerzone.thebridge.handler;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;
import java.util.Set;

public class OreProtection implements Listener {
    public Set<Material> ores = new HashSet<>();

    public Set<Material> pickaxe = Tag.MINEABLE_PICKAXE.getValues();
    public Set<Material> axe = Tag.MINEABLE_AXE.getValues();
    public Set<Material> shovel = Tag.MINEABLE_SHOVEL.getValues();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent evt) {}
}
