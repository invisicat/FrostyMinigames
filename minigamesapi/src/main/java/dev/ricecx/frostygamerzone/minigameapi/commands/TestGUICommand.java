package dev.ricecx.frostygamerzone.minigameapi.commands;

import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;

@CommandInfo(
        name = "testgui"
)
public class TestGUICommand implements Command {

    private final long time = System.currentTimeMillis();

    @Override
    public void run(CommandSender commandSender, String[] strings) {
        String[] guiSetup = {
                "   ",
                "   ",
                "  d"
        };
        InventoryGui gui = new InventoryGui(CorePlugin.getInstance(), (Player) commandSender, "NIGGER", guiSetup);
        gui.setFiller(new ItemStack(Material.GRAY_STAINED_GLASS, 1)); // fill the empty slots with this

        gui.addElement(new DynamicGuiElement('d', (viewer) -> new StaticGuiElement('d', new ItemStack (Material.CLOCK),
                click -> {
                    click.getGui().draw(); // Update the GUI
                    return true;
                },
                "Update time: " + new SimpleDateFormat("HH:mm:ss").format(new Date()))));

        gui.show(((Player) commandSender).getPlayer());

        Bukkit.getScheduler().runTaskTimer(CorePlugin.getInstance(), (Runnable) gui::draw,1, 20);
    }
}
