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

        NBTItem nbtItem = new NBTItem(item);
        SoulBound.SoulboundTypes.LENIENT.setNBT(item, nbtItem, true);
    }
}
