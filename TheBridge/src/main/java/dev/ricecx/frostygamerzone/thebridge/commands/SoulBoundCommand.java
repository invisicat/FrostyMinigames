package dev.ricecx.frostygamerzone.thebridge.commands;

import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import dev.ricecx.frostygamerzone.thebridge.handler.SoulBound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandInfo(
        name = "sb"
)
public class SoulBoundCommand implements Command {

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        ItemStack item = ((Player) commandSender).getInventory().getItemInMainHand();

        ItemStack newItemStack = SoulBound.SoulboundTypes.LENIENT.setNBT(item, true);

        ((Player) commandSender).getInventory().addItem(newItemStack);
        // Tell the player that the item is now soulbound
        commandSender.sendMessage("§aItem is now soulbound");
    }
}
