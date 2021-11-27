package dev.ricecx.frostygamerzone.thebridge.commands;

import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.thebridge.kits.BridgeKitRegistry;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(
        name = "kit"
)
public class KitCommand implements Command {

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof Player)) return;

        BridgeUser user = Users.getUser((Player) commandSender, BridgeUser.class);

        if(user != null)
            MinigamesAPI.getKitRegistry(BridgeKitRegistry.class).openKitGUI(user);
    }
}
