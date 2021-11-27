package dev.ricecx.frostygamerzone.thebridge.kits;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import de.themoep.inventorygui.InventoryGui;
import dev.ricecx.frostygamerzone.api.game.thebridge.TheBridgeKits;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.bukkitapi.Utils;
import dev.ricecx.frostygamerzone.minigameapi.kits.Kit;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitRegistry;
import dev.ricecx.frostygamerzone.minigameapi.kits.KitUser;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import dev.ricecx.frostygamerzone.thebridge.kits.impl.*;
import dev.ricecx.frostygamerzone.thebridge.users.BridgeUser;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BridgeKitRegistry extends KitRegistry<TheBridgeKits, BridgeUser> {
    final String charArray = "abcdefghijklmnopqrstuvwxyz";

    public BridgeKitRegistry() {
        register(
                new WarriorKit(),
                new AlchemistKit(),
                new ArcherKit(),
                new BeserkerKit(),
                new BlacksmithKit(),
                new BuilderKit(),
                new EnchanterKit(),
                new ButcherKit(),
                new HealerKit(),
                new HeroKit(),
                new MedicKit(),
                new NewbKit()
        );
    }


    @SuppressWarnings("UnstableApiUsage")
    public void openKitGUI(BridgeUser user) {
        InventoryGui gui = new InventoryGui(CorePlugin.getInstance(), user.getPlayer(), "Kits", generateKitGUI().toArray(String[]::new));

        List<TheBridgeKits> kitList = new ArrayList<>(getKits().keySet());
        for (TheBridgeKits kit : getKits().keySet()) {
            char idx = charArray.charAt(kitList.indexOf(kit));

            // https://stackoverflow.com/a/49772469 - the fastest way to combine 2 string[]
            String[] title = new String[] { Utils.color("&7" + kit.getName()) };
            String[] descriptionCut = Splitter.fixedLength(20).splitToList(kit.getDescription()).toArray(String[]::new);

            String[] both = Arrays.copyOf(title, title.length + descriptionCut.length);
            System.arraycopy(descriptionCut, 0, both, title.length, descriptionCut.length);

            // Add GUI element
            gui.addElement(idx, Material.ANVIL, (click) -> {
                if(user.getKit().equals(kit)) {
                    user.getPlayer().sendMessage(Utils.color("&7You've already selected kit&c " + kit.getName()));
                    user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1, 1);
                    return true;
                }
                user.setKit(kit);
                user.getPlayer().sendMessage(Utils.color("&7You have selected &c " + kit.getName()));
                user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
                return true;
            }, both);
        }

        gui.show(user.getPlayer());
    }
    private List<String> generateKitGUI() {
        List<String> gui = new ArrayList<>();

        String availableCharacters = charArray.substring(0, getKits().size());
        for (String characterSegment : Splitter.fixedLength(9).split(availableCharacters)) {
            // since max of 9 can fit on a line

            gui.add(Strings.padEnd(characterSegment, 9, ' '));
        }

        return gui;
    }
}
