package dev.ricecx.frostygamerzone.thebridge.lobby;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Game;
import dev.ricecx.frostygamerzone.minigameapi.mapvoting.MapVoterManager;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.thebridge.map.BridgeMapManager;
import dev.ricecx.frostygamerzone.thebridge.team.BridgeTeam;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.*;

public class BridgeMapVoter extends MapVoterManager<BridgeTeam, BridgeUser> {

    final String charArray = "abcdefghijklmnopqrstuvwxyz";

    public BridgeMapVoter(Game<BridgeTeam, BridgeUser> game) {
        super(game);

        this.maps.addAll(MinigamesAPI.getMapManager(BridgeMapManager.class).getAllMaps().keySet());
    }

    @Override
    public void onVote(GameUser user, String map) {
        user.getPlayer().sendMessage("You have voted for map " + map);
    }


    public void openVoteGUI(GameUser user) {


        InventoryGui gui = new InventoryGui(CorePlugin.getInstance(), user.getPlayer(), "Vote for a map", generateGUI().toArray(String[]::new));
        gui.setFiller(new ItemStack(Material.GRAY_STAINED_GLASS, 1)); // fill the empty slots with this

        List<String> mapList = new ArrayList<>(maps);
        for (String map : mapList) {
            char idx = charArray.charAt(mapList.indexOf(map));
            gui.addElement(new DynamicGuiElement(idx, (viewer) -> new StaticGuiElement(idx, new ItemStack(Material.PAPER),
                    click -> {
                        click.getGui().draw(); // Update the GUI
                        ((Player) viewer).playSound(viewer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                        vote(user, map);
                        return true;
                    },
                    "Map: " + map)));
        }

        gui.show(user.getPlayer());

        Bukkit.getScheduler().runTaskTimer(CorePlugin.getInstance(), (Runnable) gui::draw, 1, 20);
    }

    private List<String> generateGUI() {
        List<String> gui = new ArrayList<>();

        if(maps.size() <= 0) maps.addAll(MinigamesAPI.getMapManager().getAllMaps().keySet());

        gui.add("         ");
        String availableCharacters = charArray.substring(0, maps.size());
        for (String characterSegment : Splitter.fixedLength(9).split(availableCharacters)) {
            // since max of 9 can fit on a line

            gui.add(Strings.padEnd(characterSegment, 9, ' '));
        }
        gui.add("         ");

        return gui;
    }
}
