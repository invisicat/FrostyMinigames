package dev.ricecx.frostygamerzone.minigameapi.lobby;

import dev.ricecx.frostygamerzone.minigameapi.team.Team;
import dev.ricecx.frostygamerzone.minigameapi.users.GameUser;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Set;

public interface TeamSelect<T extends Team<? extends GameUser>> {

    Set<Material> getCachedItemMaterials();
    Map<ItemStack, T> getTeamSelector();

    <V extends GameUser> void chooseTeam(V user, T team);

    default ItemStack addTeamSelector(ItemStack itemStack, T team) {
        getTeamSelector().put(itemStack, team);
        getCachedItemMaterials().add(itemStack.getType());

        return itemStack;
    }

    default void onItemInteract(GameUser user, ItemStack itemStack) {
        if(itemStack == null) return;
        if(!getCachedItemMaterials().contains(itemStack.getType())) return;
        T team = getTeamFromItem(itemStack);

        if(team != null)
            chooseTeam(user, team);
    }

    default T getTeamFromItem(ItemStack itemStack) {
        T team = null;
        for (Map.Entry<ItemStack, T> teams : getTeamSelector().entrySet()) {
            if(teams.getKey().isSimilar(itemStack)) {
                team = teams.getValue();
                break;
            }
        }

        return team;
    }
}
