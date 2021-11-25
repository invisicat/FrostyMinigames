package dev.ricecx.frostygamerzone.minigameapi.buildtools.commands;

import dev.ricecx.frostygamerzone.bukkitapi.commands.Command;
import dev.ricecx.frostygamerzone.bukkitapi.commands.CommandInfo;
import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.MinigamesAPI;
import dev.ricecx.frostygamerzone.minigameapi.game.Minigame;
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
    private MapMeta map;
    private MapMeta newMap;
    private final Class<? extends MapMeta> clazz;

    public BuildToolsCommand(Class<? extends MapMeta> clazz) {
        LoggingUtils.info("Loaded Builds tools for: " + clazz.getSimpleName());
        this.clazz = clazz;
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
          //      MinigamesAPI.getMapManager().saveMap(newMap);
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
        } else if(args[0].equalsIgnoreCase("add") && multiFields.containsKey(args[1])) {
            Field field = multiFields.get(args[1]);

            List<Location> locs = ((List<Location>) friendlyGet(field));
            if(locs == null) locs = new ArrayList<>();
            locs.add(((Player) commandSender).getLocation());

            friendlySet(field, locs);
            commandSender.sendMessage("You have added a location for " + args[1]);
        }
    }

    private void saveMap(Player player, String mapName) {
        MapMeta meta = clazz.cast(MinigamesAPI.getMapManager().getMapMeta(mapName));

        var a = combineMetas(meta, map);
        map.setVersion(meta.getVersion() + 1);
        map.setName(mapName);
        map.setWorldTemplateName(player.getWorld().getName());
        map.setLastModified(System.currentTimeMillis());

        a.setVersion(meta.getVersion() + 1);
        a.setName(mapName);
        a.setWorldTemplateName(player.getWorld().getName());
        a.setLastModified(System.currentTimeMillis());

        for (Field declaredField : a.getClass().getFields()) {
            LoggingUtils.info("Found field while saving " + declaredField.getName() + " A: " + friendlyGet(declaredField));
        }

        for (Field declaredField : a.getClass().getDeclaredFields()) {
            LoggingUtils.info("Found declared field while saving " + declaredField.getName() + " A: " + friendlyGet(declaredField));
        }
        newMap = a;
        LoggingUtils.info("bbba " + a.getClass());
        LoggingUtils.info(a.toString());

        MinigamesAPI.getMapManager().saveMap(clazz.cast(a));
    }

    private MapMeta combineMetas(MapMeta oldMeta, MapMeta newMeta) {
        Map<String, Field> combinedFields = new HashMap<>();
        MapMeta combinedMeta = createObject(clazz);

        for (Field combinedField : combinedMeta.getClass().getDeclaredFields()) {
            LoggingUtils.info("[Meta Combiner] Found Combiner Field: " + combinedField.getName());
            combinedFields.put(combinedField.getName(), combinedField);
        }

        for (Field field : oldMeta.getClass().getDeclaredFields()) {
            LoggingUtils.info("[Meta Combiner] Found old field: " + field.getName());
            friendlySet(combinedFields.get(field.getName()), friendlyGet(field));
        }

        for (Field newMetaFields : newMeta.getClass().getDeclaredFields()) {
            if(friendlyGet(newMetaFields) == null) continue;
            LoggingUtils.info("[Meta Combiner] Found new field: " + newMetaFields.getName());
            friendlySet(combinedFields.get(newMetaFields.getName()), friendlyGet(newMetaFields));
        }

        for (Field combinedField : combinedMeta.getClass().getDeclaredFields()) {
            LoggingUtils.info("[Meta Combiner] Combined field: " + combinedField.getName() + " b: " + friendlyGet(combinedField));
        }

        return combinedMeta;
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
            actions.add("add");
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
