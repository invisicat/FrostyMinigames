package dev.ricecx.frostygamerzone.thebridge.buildtools;

import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import dev.ricecx.frostygamerzone.minigameapi.buildtools.commands.BuildToolsCommand;
import dev.ricecx.frostygamerzone.thebridge.map.BridgeMapMetaImpl;
import org.bukkit.command.CommandSender;

@CommandInfo(
        name = "srv"
)
public class SrvCommand implements Command {

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        CorePlugin.getInstance().registerCommands(new BuildToolsCommand(BridgeMapMetaImpl.class));
    }
}
