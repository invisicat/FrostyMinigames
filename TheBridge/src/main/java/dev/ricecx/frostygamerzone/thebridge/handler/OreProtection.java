package dev.ricecx.frostygamerzone.thebridge.handler;

import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class OreProtection implements Listener {

    @Setter @Getter private static boolean active = true;

    private static final Set<Material> ores = new HashSet<>();

    private static final Set<Material> pickaxe = Tag.MINEABLE_PICKAXE.getValues();
    private static final Set<Material> axe = Tag.MINEABLE_AXE.getValues();
    private static final Set<Material> shovel = Tag.MINEABLE_SHOVEL.getValues();

    static {
        ores.addAll(Tag.COAL_ORES.getValues());
        ores.addAll(Tag.GOLD_ORES.getValues());
        ores.addAll(Tag.REDSTONE_ORES.getValues());
        ores.addAll(Tag.EMERALD_ORES.getValues());
        ores.addAll(Tag.DIAMOND_ORES.getValues());
        ores.addAll(Tag.LAPIS_ORES.getValues());
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent evt) {
        if(!isActive()) return;
        Block brokenBlock = evt.getBlock();
        Material blockMaterial = brokenBlock.getBlockData().getMaterial();
        Player player = evt.getPlayer();

        HandToolType toolType = getHandToolType(player.getInventory().getItemInMainHand().getType());

        if(toolType.contains(blockMaterial)) {
            player.getInventory().addItem(brokenBlock.getDrops().toArray(ItemStack[]::new));
            // add statistics here.

            if(toolType.canRegen(blockMaterial)) {
                regenerateBlock(brokenBlock, 5, Material.BEDROCK);
            }
        }

        evt.setDropItems(false);
        evt.setExpToDrop(0);
    }


    public HandToolType getHandToolType(Material material) {
        if(material.name().contains("_PICKAXE")) return HandToolType.PICKAXE;
        if(material.name().contains("_SPADE") || material.name().contains("_SHOVEL")) return HandToolType.SHOVEL;
        if(material.name().contains("_AXE")) return HandToolType.AXE;

        return HandToolType.HAND;
    }

    /**
     * Regenerates a block after an amount of time
     * @param block Block to regenerate
     * @param time time to regenerate in seconds
     * @param material Material to replace the block while it's regenerating
     */
    public void regenerateBlock(Block block, int time, Material material) {
        Material previousMat = block.getType();
        block.setType(material);

        OffloadTask.offloadDelayedSync(() -> block.setType(previousMat), time * 20);
    }

    public enum HandToolType {
        PICKAXE(pickaxe),
        HAND(null),
        AXE(axe),
        SHOVEL(shovel);

        @Getter private final Set<Material> acceptedMaterials;
        HandToolType(Set<Material> acceptedMaterials) {
            this.acceptedMaterials = acceptedMaterials;
        }

        /**
         * If this material is mineable with this tool type
         * @param material Material to mine
         * @return If it's mineable or not
         */
        public boolean contains(Material material) {
            return this.acceptedMaterials.contains(material);
        }

        public boolean canRegen(Material material) {
            if(this == PICKAXE || this == AXE) return false;

            // TODO: add wood regeneration
            return ores.contains(material);
        }
    }

    public record RegenKey(Material material, int regenTime) {}
}
