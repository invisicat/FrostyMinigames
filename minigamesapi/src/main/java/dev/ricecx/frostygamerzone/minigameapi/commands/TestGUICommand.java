package dev.ricecx.frostygamerzone.minigameapi.commands;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import de.themoep.inventorygui.DynamicGuiElement;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import dev.ricecx.frostygamerzone.bukkitapi.CorePlugin;
import dev.ricecx.frostygamerzone.bukkitapi.ItemBuilder;
import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import dev.ricecx.frostygamerzone.bukkitapi.module.Module;
import dev.ricecx.frostygamerzone.minigameapi.modules.gui.GUIModule;
import dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.FrostMenu;
import dev.ricecx.frostygamerzone.minigameapi.modules.gui.core.buttons.FrostButton;
import dev.ricecx.frostygamerzone.minigameapi.utils.OffloadTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

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
