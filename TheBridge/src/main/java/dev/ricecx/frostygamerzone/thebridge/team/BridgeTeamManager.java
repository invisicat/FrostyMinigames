package dev.ricecx.frostygamerzone.thebridge.team;

import com.google.common.collect.Maps;
import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamColor;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamManager;
import dev.ricecx.frostygamerzone.minigameapi.utils.LocationUtils;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BridgeTeamManager extends TeamManager<BridgeUser, BridgeTeam> {

    @Getter
    private final Map<String, UUID> teams = Maps.newConcurrentMap();

    public BridgeTeamManager(Game<BridgeTeam, BridgeUser> game) {
        super(game);
        ItemStack redHelmet = new ItemBuilder(Material.LEATHER_HELMET).setName("&cTeam Red").toItemStack();
        LeatherArmorMeta meta = ((LeatherArmorMeta) redHelmet.getItemMeta());
        if(meta != null) meta.setColor(Color.RED);
        redHelmet.setItemMeta(meta);

        ItemStack blueHelmet = new ItemBuilder(Material.LEATHER_HELMET).setName("&9Team Blue").toItemStack();
        LeatherArmorMeta blueMeta = ((LeatherArmorMeta) blueHelmet.getItemMeta());
        if(blueMeta != null) blueMeta.setColor(Color.BLUE);
        blueHelmet.setItemMeta(blueMeta);

        BridgeTeam redTeam = new BridgeTeam(16, TeamColor.RED);
        BridgeTeam bleuTeam = new BridgeTeam(16, TeamColor.BLUE);

        redTeam.setTeamItem(redHelmet);
        bleuTeam.setTeamItem(blueHelmet);

        teams.put("red", addTeam(redTeam));
        teams.put("blue", addTeam(bleuTeam));
    }

    public BridgeTeam getTeamByNexus(Location location) {
        for (BridgeTeam team : getRegisteredTeams().values()) {
            if(LocationUtils.areSimilar(location, team.getNexusLocation())) return team;
        }

        return null;
    }

    public BridgeTeam getTeam(String name) {
        return getRegisteredTeams().get(teams.get(name));
    }
}
