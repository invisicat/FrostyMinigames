package dev.ricecx.frostygamerzone.minigameapi.map;

import dev.ricecx.frostygamerzone.common.LoggingUtils;
import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.team.TeamColor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface MapMetaConsumer<T extends MapMeta> {

    void applyConfigMapper(T mapMeta);

    default <V> V getTeamMappedMethod(Team<?> team, String keyword, T meta, Class<V> clazz) {
        V obj = null;
        if(team.getTeamColor() == TeamColor.NONE) {
            throw new IllegalArgumentException("Team color is not found. We cannot map this team! " + team.getTeamID());
        }
        for (Field field : meta.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            LoggingUtils.debug("Mapping Utils - Found declared methods: " + field.getName());

            // Contains keywords and team color.
            if(field.getName().toLowerCase().contains(keyword.toLowerCase()) && field.getName().toLowerCase().contains(team.getTeamColor().getName().toLowerCase())) {
                // since this is a get method it should be easily invoked w/o any arguments
                obj = clazz.cast(friendlyGet(meta, field));
                break;
            }
        }


        return obj;
    }

    default Object friendlyGet(T meta, Field field) {
        Object obj = null;
        try {
            System.out.println(field.get(meta));
            obj = field.get(meta);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj;
    }

    default Object friendlyInvoke(T meta, Method method) {
        Object obj = null;
        try {
            obj = method.invoke(meta);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
