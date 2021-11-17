package dev.ricecx.frostygamerzone.minigameapi.commands;

import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@CommandInfo(
        name = "gamemanager"
)
public class GameManagerCommand implements Command {

    @Override
    public void run(CommandSender commandSender, String[] args) {
        GameManager gameManager = MinigamesAPI.getGameManager();

        commandSender.sendMessage("All Players -> Lobby");
        for (Map.Entry<UUID, String> playerUUID : gameManager.getPlayersInGames().entrySet()) {
            commandSender.sendMessage(Objects.requireNonNull(Bukkit.getPlayer(playerUUID.getKey())).getDisplayName() + " - " + playerUUID.getValue());
        }
    }
}
