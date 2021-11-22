package dev.ricecx.frostygamerzone.minigameapi.buildtools.commands;

import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.map.MapMeta;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@CommandInfo(
        name = "bt"
)
public class BuildToolsCommand implements Command {

    private final Map<String, Field> singleFields = new HashMap<>();
    private final Map<String, Field> multiFields = new HashMap<>();
    private final MapMeta map;

    public BuildToolsCommand(Class<? extends MapMeta> clazz) {
        LoggingUtils.info("Loaded Builds tools for: " + clazz.getSimpleName());
        map = createObject(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("FIELD: " + field.getName());
            if(Collection.class.isAssignableFrom(field.getType())) {
                // this is a collection
                multiFields.put(field.getName(), field);
            } else {
                singleFields.put(field.getName(), field);
            }
        }
    }

    @Override
    public void run(CommandSender commandSender, String[] args) {

        if(args[0].equalsIgnoreCase("save")) {
            if(args.length >= 2) {
                saveMap((Player) commandSender, args[1]);
                MinigamesAPI.getMapManager().saveMap(map);
            } else {
                commandSender.sendMessage("Specify a map name.");
            }
            return;
        }
        if(args[0].equalsIgnoreCase("display")) {
            for (Map.Entry<String, String> str : prettyPrintFields().entrySet()) {
                commandSender.sendMessage(str.getKey() + " - " + str.getValue());
            }
        } else if(args[0].equalsIgnoreCase("set") && singleFields.containsKey(args[1])) {
            Field field = singleFields.get(args[1]);
            if(field.getType().isAssignableFrom(Location.class)) {
                commandSender.sendMessage("You have set a location for " + args[1]);
                commandSender.sendMessage(dev.ricecx.frostygamerzone.minigameapi.utils.Styles.location(((Player) commandSender).getLocation()));
                friendlySet(field, ((Player) commandSender).getLocation());
            }
        }
    }

    private void saveMap(Player player, String mapName) {
        map.setVersion(map.getVersion() + 1);
        map.setName(mapName);
        map.setWorldTemplateName(player.getWorld().getName());
        map.setLastModified(System.currentTimeMillis());
    }

    private Map<String, String> prettyPrintFields() {
        Map<String, String> prettyFields = new HashMap<>();

        for (Field declaredField : map.getClass().getDeclaredFields()) {
            Object wtf = declaredField.getType().cast(friendlyGet(declaredField));
            if(wtf != null) prettyFields.put(declaredField.getName(), wtf.toString());
        }

        return prettyFields;
    }

    private Object friendlyGet(Field field) {
        Object obj = null;
        try {
            field.setAccessible(true);
            obj = field.get(map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj;
    }
    private void friendlySet(Field field, Object set) {
        try {
            field.setAccessible(true);
            field.set(map, set);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        List<String> actions = new ArrayList<>();
        if(args.length <= 1) {
            actions.add("set");
            actions.add("display");
            actions.add("save");
        } else if(args.length == 2) {
            actions.addAll(singleFields.keySet());
            for (String multiField : multiFields.keySet()) {
                actions.add("list-" + multiField);
            }
        }

        return actions;
    }

    private static <T> T createObject(Class<? extends T> clazz) {
        T object = null;
        try {
            object = clazz.getConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            //
        }

        return object;
    }
}
