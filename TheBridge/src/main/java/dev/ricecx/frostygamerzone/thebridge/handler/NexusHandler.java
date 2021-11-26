package dev.ricecx.frostygamerzone.thebridge.handler;

import dev.ricecx.frostygamerzone.bukkitapi.user.Users;
import dev.ricecx.frostygamerzone.thebridge.TheBridgeGame;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeamManager;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class NexusHandler implements Listener {
    @EventHandler
    public void onBreakNexus(BlockBreakEvent evt) {
        if(evt.getBlock().getType() != Material.END_STONE) return;

        BridgeUser user = Users.getUser(evt.getPlayer(), BridgeUser.class);
        if(user == null) return;

        BridgeTeam targetTeam = ((BridgeTeamManager) user.getGameObject(TheBridgeGame.class).getTeamManager()).getTeamByNexus(evt.getBlock().getLocation());
        if(targetTeam == null) return;

        if(user.getTeam() != targetTeam)
            targetTeam.breakNexus(user);
        else
            user.getPlayer().sendMessage("&cYou cannot destroy you own nexus!");

        evt.setCancelled(true);
    }
}
